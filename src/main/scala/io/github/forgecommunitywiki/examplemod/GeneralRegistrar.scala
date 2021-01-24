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

import scala.jdk.CollectionConverters._
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.RegistryObject
import net.minecraft.util.SoundEvent
import net.minecraft.util.ResourceLocation
import io.github.forgecommunitywiki.examplemod.item.InstrumentItem
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.block.Blocks
import io.github.forgecommunitywiki.examplemod.loot.ReplaceLootModifier
import io.github.forgecommunitywiki.examplemod.potion.DamageEffect
import net.minecraft.potion.EffectType
import net.minecraft.util.DamageSource
import net.minecraft.item.Food
import net.minecraft.potion.EffectInstance
import net.minecraft.potion.Effects
import net.minecraft.util.SoundEvents
import io.github.forgecommunitywiki.examplemod.item.GlobalInstrumentItem
import io.github.forgecommunitywiki.examplemod.block.RotatedInstrumentBlock
import io.github.forgecommunitywiki.examplemod.util.GeneralHelper
import net.minecraft.util.math.shapes.VoxelShapes
import net.minecraft.block.AbstractBlock
import net.minecraftforge.common.ToolType
import io.github.forgecommunitywiki.examplemod.item.WrappedBlockItem

/**
 * A class used to handle all registry objects added by this mod.
 */
object GeneralRegistrar {

