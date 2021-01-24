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

import com.mojang.datafixers.util.Pair
import io.github.forgecommunitywiki.examplemod.data.server.loot.BlockTables
import net.minecraft.data.DataGenerator
import net.minecraft.data.LootTableProvider
import net.minecraft.loot.LootParameterSet
import net.minecraft.loot.LootParameterSets
import net.minecraft.loot.LootTable
import net.minecraft.loot.ValidationTracker
import net.minecraft.util.ResourceLocation
import java.util.function.BiConsumer
import java.util.function.Consumer
import java.util.function.Supplier

/**
 * A provider used to generate loot tables for associated blocks.
 */
class LootTables(generator: DataGenerator) : LootTableProvider(generator) {

    override fun getTables(): MutableList<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> =
        mutableListOf(Pair(Supplier { BlockTables() }, LootParameterSets.BLOCK))

    /**
     * This is overridden as otherwise the validation will fail since we do not have
     * any built-in tables.
     */
    override fun validate(map: MutableMap<ResourceLocation, LootTable>, validationtracker: ValidationTracker) {}
}
