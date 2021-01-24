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

package io.github.forgecommunitywiki.examplemod.item

import net.minecraft.block.Block
import io.github.forgecommunitywiki.examplemod.block.IWrappedState
import net.minecraft.item.Item.Properties
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.AbstractFurnaceTileEntity

/**
 * A {@link BlockItem} that borrows the properties of a wrapped instance held
 * within {@link IWrappedState}.
 */
class WrappedBlockItem[T <: Block with IWrappedState](block: T, properties: Properties)
    extends BlockItem(block, properties) {

    /**
     * Used to mimic the properties of the wrapped block. For example, a block that
     * holds an oak log will have the same burn time as the oak log.
     */
    //@nowarn // Forge deprecation, only used to get vanilla burn times
    override def getBurnTime(itemStack: ItemStack): Int = {
        val item = this.getBlock.asInstanceOf[IWrappedState].getWrappedState.getBlock.asItem
        val time = item.getBurnTime(itemStack)
        if(time == -1) AbstractFurnaceTileEntity.getBurnTimes().getOrDefault(item, -1)
        else time
    }
}
