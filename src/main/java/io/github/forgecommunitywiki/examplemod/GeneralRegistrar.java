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

package io.github.forgecommunitywiki.examplemod;

import java.util.*;

import io.github.forgecommunitywiki.examplemod.item.InstrumentElementItem;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * A class used to handle all registry objects added by this mod.
 */
public final class GeneralRegistrar {

    // Registers
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            ExampleMod.ID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExampleMod.ID);
    private static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister
            .create(ForgeRegistries.SOUND_EVENTS, ExampleMod.ID);

    // Items
    public static final RegistryObject<InstrumentElementItem> DRUMSTICK = GeneralRegistrar.ITEMS.register("drumstick",
            () -> new InstrumentElementItem(new Item.Properties().group(ItemGroup.MISC).maxStackSize(2)) {
                /**
                 * We implement the burn time here instead of as a method in the class as its
                 * completely unrelated to any data stored inside. It should be more of an item
                 * property, but since this is not the case, an anonymous class that adds the
                 * parameter when needed will work as well.
                 *
                 * @param  stack The stack being smelted
                 * @return       The burn time of the item in ticks
                 */
                @Override
                public int getBurnTime(final ItemStack stack) {
                    return 300;
                }
            });

    // Sound Events
    public static final RegistryObject<SoundEvent> DRUMSTICK_OAK_LOG_HIT = GeneralRegistrar
            .registerSoundEvent("instrument.drumstick.oak_log");
    public static final RegistryObject<SoundEvent> DRUMSTICK_BIRCH_LOG_HIT = GeneralRegistrar
            .registerSoundEvent("instrument.drumstick.birch_log");
    public static final RegistryObject<SoundEvent> DRUMSTICK_SPRUCE_LOG_HIT = GeneralRegistrar
            .registerSoundEvent("instrument.drumstick.spruce_log");
    public static final RegistryObject<SoundEvent> DRUMSTICK_JUNGLE_LOG_HIT = GeneralRegistrar
            .registerSoundEvent("instrument.drumstick.jungle_log");
    public static final RegistryObject<SoundEvent> DRUMSTICK_ACACIA_LOG_HIT = GeneralRegistrar
            .registerSoundEvent("instrument.drumstick.acacia_log");
    public static final RegistryObject<SoundEvent> DRUMSTICK_DARK_OAK_LOG_HIT = GeneralRegistrar
            .registerSoundEvent("instrument.drumstick.dark_oak_log");
    public static final RegistryObject<SoundEvent> DRUMSTICK_CRIMSON_STEM_HIT = GeneralRegistrar
            .registerSoundEvent("instrument.drumstick.crimson_stem");
    public static final RegistryObject<SoundEvent> DRUMSTICK_WARPED_STEM_HIT = GeneralRegistrar
            .registerSoundEvent("instrument.drumstick.warped_stem");

    // Slave Maps
    private static final Map<InstrumentElementItem, Map<Block, SoundEvent>> ELEMENT_SOUNDS = new HashMap<>();

    /**
     * Registers the {@link DeferredRegister}s to the event bus.
     *
     * @param modBus The associated mod event bus
     */
    protected static void register(final IEventBus modBus) {
        GeneralRegistrar.BLOCKS.register(modBus);
        GeneralRegistrar.ITEMS.register(modBus);
        GeneralRegistrar.SOUND_EVENTS.register(modBus);
    }

    /**
     * Handles any slave mappings between different vanilla registries.
     */
    protected static void registerSlaveMaps() {
        GeneralRegistrar.ELEMENT_SOUNDS.put(GeneralRegistrar.DRUMSTICK.get(), Util.make(new HashMap<>(), map -> {
            map.put(Blocks.OAK_LOG, GeneralRegistrar.DRUMSTICK_OAK_LOG_HIT.get());
            map.put(Blocks.BIRCH_LOG, GeneralRegistrar.DRUMSTICK_BIRCH_LOG_HIT.get());
            map.put(Blocks.SPRUCE_LOG, GeneralRegistrar.DRUMSTICK_SPRUCE_LOG_HIT.get());
            map.put(Blocks.JUNGLE_LOG, GeneralRegistrar.DRUMSTICK_JUNGLE_LOG_HIT.get());
            map.put(Blocks.ACACIA_LOG, GeneralRegistrar.DRUMSTICK_ACACIA_LOG_HIT.get());
            map.put(Blocks.DARK_OAK_LOG, GeneralRegistrar.DRUMSTICK_DARK_OAK_LOG_HIT.get());
            map.put(Blocks.CRIMSON_STEM, GeneralRegistrar.DRUMSTICK_CRIMSON_STEM_HIT.get());
            map.put(Blocks.WARPED_STEM, GeneralRegistrar.DRUMSTICK_WARPED_STEM_HIT.get());
        }));
    }

    /**
     * Helper method to create a sound event as the names can be equivalent.
     *
     * @param  name The sound name as dictated within {@code sounds.json}.
     * @return      The sound event registry object
     */
    private static RegistryObject<SoundEvent> registerSoundEvent(final String name) {
        return GeneralRegistrar.SOUND_EVENTS.register(name,
                () -> new SoundEvent(new ResourceLocation(ExampleMod.ID, name)));
    }

    /**
     * Grabs the instrument element sound according to the block hit. Returns null
     * if there is no sound present.
     *
     * @param  item     The item hitting the block
     * @param  hitBlock The block being hit
     * @return          The sound played if the block is hit, an optional otherwise
     */
    public static Optional<SoundEvent> getInstrumentElementSounds(final InstrumentElementItem item,
            final Block hitBlock) {
        return Optional
                .ofNullable(GeneralRegistrar.ELEMENT_SOUNDS.getOrDefault(item, Collections.emptyMap()).get(hitBlock));
    }
}
