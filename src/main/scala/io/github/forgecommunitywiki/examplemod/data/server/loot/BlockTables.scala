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

import scala.jdk.CollectionConverters._
import net.minecraft.data.loot.BlockLootTables
import net.minecraft.block.Block
import io.github.forgecommunitywiki.examplemod.GeneralRegistrar

/**
 * A class used to register loot tables associated with blocks.
 */
class BlockTables extends BlockLootTables {

    override protected def addTables: Unit = {
        this.registerDropSelfLootTable(GeneralRegistrar.OAK_LOG_DRUM.get)
        this.registerDropSelfLootTable(GeneralRegistrar.BIRCH_LOG_DRUM.get)
        this.registerDropSelfLootTable(GeneralRegistrar.SPRUCE_LOG_DRUM.get)
        this.registerDropSelfLootTable(GeneralRegistrar.JUNGLE_LOG_DRUM.get)
        this.registerDropSelfLootTable(GeneralRegistrar.ACACIA_LOG_DRUM.get)
        this.registerDropSelfLootTable(GeneralRegistrar.DARK_OAK_LOG_DRUM.get)
        this.registerDropSelfLootTable(GeneralRegistrar.CRIMSON_STEM_DRUM.get)
        this.registerDropSelfLootTable(GeneralRegistrar.WARPED_STEM_DRUM.get)  
    }

    override protected def getKnownBlocks: java.lang.Iterable[Block] =
        GeneralRegistrar.getBlocks.map(_.get).asJava
}
