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

import scala.jdk.CollectionConverters._
import net.minecraft.data.DataGenerator
import net.minecraft.data.LootTableProvider
import java.{util => ju}
import net.minecraft.loot.{LootTable, ValidationTracker}
import net.minecraft.util.ResourceLocation
import com.mojang.datafixers.util.Pair
import java.util.function.{BiConsumer, Consumer, Supplier}
import net.minecraft.loot.LootParameterSet
import net.minecraft.loot.LootTable.Builder
import scala.collection.mutable.Buffer
import io.github.forgecommunitywiki.examplemod.data.server.loot.BlockTables
import net.minecraft.loot.LootParameterSets

/**
 * A provider used to generate loot tables for associated parameter sets.
 */
class LootTables(generator: DataGenerator) extends LootTableProvider(generator) {

    override protected def getTables: ju.List[Pair[Supplier[Consumer[BiConsumer[ResourceLocation,Builder]]],LootParameterSet]] =
        Buffer[Pair[Supplier[Consumer[BiConsumer[ResourceLocation,Builder]]],LootParameterSet]](Pair.of(() => new BlockTables(), LootParameterSets.BLOCK)).asJava

    /**
     * This is overridden as otherwise the validation will fail since we do not have
     * any built-in tables.
     */
    override protected def validate(map: ju.Map[ResourceLocation,LootTable], validationtracker: ValidationTracker): Unit = {}
}
