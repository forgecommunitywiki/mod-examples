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

import net.minecraft.util.SoundEvent
import net.minecraft.item.Item.Properties
import net.minecraft.block.BlockState
import net.minecraft.item.ItemStack

/**
 * An instrument element used to play sounds on right click regardless of block.
 * It will choose a random pitch to play the sound at.
 */
class GlobalInstrumentItem(_sound: => SoundEvent, burnTime: Int, properties: Properties)
    extends InstrumentItem(burnTime, properties) {
    /**
      * Lazily store the by-name reference
      */
    private final lazy val sound = _sound

    /**
     * Convenience constructor for non-burnable items.
     *
     * @param sound      The global sound to be played when right clicked
     * @param properties The properties associated with the item
     */
    def this(_sound: => SoundEvent, properties: Properties) = this(_sound, -1, properties)

    override def getInstrumentSound(stack: ItemStack, state: BlockState): Option[SoundEvent] = Some(sound)
}
