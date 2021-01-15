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

package io.github.forgecommunitywiki.examplemod.loot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.serialization.JsonOps;

import io.github.forgecommunitywiki.examplemod.util.GeneralHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.*;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * A loot modifier used to replace the target item with the associated
 * replacement if all the conditions have returned true.
 */
public class ReplaceLootModifier extends LootModifier {

    /**
     * The target item to replace.
     */
    private final Item target;
    /**
     * The stack to replace the item with.
     */
    private final ItemStack replacement;

    public ReplaceLootModifier(final ILootCondition[] conditionsIn, final Item target, final ItemStack replacement) {
        super(conditionsIn);
        this.target = target;
        this.replacement = replacement;
    }

    @Override
    protected List<ItemStack> doApply(final List<ItemStack> generatedLoot, final LootContext context) {
        return generatedLoot.stream().flatMap(
                stack -> stack.getItem() == this.target ? this.createStacks(stack.getCount()) : Stream.of(stack))
                .collect(Collectors.toList());
    }

    /**
     * Creates the {@link ItemStack}(s) of the replacement and compresses them into
     * as few stacks as possible.
     *
     * @param  count The number of stacks to produce
     * @return       The compressed stack list
     */
    private Stream<ItemStack> createStacks(int count) {
        final List<ItemStack> list = new ArrayList<>();
        while (count > 0) {
            final int stackCount = Math.min(count, this.replacement.getMaxStackSize());
            list.add(Util.make(this.replacement.copy(), stack -> stack.setCount(stackCount)));
            count -= stackCount;
        }
        return list.stream();
    }

    /**
     * A serializer used to decode the JSON for our loot modifiers.
     */
    public static class Serializer extends GlobalLootModifierSerializer<ReplaceLootModifier> {

        /**
         * A log marker used for the serializer.
         */
        private static final Marker MARKER = MarkerManager.getMarker("Replace Loot Serializer");

        @Override
        public ReplaceLootModifier read(final ResourceLocation location, final JsonObject object,
                final ILootCondition[] ailootcondition) {
            final Item target = ForgeRegistries.ITEMS
                    .getValue(new ResourceLocation(JSONUtils.getString(object, "target")));
            final ItemStack replacement = ItemStack.CODEC
                    .parse(JsonOps.INSTANCE, JSONUtils.getJsonObject(object, "replacement"))
                    .resultOrPartial(
                            Util.func_240982_a_("An error has occurred decoding the following replace loot modifier: ",
                                    str -> GeneralHelper.LOGGER.error(Serializer.MARKER, str)))
                    .orElseThrow(() -> new JsonParseException(
                            "The following replacement stack has been deserialized incorrectly."));
            return new ReplaceLootModifier(ailootcondition, target, replacement);
        }

        @Override
        public JsonObject write(final ReplaceLootModifier instance) {
            final JsonObject json = this.makeConditions(instance.conditions);
            json.addProperty("target", instance.target.getRegistryName().toString());
            ItemStack.CODEC.encodeStart(JsonOps.INSTANCE, instance.replacement)
                    .resultOrPartial(
                            Util.func_240982_a_("An error has occurred encoding the following replace loot modifier: ",
                                    str -> GeneralHelper.LOGGER.error(Serializer.MARKER, str)))
                    .ifPresent(element -> json.add("replacement", element));
            return json;
        }
    }
}
