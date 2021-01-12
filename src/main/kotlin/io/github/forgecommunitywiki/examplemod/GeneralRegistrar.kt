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

package io.github.forgecommunitywiki.examplemod

import io.github.forgecommunitywiki.examplemod.item.InstrumentElementItem
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraft.util.SoundEvent
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

/**
 * A class used to handle all registry objects added by this mod.
 */
object GeneralRegistrar {

    // Registers
    private val BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ExampleMod.ID)
    private val ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExampleMod.ID)
    private val SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ExampleMod.ID)

    // Items
    val DRUMSTICK: RegistryObject<InstrumentElementItem> = ITEMS.register("drumstick")
        { object : InstrumentElementItem(Properties().group(ItemGroup.MISC).maxStackSize(2)) {
            /**
             * We implement the burn time here instead of as a method in the class as its
             * completely unrelated to any data stored inside. It should be more of an item
             * property, but since this is not the case, an anonymous class that adds the
             * parameter when needed will work as well.
             *
             * @param  stack The stack being smelted
             * @return       The burn time of the item in ticks
             */
            override fun getBurnTime(stack: ItemStack?): Int {
                return 300
            }
        } }

    // Sound Events
    val DRUMSTICK_OAK_LOG_HIT = registerSoundEvent("instrument.drumstick.oak_log")
    val DRUMSTICK_BIRCH_LOG_HIT = registerSoundEvent("instrument.drumstick.birch_log")
    val DRUMSTICK_SPRUCE_LOG_HIT = registerSoundEvent("instrument.drumstick.spruce_log")
    val DRUMSTICK_JUNGLE_LOG_HIT = registerSoundEvent("instrument.drumstick.jungle_log")
    val DRUMSTICK_ACACIA_LOG_HIT = registerSoundEvent("instrument.drumstick.acacia_log")
    val DRUMSTICK_DARK_OAK_LOG_HIT = registerSoundEvent("instrument.drumstick.dark_oak_log")
    val DRUMSTICK_CRIMSON_STEM_HIT = registerSoundEvent("instrument.drumstick.crimson_stem")
    val DRUMSTICK_WARPED_STEM_HIT = registerSoundEvent("instrument.drumstick.warped_stem")

    // Slave Maps
    private val ELEMENT_SOUNDS: MutableMap<InstrumentElementItem, MutableMap<Block, SoundEvent>> = mutableMapOf()

    /**
    * Registers the {@link DeferredRegister}s to the event bus.
    */
    internal fun register(modBus: IEventBus) {
        BLOCKS.register(modBus)
        ITEMS.register(modBus)
        SOUND_EVENTS.register(modBus)
    }

    /**
     * Handles any slave mappings between different vanilla registries.
     */
    internal fun registerSlaveMaps() {
        ELEMENT_SOUNDS[DRUMSTICK.get()] = mutableMapOf(
            Blocks.OAK_LOG to DRUMSTICK_OAK_LOG_HIT.get(),
            Blocks.BIRCH_LOG to DRUMSTICK_BIRCH_LOG_HIT.get(),
            Blocks.SPRUCE_LOG to DRUMSTICK_SPRUCE_LOG_HIT.get(),
            Blocks.JUNGLE_LOG to DRUMSTICK_JUNGLE_LOG_HIT.get(),
            Blocks.ACACIA_LOG to DRUMSTICK_ACACIA_LOG_HIT.get(),
            Blocks.DARK_OAK_LOG to DRUMSTICK_DARK_OAK_LOG_HIT.get(),
            Blocks.CRIMSON_STEM to DRUMSTICK_CRIMSON_STEM_HIT.get(),
            Blocks.WARPED_STEM to DRUMSTICK_WARPED_STEM_HIT.get(),
        )
    }

    /**
     * Helper method to create a sound event as the names can be equivalent.
     *
     * @param  name The sound name as dictated within {@code sounds.json}.
     * @return      The sound event registry object
     */
    private fun registerSoundEvent(name: String) : RegistryObject<SoundEvent>
        = SOUND_EVENTS.register(name) { SoundEvent(ResourceLocation(ExampleMod.ID, name)) }

    fun getInstrumentElementSounds(item: InstrumentElementItem, hitBlock: Block): SoundEvent?
        = ELEMENT_SOUNDS.getOrDefault(item, mapOf())[hitBlock]
}
