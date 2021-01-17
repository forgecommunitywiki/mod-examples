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

package io.github.forgecommunitywiki.examplemod.data.server

import io.github.forgecommunitywiki.examplemod.CHICKEN_LEG
import io.github.forgecommunitywiki.examplemod.COOKED_CHICKEN_LEG
import io.github.forgecommunitywiki.examplemod.MOD_ID
import io.github.forgecommunitywiki.examplemod.REPLACE_LOOT
import io.github.forgecommunitywiki.examplemod.loot.ReplaceLootModifier
import net.minecraft.data.DataGenerator
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.loot.conditions.RandomChance
import net.minecraftforge.common.data.GlobalLootModifierProvider

/**
 * A provider used to generate loot modifiers as needed. These can either
 * replace, remove, or append items as specified by the modifier used.
 */
class GlobalLootModifiers(gen: DataGenerator): GlobalLootModifierProvider(gen, MOD_ID) {

    override fun start() {
        add("chicken_leg", REPLACE_LOOT.get(),
            ReplaceLootModifier(arrayOf(RandomChance.builder(0.4f).build()),
                Items.CHICKEN, ItemStack(CHICKEN_LEG.get(), 2)))
        add("cooked_chicken_leg", REPLACE_LOOT.get(),
            ReplaceLootModifier(arrayOf(RandomChance.builder(0.3f).build()),
                Items.COOKED_CHICKEN, ItemStack(COOKED_CHICKEN_LEG.get(), 2)))
    }
}