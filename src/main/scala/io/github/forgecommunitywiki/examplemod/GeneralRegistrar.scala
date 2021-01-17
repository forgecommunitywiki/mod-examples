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

import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.RegistryObject
import net.minecraft.util.SoundEvent
import net.minecraft.util.ResourceLocation
import io.github.forgecommunitywiki.examplemod.item.InstrumentElementItem
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.block.Blocks

/**
 * A class used to handle all registry objects added by this mod.
 */
object GeneralRegistrar {

    // Registers
    private final val BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ExampleMod.ID)
    private final val ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExampleMod.ID)
    private final val SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ExampleMod.ID)

    // Items
    final val DRUMSTICK: RegistryObject[InstrumentElementItem] = ITEMS.register("drumstick", () =>
        new InstrumentElementItem(new Item.Properties().group(ItemGroup.MISC).maxStackSize(2)) {
            /**
            * We implement the burn time here instead of as a method in the class as its
            * completely unrelated to any data stored inside. It should be more of an item
            * property, but since this is not the case, an anonymous class that adds the
            * parameter when needed will work as well.
            *
            * @param  stack The stack being smelted
            * @return       The burn time of the item in ticks
            */
            override def getBurnTime(stack: ItemStack): Int = 300
        })

    // Sound Events
    final val DRUMSTICK_OAK_LOG_HIT = registerSoundEvent("instrument.drumstick.oak_log")
    final val DRUMSTICK_BIRCH_LOG_HIT = registerSoundEvent("instrument.drumstick.birch_log")
    final val DRUMSTICK_SPRUCE_LOG_HIT = registerSoundEvent("instrument.drumstick.spruce_log")
    final val DRUMSTICK_JUNGLE_LOG_HIT = registerSoundEvent("instrument.drumstick.jungle_log")
    final val DRUMSTICK_ACACIA_LOG_HIT = registerSoundEvent("instrument.drumstick.acacia_log")
    final val DRUMSTICK_DARK_OAK_LOG_HIT = registerSoundEvent("instrument.drumstick.dark_oak_log")
    final val DRUMSTICK_CRIMSON_STEM_HIT = registerSoundEvent("instrument.drumstick.crimson_stem")
    final val DRUMSTICK_WARPED_STEM_HIT = registerSoundEvent("instrument.drumstick.warped_stem")

    // Slave Maps
    private final lazy val ELEMENT_SOUNDS = Map(
        DRUMSTICK.get() -> Map(
            Blocks.OAK_LOG -> DRUMSTICK_OAK_LOG_HIT.get(),
            Blocks.BIRCH_LOG -> DRUMSTICK_BIRCH_LOG_HIT.get(),
            Blocks.SPRUCE_LOG -> DRUMSTICK_SPRUCE_LOG_HIT.get(),
            Blocks.JUNGLE_LOG -> DRUMSTICK_JUNGLE_LOG_HIT.get(),
            Blocks.ACACIA_LOG -> DRUMSTICK_ACACIA_LOG_HIT.get(),
            Blocks.DARK_OAK_LOG -> DRUMSTICK_DARK_OAK_LOG_HIT.get(),
            Blocks.CRIMSON_STEM -> DRUMSTICK_CRIMSON_STEM_HIT.get(),
            Blocks.WARPED_STEM -> DRUMSTICK_WARPED_STEM_HIT.get()
        )
    )

    /**
     * Registers the {@link DeferredRegister}s to the event bus.
     *
     * @param modBus The associated mod event bus
     */
    def register(modBus: IEventBus): Unit = {
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        SOUND_EVENTS.register(modBus);
    }

    /**
     * Handles any slave mappings between different vanilla registries.
     *
     * Does not need to be synchronized as the map is thread-safe and
     * there are no modifications that can happen at this time.
     */
    def registerSlaveMaps(): Unit = {
        ELEMENT_SOUNDS.values
    }
    
    /**
     * Helper method to create a sound event as the names can be equivalent.
     *
     * @param  name The sound name as dictated within {@code sounds.json}.
     * @return      The sound event registry object
     */
    private def registerSoundEvent(name: String): RegistryObject[SoundEvent] =
        SOUND_EVENTS.register(name, () => new SoundEvent(new ResourceLocation(ExampleMod.ID, name)))

     /**
     * Grabs the instrument element sound according to the block hit. Returns null
     * if there is no sound present.
     *
     * @param  item     The item hitting the block
     * @param  hitBlock The block being hit
     * @return          The sound played if the block is hit, an empty option otherwise
     */
    def getInstrumentElementSounds(item: InstrumentElementItem, hitBlock: Block): Option[SoundEvent] =
        ELEMENT_SOUNDS.getOrElse(item, Map()).get(hitBlock)
}
