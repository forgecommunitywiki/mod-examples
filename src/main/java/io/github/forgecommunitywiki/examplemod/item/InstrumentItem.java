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

import io.github.forgecommunitywiki.examplemod.GeneralRegistrar;
import net.minecraft.block.BlockState;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * An instrument element used to play sounds on right click of a certain block.
 * It will choose a random pitch to play the sound at.
 */
public class InstrumentItem extends Item {

    /**
     * The burn time of the item. If it shouldn't burn, this is -1.
     */
    private final int burnTime;

    /**
     * Convenience constructor for non-burnable items.
     *
     * @param properties The properties associated with the item
     */
    public InstrumentItem(final Properties properties) {
        this(-1, properties);
    }

    public InstrumentItem(final int burnTime, final Properties properties) {
        super(properties);
        this.burnTime = burnTime;
    }

    /**
     * Plays the sound on item use for the specific block.
     */
    @Override
    public ActionResultType onItemUse(final ItemUseContext context) {
        final World world = context.getWorld();
        final BlockPos pos = context.getPos();
        return this.getInstrumentSound(world.getBlockState(pos)).map(sound -> {
            world.playSound(context.getPlayer(), pos, sound, SoundCategory.BLOCKS, 0.1f,
                    (float) Math.pow(2d, (Item.random.nextInt(24) - 12) / 12d));
            return ActionResultType.func_233537_a_(world.isRemote);
        }).orElseGet(() -> super.onItemUse(context));
    }

    /**
     * Gets an optional of the instrument sound to play if present.
     *
     * @param  state The state of the block being hit
     * @return       An {@link Optional} containing the sound event if available
     */
    public Optional<SoundEvent> getInstrumentSound(final BlockState state) {
        return GeneralRegistrar.getInstrumentElementSounds(this, state.getBlock());
    }

    @Override
    public int getBurnTime(final ItemStack itemStack) {
        return this.burnTime;
    }
}
