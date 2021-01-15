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
import io.github.forgecommunitywiki.examplemod.loot.ReplaceLootModifier;
import io.github.forgecommunitywiki.examplemod.potion.DamageEffect;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
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
    private static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS,
            ExampleMod.ID);
    private static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister
            .create(ForgeRegistries.SOUND_EVENTS, ExampleMod.ID);
    private static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIER_SERIALIZERS = DeferredRegister
            .create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, ExampleMod.ID);

    // Damage Sources
    public static final DamageSource INTERNAL_HEMORRHAGE_SOURCE = new DamageSource("internal_hemorrhage")
            .setDamageBypassesArmor();

    // Foods
    public static final Food CHICKEN_LEG_FOOD = new Food.Builder().hunger(1).saturation(0.1f)
            .effect(() -> new EffectInstance(Effects.HUNGER, 600, 0), 0.4f).meat().build();
    public static final Food COOKED_CHICKEN_LEG_FOOD = new Food.Builder().hunger(3).saturation(0.3f).meat().build();
    public static final Food CHICKEN_DRUMSTICK_FOOD = new Food.Builder().hunger(2).saturation(0.2f)
            .effect(() -> new EffectInstance(GeneralRegistrar.INTERNAL_HEMORRHAGE.get(), 400, 1, false, false), 0.8f)
            .meat().build();
    public static final Food COOKED_CHICKEN_DRUMSTICK_FOOD = new Food.Builder().hunger(4).saturation(0.3f)
            .effect(() -> new EffectInstance(GeneralRegistrar.INTERNAL_HEMORRHAGE.get(), 300, 0, false, false), 0.6f)
            .meat().build();

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
    public static final RegistryObject<Item> CHICKEN_LEG = GeneralRegistrar.ITEMS.register("chicken_leg",
            () -> new Item(new Item.Properties().group(ItemGroup.FOOD).food(GeneralRegistrar.CHICKEN_LEG_FOOD)));
    public static final RegistryObject<Item> COOKED_CHICKEN_LEG = GeneralRegistrar.ITEMS.register("cooked_chicken_leg",
            () -> new Item(new Item.Properties().group(ItemGroup.FOOD).food(GeneralRegistrar.COOKED_CHICKEN_LEG_FOOD)));
    public static final RegistryObject<InstrumentElementItem> CHICKEN_DRUMSTICK = GeneralRegistrar.ITEMS
            .register("chicken_drumstick", () -> new InstrumentElementItem(new Item.Properties().group(ItemGroup.MISC)
                    .maxStackSize(2).food(GeneralRegistrar.CHICKEN_DRUMSTICK_FOOD)));
    public static final RegistryObject<InstrumentElementItem> COOKED_CHICKEN_DRUMSTICK = GeneralRegistrar.ITEMS
            .register("cooked_chicken_drumstick", () -> new InstrumentElementItem(new Item.Properties()
                    .group(ItemGroup.MISC).maxStackSize(2).food(GeneralRegistrar.COOKED_CHICKEN_DRUMSTICK_FOOD)));

    // Effects
    public static final RegistryObject<Effect> INTERNAL_HEMORRHAGE = GeneralRegistrar.EFFECTS
            .register("internal_hemorrhage", () -> new DamageEffect(EffectType.HARMFUL, 0x9F0000,
                    GeneralRegistrar.INTERNAL_HEMORRHAGE_SOURCE, 40, false));

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

    // Global Loot Modifiers
    public static final RegistryObject<GlobalLootModifierSerializer<ReplaceLootModifier>> REPLACE_LOOT = GeneralRegistrar.LOOT_MODIFIER_SERIALIZERS
            .register("replace", ReplaceLootModifier.Serializer::new);

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
        GeneralRegistrar.EFFECTS.register(modBus);
        GeneralRegistrar.SOUND_EVENTS.register(modBus);
        GeneralRegistrar.LOOT_MODIFIER_SERIALIZERS.register(modBus);
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
        GeneralRegistrar.ELEMENT_SOUNDS.put(GeneralRegistrar.CHICKEN_DRUMSTICK.get(),
                Util.make(new HashMap<>(), map -> {
                    map.put(Blocks.STONE, SoundEvents.ENTITY_CHICKEN_HURT);
                    map.put(Blocks.GRANITE, SoundEvents.ENTITY_CHICKEN_HURT);
                    map.put(Blocks.DIORITE, SoundEvents.ENTITY_CHICKEN_HURT);
                    map.put(Blocks.ANDESITE, SoundEvents.ENTITY_CHICKEN_HURT);
                }));
        GeneralRegistrar.ELEMENT_SOUNDS.put(GeneralRegistrar.COOKED_CHICKEN_DRUMSTICK.get(),
                Util.make(new HashMap<>(), map -> {
                    map.put(Blocks.GRASS_BLOCK, SoundEvents.ENTITY_CHICKEN_AMBIENT);
                    map.put(Blocks.DIRT, SoundEvents.ENTITY_CHICKEN_AMBIENT);
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
