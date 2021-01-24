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
import net.minecraft.item.ItemStack
import net.minecraft.block.BlockState
import net.minecraft.util.SoundEvent

/**
 * An instrument element used to play sounds on right click of a certain block.
 * It will choose a random pitch to play the sound at.
 */
class InstrumentItem(private final val burnTime: Int, properties: Properties) extends Item(properties) {

    /**
     * Convenience constructor for non-burnable items.
     *
     * @param properties The properties associated with the item
     */
    def this(properties: Properties) = this(-1, properties)

    /**
     * Plays the sound on item use for the specific block.
     */
    override def onItemUse(context: ItemUseContext): ActionResultType = {
        val world = context.getWorld
        val pos = context.getPos
        this.getInstrumentSound(context.getItem, world.getBlockState(pos))
            .map(sound => {
                world.playSound(context.getPlayer, pos, sound, SoundCategory.BLOCKS, 0.1f,
                    Math.pow(2d, (Item.random.nextInt(24) - 12) / 12d).toFloat)
                ActionResultType.func_233537_a_(world.isRemote)
            }).getOrElse(super.onItemUse(context))
    }

    /**
     * Gets the sound of the instrument to play if present.
     *
     * @param stack The stack hitting the block
     * @param state The state of the block being hit
     * @return The sound event if present
     */
    def getInstrumentSound(stack: ItemStack, state: BlockState): Option[SoundEvent] =
        if(stack.getItem.isInstanceOf[InstrumentItem]) GeneralRegistrar.getInstrumentElementSounds(stack.getItem.asInstanceOf[InstrumentItem], state.getBlock)
        else None

    override def getBurnTime(itemStack: ItemStack): Int = burnTime
}
