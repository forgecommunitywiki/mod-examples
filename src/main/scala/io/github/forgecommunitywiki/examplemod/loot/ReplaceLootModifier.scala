/*
 * MIT License
 *
 * Copyright (c) 2021 Forge Community Wiki
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.forgecommunitywiki.examplemod.loot

import net.minecraftforge.common.loot.LootModifier
import net.minecraft.loot.conditions.ILootCondition
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.loot.LootContext
import scala.jdk.CollectionConverters._
import java.{util => ju}
import net.minecraftforge.common.loot.GlobalLootModifierSerializer
import com.google.gson.JsonObject
import net.minecraft.util.ResourceLocation
import com.google.gson.JsonObject
import org.apache.logging.log4j.MarkerManager
import com.mojang.serialization.JsonOps
import io.github.forgecommunitywiki.examplemod.util.GeneralHelper
import com.google.gson.JsonElement
import net.minecraftforge.registries.ForgeRegistries
import net.minecraft.util.JSONUtils
import com.google.gson.JsonParseException

/**
 * A loot modifier used to replace the target item with the associated
 * replacement if all the conditions have returned true.
 */
class ReplaceLootModifier(conditions: Array[ILootCondition], private final val target: Item, private final val replacement: ItemStack)
    extends LootModifier(conditions) {

    private final val conditionsIn = conditions

    override protected def doApply(generatedLoot: ju.List[ItemStack], context: LootContext): ju.List[ItemStack] = 
        generatedLoot.asScala.flatMap(s => 
            if(s.getItem() == target) createStack(s.getCount() * replacement.getCount())
            else s #:: LazyList.empty).asJava

    /**
     * Creates the {@link ItemStack}(s) of the replacement and compresses them into
     * as few stacks as possible.
     *
     * @param  count The number of stacks to produce
     * @return       The compressed stack list
     */
    private def createStack(count: Int): LazyList[ItemStack] =
        if (count <= 0) LazyList.empty
        else {
            val stackCount = count.min(replacement.getMaxStackSize())
            new Some(replacement.copy()).map(s => {
                s.setCount(stackCount)
                s
            }).get #:: createStack(count - stackCount)
        }
}
object ReplaceLootModifier {
    class Serializer extends GlobalLootModifierSerializer[ReplaceLootModifier] {    

        override def read(location: ResourceLocation, `object`: JsonObject, conditions: Array[ILootCondition]): ReplaceLootModifier =
            new ReplaceLootModifier(conditions,
                ForgeRegistries.ITEMS.getValue(new ResourceLocation(JSONUtils.getString(`object`, "target"))),
                ItemStack.CODEC.parse(JsonOps.INSTANCE, JSONUtils.getJsonObject(`object`, "replacement"))
                    .resultOrPartial((s: String) => GeneralHelper.LOGGER.error("An error has occurred decoding the following replace loot modifier: {}", s))
                    .orElseThrow(() => new JsonParseException("The following replacement stack has been deserialized incorrectly.")))

        // TODO: Seems as though private members cannot be accessed from nested classes
        override def write(instance: ReplaceLootModifier): JsonObject =
            new Some(makeConditions(instance.conditionsIn)).map(json => {
                json.addProperty("target", instance.target.getRegistryName().toString())
                ItemStack.CODEC.encodeStart(JsonOps.INSTANCE, instance.replacement)
                    .resultOrPartial((s: String) => GeneralHelper.LOGGER.error("An error has occurred encoding the following replace loot modifier: {}", s))
                    .ifPresent((e: JsonElement) => json.add("replacement", e))
                json
            }).get
    }
    object Serializer {

        /**
         * A log marker used for the serializer.
         */
        private final val MARKER = MarkerManager.getMarker("Replace Loot Serializer")
    }
}