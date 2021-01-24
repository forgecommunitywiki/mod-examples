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
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import io.github.forgecommunitywiki.examplemod.block.RotatedInstrumentBlock;
import io.github.forgecommunitywiki.examplemod.item.*;
import io.github.forgecommunitywiki.examplemod.loot.ReplaceLootModifier;
import io.github.forgecommunitywiki.examplemod.potion.DamageEffect;
import io.github.forgecommunitywiki.examplemod.util.GeneralHelper;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.util.Lazy;
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

    // Voxel Shapes
    private static final Map<Axis, VoxelShape> DRUM_SHAPES = GeneralHelper
            .createAxisShapes(VoxelShapes.or(Block.makeCuboidShape(1, 0, 1, 15, 16, 15),
                    Block.makeCuboidShape(5, 0, 0, 11, 16, 16), Block.makeCuboidShape(0, 0, 5, 16, 16, 11)));

    // Item Properties
    private static final Item.Properties DECORATIONS = new Item.Properties().group(ItemGroup.DECORATIONS);
    private static final Item.Properties DRUMSTICK_PROPERTIES = new Item.Properties().group(ItemGroup.MISC)
            .maxStackSize(2);

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

    // Blocks
    public static final RegistryObject<RotatedInstrumentBlock> OAK_LOG_DRUM = GeneralRegistrar.registerRotatedInstrumentBlock(
            "oak_log_drum", () -> Blocks.OAK_LOG);
    public static final RegistryObject<RotatedInstrumentBlock> BIRCH_LOG_DRUM = GeneralRegistrar.registerRotatedInstrumentBlock(
            "birch_log_drum", () -> Blocks.BIRCH_LOG);
    public static final RegistryObject<RotatedInstrumentBlock> SPRUCE_LOG_DRUM = GeneralRegistrar.registerRotatedInstrumentBlock(
            "spruce_log_drum", () -> Blocks.SPRUCE_LOG);
    public static final RegistryObject<RotatedInstrumentBlock> JUNGLE_LOG_DRUM = GeneralRegistrar.registerRotatedInstrumentBlock(
            "jungle_log_drum", () -> Blocks.JUNGLE_LOG);
    public static final RegistryObject<RotatedInstrumentBlock> ACACIA_LOG_DRUM = GeneralRegistrar.registerRotatedInstrumentBlock(
            "acacia_log_drum", () -> Blocks.ACACIA_LOG);
    public static final RegistryObject<RotatedInstrumentBlock> DARK_OAK_LOG_DRUM = GeneralRegistrar
            .registerRotatedInstrumentBlock("dark_oak_log_drum", () -> Blocks.DARK_OAK_LOG);
    public static final RegistryObject<RotatedInstrumentBlock> CRIMSON_STEM_DRUM = GeneralRegistrar
            .registerRotatedInstrumentBlock("crimson_stem_drum", () -> Blocks.CRIMSON_STEM);
    public static final RegistryObject<RotatedInstrumentBlock> WARPED_STEM_DRUM = GeneralRegistrar.registerRotatedInstrumentBlock(
            "warped_stem_drum", () -> Blocks.WARPED_STEM);

    // Items
    public static final RegistryObject<InstrumentItem> DRUMSTICK = GeneralRegistrar.ITEMS.register("drumstick",
            () -> new InstrumentItem(300, GeneralRegistrar.DRUMSTICK_PROPERTIES));
    public static final RegistryObject<Item> CHICKEN_LEG = GeneralRegistrar.ITEMS.register("chicken_leg",
            () -> new Item(new Item.Properties().group(ItemGroup.FOOD).food(GeneralRegistrar.CHICKEN_LEG_FOOD)));
    public static final RegistryObject<Item> COOKED_CHICKEN_LEG = GeneralRegistrar.ITEMS.register("cooked_chicken_leg",
            () -> new Item(new Item.Properties().group(ItemGroup.FOOD).food(GeneralRegistrar.COOKED_CHICKEN_LEG_FOOD)));
    public static final RegistryObject<GlobalInstrumentItem> CHICKEN_DRUMSTICK = GeneralRegistrar.ITEMS.register(
            "chicken_drumstick",
            () -> new GlobalInstrumentItem(() -> SoundEvents.ENTITY_CHICKEN_HURT, new Item.Properties()
                    .group(ItemGroup.MISC).maxStackSize(2).food(GeneralRegistrar.CHICKEN_DRUMSTICK_FOOD)));
    public static final RegistryObject<GlobalInstrumentItem> COOKED_CHICKEN_DRUMSTICK = GeneralRegistrar.ITEMS.register(
            "cooked_chicken_drumstick",
            () -> new GlobalInstrumentItem(() -> SoundEvents.ENTITY_CHICKEN_AMBIENT, new Item.Properties()
                    .group(ItemGroup.MISC).maxStackSize(2).food(GeneralRegistrar.COOKED_CHICKEN_DRUMSTICK_FOOD)));
    public static final RegistryObject<GlobalInstrumentItem> OAK_LOG_DRUM_DRUMSTICK = GeneralRegistrar.ITEMS
            .register("oak_log_drum_drumstick", () -> new GlobalInstrumentItem(GeneralRegistrar.DRUMSTICK_OAK_LOG_HIT,
                    500, GeneralRegistrar.DRUMSTICK_PROPERTIES));
    public static final RegistryObject<GlobalInstrumentItem> BIRCH_LOG_DRUM_DRUMSTICK = GeneralRegistrar.ITEMS.register(
            "birch_log_drum_drumstick", () -> new GlobalInstrumentItem(GeneralRegistrar.DRUMSTICK_BIRCH_LOG_HIT, 500,
                    GeneralRegistrar.DRUMSTICK_PROPERTIES));
    public static final RegistryObject<GlobalInstrumentItem> SPRUCE_LOG_DRUM_DRUMSTICK = GeneralRegistrar.ITEMS
            .register("spruce_log_drum_drumstick",
                    () -> new GlobalInstrumentItem(GeneralRegistrar.DRUMSTICK_SPRUCE_LOG_HIT, 500,
                            GeneralRegistrar.DRUMSTICK_PROPERTIES));
    public static final RegistryObject<GlobalInstrumentItem> JUNGLE_LOG_DRUM_DRUMSTICK = GeneralRegistrar.ITEMS
            .register("jungle_log_drum_drumstick",
                    () -> new GlobalInstrumentItem(GeneralRegistrar.DRUMSTICK_JUNGLE_LOG_HIT, 500,
                            GeneralRegistrar.DRUMSTICK_PROPERTIES));
    public static final RegistryObject<GlobalInstrumentItem> ACACIA_LOG_DRUM_DRUMSTICK = GeneralRegistrar.ITEMS
            .register("acacia_log_drum_drumstick",
                    () -> new GlobalInstrumentItem(GeneralRegistrar.DRUMSTICK_ACACIA_LOG_HIT, 500,
                            GeneralRegistrar.DRUMSTICK_PROPERTIES));
    public static final RegistryObject<GlobalInstrumentItem> DARK_OAK_LOG_DRUM_DRUMSTICK = GeneralRegistrar.ITEMS
            .register("dark_oak_log_drum_drumstick",
                    () -> new GlobalInstrumentItem(GeneralRegistrar.DRUMSTICK_DARK_OAK_LOG_HIT, 500,
                            GeneralRegistrar.DRUMSTICK_PROPERTIES));
    public static final RegistryObject<GlobalInstrumentItem> CRIMSON_STEM_DRUM_DRUMSTICK = GeneralRegistrar.ITEMS
            .register("crimson_stem_drum_drumstick",
                    () -> new GlobalInstrumentItem(GeneralRegistrar.DRUMSTICK_CRIMSON_STEM_HIT, 150,
                            GeneralRegistrar.DRUMSTICK_PROPERTIES));
    public static final RegistryObject<GlobalInstrumentItem> WARPED_STEM_DRUM_DRUMSTICK = GeneralRegistrar.ITEMS
            .register("warped_stem_drum_drumstick",
                    () -> new GlobalInstrumentItem(GeneralRegistrar.DRUMSTICK_WARPED_STEM_HIT, 150,
                            GeneralRegistrar.DRUMSTICK_PROPERTIES));

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
    public static final RegistryObject<SoundEvent> DRUM_TEST_HIT = GeneralRegistrar
            .registerSoundEvent("instrument.drum.test");

    // Global Loot Modifiers
    public static final RegistryObject<GlobalLootModifierSerializer<ReplaceLootModifier>> REPLACE_LOOT = GeneralRegistrar.LOOT_MODIFIER_SERIALIZERS
            .register("replace", ReplaceLootModifier.Serializer::new);

    // Slave Maps
    private static final Map<InstrumentItem, Map<Block, SoundEvent>> ELEMENT_SOUNDS = new HashMap<>();

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
    }

    /**
     * @return A collection of registered blocks.
     */
    public static Collection<RegistryObject<Block>> getBlocks() { return GeneralRegistrar.BLOCKS.getEntries(); }

    /**
     * Creates a rotated instrument block using the specified material.
     *
     * @param  name     The registry name of the block
     * @param  material The material of the instrument block
     * @return          The block registry object
     */
    private static RegistryObject<RotatedInstrumentBlock> registerRotatedInstrumentBlock(final String name,
            final Supplier<? extends Block> material) {
        return GeneralRegistrar.registerBlock(name, () -> {
            final Block block = material.get();
            return new RotatedInstrumentBlock(Lazy.of(() -> block.getDefaultState()), GeneralRegistrar.DRUM_SHAPES,
                    AbstractBlock.Properties.from(block).harvestLevel(0).harvestTool(ToolType.AXE));
        }, b -> new WrappedBlockItem<>(b, GeneralRegistrar.DECORATIONS));
    }

    /**
     * Helper method to create a block with an associated item if applicable.
     *
     * @param  <T>   A block type
     * @param  name  The registry name of the block
     * @param  block A supplied, new block instance
     * @param  item  A function that takes in the block and outputs a new item
     *               instance
     * @return       The block registry object
     */
    private static <T extends Block> RegistryObject<T> registerBlock(final String name, final Supplier<T> block,
            @Nullable final Function<T, Item> item) {
        final RegistryObject<T> obj = GeneralRegistrar.BLOCKS.register(name, block);
        if (item != null)
            GeneralRegistrar.ITEMS.register(name, () -> item.apply(obj.get()));
        return obj;
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
    public static Optional<SoundEvent> getInstrumentElementSounds(final InstrumentItem item, final Block hitBlock) {
        return Optional
                .ofNullable(GeneralRegistrar.ELEMENT_SOUNDS.getOrDefault(item, Collections.emptyMap()).get(hitBlock));
    }
}
