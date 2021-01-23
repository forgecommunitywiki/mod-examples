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

package io.github.forgecommunitywiki.examplemod.item;

import io.github.forgecommunitywiki.examplemod.block.IWrappedState;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;

/**
 * A {@link BlockItem} that borrows the properties of a wrapped instance held
 * within {@link IWrappedState}.
 *
 * @param <T> A block that implements {@link IWrappedState}.
 */
public class WrappedBlockItem<T extends Block & IWrappedState> extends BlockItem {

    public WrappedBlockItem(final T block, final Properties builder) {
        super(block, builder);
    }

    /**
     * Used to mimic the properties of the wrapped block. For example, a block that
     * holds an oak log will have the same burn time as the oak log.
     */
    @SuppressWarnings("deprecation") // Forge deprecation, only used to get vanilla burn times
    @Override
    public int getBurnTime(final ItemStack itemStack) {
        final Item item = ((IWrappedState) this.getBlock()).getWrappedState().getBlock().asItem();
        final int time = item.getBurnTime(itemStack);
        return time == -1 ? AbstractFurnaceTileEntity.getBurnTimes().getOrDefault(item, -1) : time;
    }
}
