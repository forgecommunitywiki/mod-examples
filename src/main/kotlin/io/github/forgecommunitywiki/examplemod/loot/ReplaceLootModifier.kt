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

import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.mojang.serialization.JsonOps
import io.github.forgecommunitywiki.examplemod.util.LOGGER
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.loot.LootContext
import net.minecraft.loot.conditions.ILootCondition
import net.minecraft.util.JSONUtils
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.loot.GlobalLootModifierSerializer
import net.minecraftforge.common.loot.LootModifier
import net.minecraftforge.registries.ForgeRegistries
import org.apache.logging.log4j.MarkerManager
import kotlin.math.min


/**
 * A loot modifier used to replace the target item with the associated
 * replacement if all the conditions have returned true.
 */
open class ReplaceLootModifier(conditions: Array<out ILootCondition>?, private val target: Item, private val replacement: ItemStack)
    : LootModifier(conditions) {

    override fun doApply(generatedLoot: MutableList<ItemStack>?, context: LootContext?): MutableList<ItemStack>
        = generatedLoot!!.asSequence().flatMap {
            if(it.item == target) createStacks(it.count) else sequenceOf(it) }.toMutableList()

    /**
     * Creates the {@link ItemStack}(s) of the replacement and compresses them into
     * as few stacks as possible.
     *
     * @param  count The number of stacks to produce
     * @return       The compressed stack list
     */
    private fun createStacks(count: Int): Sequence<ItemStack> = sequence {
        var remaining = count * replacement.count
        while (remaining > 0) {
            val stackCount = min(remaining, replacement.maxStackSize)
            remaining -= stackCount
            yield(replacement.copy().also { it.count = stackCount })
        }
    }

    /**
     * A serializer used to decode the JSON for our loot modifiers.
     */
    class Serializer: GlobalLootModifierSerializer<ReplaceLootModifier>() {

        /**
         * A log marker used for the serializer.
         */
        private val marker = MarkerManager.getMarker("Replace Loot Serializer")

        override fun read(
            location: ResourceLocation?,
            `object`: JsonObject?,
            ailootcondition: Array<out ILootCondition>?
        ): ReplaceLootModifier
             = ReplaceLootModifier(ailootcondition,
            ForgeRegistries.ITEMS.getValue(ResourceLocation(JSONUtils.getString(`object`!!, "target")))!!,
            ItemStack.CODEC.parse(JsonOps.INSTANCE, JSONUtils.getJsonObject(`object`, "replacement"))
                .resultOrPartial { LOGGER.error(marker, "An error has occurred decoding the following replace loot modifier: {}", it) }
                .orElseThrow { JsonParseException("The following replacement stack has been deserialized incorrectly.") })

        override fun write(instance: ReplaceLootModifier?): JsonObject
            = makeConditions(instance?.conditions).also { json ->
            json.addProperty("target", instance?.target?.registryName.toString())
            ItemStack.CODEC.encodeStart(JsonOps.INSTANCE, instance?.replacement)
                .resultOrPartial  { LOGGER.error(marker, "An error has occurred encoding the following replace loot modifier: {}", it) }
                .ifPresent { json.add("replacement", it) }
        }
    }
}