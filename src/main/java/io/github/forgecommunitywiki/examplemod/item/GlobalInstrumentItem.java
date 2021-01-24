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

import java.util.Optional;
import java.util.function.Supplier;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.LazyOptional;

/**
 * An instrument element used to play sounds on right click regardless of block.
 * It will choose a random pitch to play the sound at.
 */
public class GlobalInstrumentItem extends InstrumentItem {

    /**
     * Holds the sound to use when right clicking on a block.
     */
    private final LazyOptional<SoundEvent> sound;

    /**
     * Convenience constructor for non-burnable items.
     *
     * @param sound      The global sound to be played when right clicked
     * @param properties The properties associated with the item
     */
    public GlobalInstrumentItem(final Supplier<SoundEvent> sound, final Properties properties) {
        this(sound, -1, properties);
    }

    public GlobalInstrumentItem(final Supplier<SoundEvent> sound, final int burnTime, final Properties properties) {
        super(burnTime, properties);
        /*
         * We don't pass in a non null supplier because registry objects would also need
         * to be wrapped like so. In this case, it doesn't particularly matter as the
         * value will be resolved on first access.
         */
        this.sound = LazyOptional.of(() -> sound.get());
    }

    @Override
    public Optional<SoundEvent> getInstrumentSound(final ItemStack stack, final BlockState state) {
        return this.sound.resolve();
    }
}
