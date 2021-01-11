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
import io.github.forgecommunitywiki.examplemod.util.GeneralHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * An instrument element used to play sounds on right click. It will choose a
 * random pitch to test the sound for.
 */
public class InstrumentElementItem extends Item {

    public InstrumentElementItem(final Properties properties) {
        super(properties);
    }

    /**
     * Plays the sound on item use for the specific block.
     */
    @Override
    public ActionResultType onItemUse(final ItemUseContext context) {
        final World world = context.getWorld();
        final BlockPos pos = context.getPos();
        return Optional
                .ofNullable(GeneralRegistrar.getInstrumentElementSounds(this, world.getBlockState(pos).getBlock()))
                .map(sound -> {
                    world.playSound(context.getPlayer(), pos, sound, SoundCategory.BLOCKS, 0.1f,
                            (float) Math.pow(2d, (GeneralHelper.RANDOM.nextInt(24) - 12) / 12d));
                    return ActionResultType.func_233537_a_(world.isRemote);
                }).orElse(super.onItemUse(context));
    }
}
