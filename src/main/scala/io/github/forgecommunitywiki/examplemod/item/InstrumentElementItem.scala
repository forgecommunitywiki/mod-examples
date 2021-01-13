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

import net.minecraft.item.Item.Properties
import net.minecraft.item.Item
import net.minecraft.item.ItemUseContext
import net.minecraft.util.ActionResultType
import io.github.forgecommunitywiki.examplemod.GeneralRegistrar
import net.minecraft.util.SoundCategory

/**
 * An instrument element used to play sounds on right click. It will choose a
 * random pitch to test the sound for.
 */
class InstrumentElementItem(properties: Properties) extends Item(properties) {

    /**
     * Plays the sound on item use for the specific block.
     */
    override def onItemUse(context: ItemUseContext): ActionResultType = {
        val world = context.getWorld()
        val pos = context.getPos()
        GeneralRegistrar.getInstrumentElementSounds(this, world.getBlockState(pos).getBlock())
            .map(sound => {
                world.playSound(context.getPlayer(), pos, sound, SoundCategory.BLOCKS, 0.1f,
                    Math.pow(2d, (Item.random.nextInt(24) - 12) / 12d).toFloat)
                ActionResultType.func_233537_a_(world.isRemote)
            }).getOrElse(super.onItemUse(context))
    }
}