    // Registers
    private final val BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ExampleMod.ID)
    private final val ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExampleMod.ID)
    private final val EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, ExampleMod.ID)
    private final val SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ExampleMod.ID)
    private final val LOOT_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, ExampleMod.ID)

    // Voxel Shapes
    private final val DRUM_SHAPES = GeneralHelper.createAxisShapes(VoxelShapes.or(Block.makeCuboidShape(1d, 0d, 1d, 15d, 16d, 15d),
        Block.makeCuboidShape(5d, 0d, 0d, 11d, 16d, 16d),
        Block.makeCuboidShape(0d, 0d, 5d, 16d, 16d, 11d)))

    // Item Properties
    private final val DECORATIONS = new Item.Properties().group(ItemGroup.DECORATIONS)
    private final val DRUMSTICK_PROPERTIES = new Item.Properties().group(ItemGroup.MISC).maxStackSize(2)

    // Damage Sources
    final val INTERNAL_HEMORRHAGE_SOURCE = new DamageSource("internal_hemorrhage").setDamageBypassesArmor

    // Foods
    final val CHICKEN_LEG_FOOD = new Food.Builder().hunger(1).saturation(0.1f)
        .effect(() => new EffectInstance(Effects.HUNGER, 600, 0), 0.4f).meat.build
    final val COOKED_CHICKEN_LEG_FOOD = new Food.Builder().hunger(3).saturation(0.3f).meat.build
    final val CHICKEN_DRUMSTICK_FOOD = new Food.Builder().hunger(2).saturation(0.2f)
        .effect(() => new EffectInstance(INTERNAL_HEMORRHAGE.get(), 400, 1, false, false), 0.8f)
        .meat.build
    final val COOKED_CHICKEN_DRUMSTICK_FOOD = new Food.Builder().hunger(4).saturation(0.3f)
        .effect(() => new EffectInstance(INTERNAL_HEMORRHAGE.get(), 300, 0, false, false), 0.6f)
        .meat.build

    // Blocks
    final val OAK_LOG_DRUM = registerRotatedInstrumentBlock("oak_log_drum", Blocks.OAK_LOG)
    final val BIRCH_LOG_DRUM = registerRotatedInstrumentBlock("birch_log_drum", Blocks.BIRCH_LOG)
    final val SPRUCE_LOG_DRUM = registerRotatedInstrumentBlock("spruce_log_drum", Blocks.SPRUCE_LOG)
    final val JUNGLE_LOG_DRUM = registerRotatedInstrumentBlock("jungle_log_drum", Blocks.JUNGLE_LOG)
    final val ACACIA_LOG_DRUM = registerRotatedInstrumentBlock("acacia_log_drum", Blocks.ACACIA_LOG)
    final val DARK_OAK_LOG_DRUM = registerRotatedInstrumentBlock("dark_oak_log_drum", Blocks.DARK_OAK_LOG)
    final val CRIMSON_STEM_DRUM = registerRotatedInstrumentBlock("crimson_stem_drum", Blocks.CRIMSON_STEM)
    final val WARPED_STEM_DRUM = registerRotatedInstrumentBlock("warped_stem_drum", Blocks.WARPED_STEM)

    // Items
    final val DRUMSTICK = ITEMS.register("drumstick", () =>
        new InstrumentItem(300, DRUMSTICK_PROPERTIES))
    final val CHICKEN_LEG = ITEMS.register("chicken_leg", () =>
        new Item(new Item.Properties().group(ItemGroup.FOOD).food(CHICKEN_LEG_FOOD)))
    final val COOKED_CHICKEN_LEG = ITEMS.register("cooked_chicken_leg", () =>
        new Item(new Item.Properties().group(ItemGroup.FOOD).food(COOKED_CHICKEN_LEG_FOOD)))
    final val CHICKEN_DRUMSTICK = ITEMS.register("chicken_drumstick", () =>
        new GlobalInstrumentItem(SoundEvents.ENTITY_CHICKEN_HURT, new Item.Properties().group(ItemGroup.MISC).maxStackSize(2).food(CHICKEN_DRUMSTICK_FOOD)))
    final val COOKED_CHICKEN_DRUMSTICK = ITEMS.register("cooked_chicken_drumstick", () =>
        new GlobalInstrumentItem(SoundEvents.ENTITY_CHICKEN_AMBIENT, new Item.Properties().group(ItemGroup.MISC).maxStackSize(2).food(COOKED_CHICKEN_DRUMSTICK_FOOD)))
    final val OAK_LOG_DRUM_DRUMSTICK = ITEMS.register("oak_log_drum_drumstick", () => 
        new GlobalInstrumentItem(DRUMSTICK_OAK_LOG_HIT.get, 500, DRUMSTICK_PROPERTIES))
    final val BIRCH_LOG_DRUM_DRUMSTICK = ITEMS.register("birch_log_drum_drumstick", () => 
        new GlobalInstrumentItem(DRUMSTICK_BIRCH_LOG_HIT.get, 500, DRUMSTICK_PROPERTIES))
    final val SPRUCE_LOG_DRUM_DRUMSTICK = ITEMS.register("spruce_log_drum_drumstick", () => 
        new GlobalInstrumentItem(DRUMSTICK_SPRUCE_LOG_HIT.get, 500, DRUMSTICK_PROPERTIES))
    final val JUNGLE_LOG_DRUM_DRUMSTICK = ITEMS.register("jungle_log_drum_drumstick", () => 
        new GlobalInstrumentItem(DRUMSTICK_JUNGLE_LOG_HIT.get, 500, DRUMSTICK_PROPERTIES))
    final val ACACIA_LOG_DRUM_DRUMSTICK = ITEMS.register("acacia_log_drum_drumstick", () => 
        new GlobalInstrumentItem(DRUMSTICK_ACACIA_LOG_HIT.get, 500, DRUMSTICK_PROPERTIES))
    final val DARK_OAK_LOG_DRUM_DRUMSTICK = ITEMS.register("dark_oak_log_drum_drumstick", () => 
        new GlobalInstrumentItem(DRUMSTICK_DARK_OAK_LOG_HIT.get, 500, DRUMSTICK_PROPERTIES))
    final val CRIMSON_STEM_DRUM_DRUMSTICK = ITEMS.register("crimson_stem_drum_drumstick", () => 
        new GlobalInstrumentItem(DRUMSTICK_CRIMSON_STEM_HIT.get, 150, DRUMSTICK_PROPERTIES))
    final val WARPED_STEM_DRUM_DRUMSTICK = ITEMS.register("warped_stem_drum_drumstick", () => 
        new GlobalInstrumentItem(DRUMSTICK_WARPED_STEM_HIT.get, 150, DRUMSTICK_PROPERTIES))

    // Effects
    final val INTERNAL_HEMORRHAGE = EFFECTS.register("internal_hemorrhage", () =>
        new DamageEffect(EffectType.HARMFUL, 0x9F0000, INTERNAL_HEMORRHAGE_SOURCE, 40, false))

    // Sound Events
    final val DRUMSTICK_OAK_LOG_HIT = registerSoundEvent("instrument.drumstick.oak_log")
    final val DRUMSTICK_BIRCH_LOG_HIT = registerSoundEvent("instrument.drumstick.birch_log")
    final val DRUMSTICK_SPRUCE_LOG_HIT = registerSoundEvent("instrument.drumstick.spruce_log")
    final val DRUMSTICK_JUNGLE_LOG_HIT = registerSoundEvent("instrument.drumstick.jungle_log")
    final val DRUMSTICK_ACACIA_LOG_HIT = registerSoundEvent("instrument.drumstick.acacia_log")
    final val DRUMSTICK_DARK_OAK_LOG_HIT = registerSoundEvent("instrument.drumstick.dark_oak_log")
    final val DRUMSTICK_CRIMSON_STEM_HIT = registerSoundEvent("instrument.drumstick.crimson_stem")
    final val DRUMSTICK_WARPED_STEM_HIT = registerSoundEvent("instrument.drumstick.warped_stem")
    final val DRUM_TEST_HIT = registerSoundEvent("instrument.drum.test")

    // Global Loot Modifiers
    final val REPLACE_LOOT = LOOT_MODIFIER_SERIALIZERS.register("replace", () => new ReplaceLootModifier.Serializer())

    // Slave Maps
    private final lazy val ELEMENT_SOUNDS = Map(
        DRUMSTICK.get -> Map(
            Blocks.OAK_LOG -> DRUMSTICK_OAK_LOG_HIT.get,
            Blocks.BIRCH_LOG -> DRUMSTICK_BIRCH_LOG_HIT.get,
            Blocks.SPRUCE_LOG -> DRUMSTICK_SPRUCE_LOG_HIT.get,
            Blocks.JUNGLE_LOG -> DRUMSTICK_JUNGLE_LOG_HIT.get,
            Blocks.ACACIA_LOG -> DRUMSTICK_ACACIA_LOG_HIT.get,
            Blocks.DARK_OAK_LOG -> DRUMSTICK_DARK_OAK_LOG_HIT.get,
            Blocks.CRIMSON_STEM -> DRUMSTICK_CRIMSON_STEM_HIT.get,
            Blocks.WARPED_STEM -> DRUMSTICK_WARPED_STEM_HIT.get
        )
    )

    /**
     * Registers the {@link DeferredRegister}s to the event bus.
     *
     * @param modBus The associated mod event bus
     */
    def register(modBus: IEventBus): Unit = {
        BLOCKS.register(modBus)
        ITEMS.register(modBus)
        EFFECTS.register(modBus)
        SOUND_EVENTS.register(modBus)
        LOOT_MODIFIER_SERIALIZERS.register(modBus)
    }

    /**
     * Handles any slave mappings between different vanilla registries.
     *
     * Does not need to be synchronized as the map is thread-safe and
     * there are no modifications that can happen at this time.
     */
    def registerSlaveMaps: Unit = {
        ELEMENT_SOUNDS.values
    }

    /**
     * @return A collection of registered blocks.
     */
    def getBlocks = BLOCKS.getEntries().asScala

    /**
     * Creates a rotated instrument block using the specified material.
     *
     * @param name     The registry name of the block
     * @param material The material of the instrument block
     * @return The block registry object
     */
    private def registerRotatedInstrumentBlock(name: String, material: => Block): RegistryObject[RotatedInstrumentBlock] =
        registerBlock(name, () => {
            val block = material
            new RotatedInstrumentBlock(block.getDefaultState(), DRUM_SHAPES, AbstractBlock.Properties.from(block).harvestLevel(0).harvestTool(ToolType.AXE))
        }, new WrappedBlockItem(_, DECORATIONS))

    /**
     * Helper method to create a block with an associated item if applicable.
     *
     * @param <T>   A block type
     * @param name  The registry name of the block
     * @param block A supplied, new block instance
     * @param item  A function that takes in the block and outputs a new item
     *               instance
     * @return The block registry object
     */
    private def registerBlock[T <: Block](name: String, block: () => T, item: (T) => Item = null): RegistryObject[T] = {
        val obj = BLOCKS.register(name, () => block.apply())
        if(item != null) ITEMS.register(name, () => item.apply(obj.get))
        obj
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
    def getInstrumentElementSounds(item: InstrumentItem, hitBlock: Block): Option[SoundEvent] =
        ELEMENT_SOUNDS.getOrElse(item, Map()).get(hitBlock)
}
