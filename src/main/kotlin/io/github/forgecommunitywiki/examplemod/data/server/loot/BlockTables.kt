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

package io.github.forgecommunitywiki.examplemod.data.server.loot

import io.github.forgecommunitywiki.examplemod.ACACIA_LOG_DRUM
import io.github.forgecommunitywiki.examplemod.BIRCH_LOG_DRUM
import io.github.forgecommunitywiki.examplemod.CRIMSON_STEM_DRUM
import io.github.forgecommunitywiki.examplemod.DARK_OAK_LOG_DRUM
import io.github.forgecommunitywiki.examplemod.JUNGLE_LOG_DRUM
import io.github.forgecommunitywiki.examplemod.OAK_LOG_DRUM
import io.github.forgecommunitywiki.examplemod.SPRUCE_LOG_DRUM
import io.github.forgecommunitywiki.examplemod.WARPED_STEM_DRUM
import io.github.forgecommunitywiki.examplemod.getBlocks
import net.minecraft.block.Block
import net.minecraft.data.loot.BlockLootTables

/**
 * A class used to register loot tables associated with blocks.
 */
class BlockTables : BlockLootTables() {

    override fun addTables() {
        this.registerDropSelfLootTable(OAK_LOG_DRUM.get())
        this.registerDropSelfLootTable(BIRCH_LOG_DRUM.get())
        this.registerDropSelfLootTable(SPRUCE_LOG_DRUM.get())
        this.registerDropSelfLootTable(JUNGLE_LOG_DRUM.get())
        this.registerDropSelfLootTable(ACACIA_LOG_DRUM.get())
        this.registerDropSelfLootTable(DARK_OAK_LOG_DRUM.get())
        this.registerDropSelfLootTable(CRIMSON_STEM_DRUM.get())
        this.registerDropSelfLootTable(WARPED_STEM_DRUM.get())
    }

    /**
     * Used to verify that all registered blocks has an associated loot table.
     */
    override fun getKnownBlocks(): MutableIterable<Block> =
        getBlocks().map { it.get() }.toMutableSet()
}
