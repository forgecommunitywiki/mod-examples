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

import io.github.forgecommunitywiki.examplemod.block.RotatedInstrumentBlock
import io.github.forgecommunitywiki.examplemod.item.GlobalInstrumentItem
import io.github.forgecommunitywiki.examplemod.item.InstrumentItem
import io.github.forgecommunitywiki.examplemod.item.WrappedBlockItem
import io.github.forgecommunitywiki.examplemod.loot.ReplaceLootModifier
import io.github.forgecommunitywiki.examplemod.potion.DamageEffect
import io.github.forgecommunitywiki.examplemod.util.createAxisShapes
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.item.Food
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.potion.Effect
import net.minecraft.potion.EffectInstance
import net.minecraft.potion.EffectType
import net.minecraft.potion.Effects
import net.minecraft.util.DamageSource
import net.minecraft.util.ResourceLocation
import net.minecraft.util.SoundEvent
import net.minecraft.util.SoundEvents
import net.minecraft.util.math.shapes.VoxelShapes
import net.minecraftforge.common.ToolType
import net.minecraftforge.common.loot.GlobalLootModifierSerializer
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

// Registers
private val BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID)
private val ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID)
private val EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, MOD_ID)
private val SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MOD_ID)
private val LOOT_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, MOD_ID)

// Voxel Shapes
private val DRUM_SHAPES = createAxisShapes(VoxelShapes.or(Block.makeCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0),
    Block.makeCuboidShape(5.0, 0.0, 0.0, 11.0, 16.0, 16.0),
    Block.makeCuboidShape(0.0, 0.0, 5.0, 16.0, 16.0, 11.0)))

// Item Properties
private val DECORATIONS = Item.Properties().group(ItemGroup.DECORATIONS)
private val DRUMSTICK_PROPERTIES = Item.Properties().group(ItemGroup.MISC).maxStackSize(2)

// Damage Sources
val INTERNAL_HEMORRHAGE_SOURCE: DamageSource = DamageSource("internal_hemorrhage").setDamageBypassesArmor()

// Foods
val CHICKEN_LEG_FOOD: Food = Food.Builder().hunger(1).saturation(0.1f)
    .effect({ EffectInstance(Effects.HUNGER, 600, 0) }, 0.4f).meat().build()
val COOKED_CHICKEN_LEG_FOOD: Food = Food.Builder().hunger(3).saturation(0.3f).meat().build()
val CHICKEN_DRUMSTICK_FOOD: Food = Food.Builder().hunger(2).saturation(0.2f)
    .effect({ EffectInstance(INTERNAL_HEMORRHAGE.get(), 400, 1, false, false) }, 0.8f)
    .meat().build()
val COOKED_CHICKEN_DRUMSTICK_FOOD: Food = Food.Builder().hunger(4).saturation(0.3f)
    .effect({ EffectInstance(INTERNAL_HEMORRHAGE.get(), 300, 0, false, false) }, 0.6f)
    .meat().build()

// Blocks
val OAK_LOG_DRUM = registerRotatedInstrumentBlock("oak_log_drum") { Blocks.OAK_LOG }
val BIRCH_LOG_DRUM = registerRotatedInstrumentBlock("birch_log_drum") { Blocks.BIRCH_LOG }
val SPRUCE_LOG_DRUM = registerRotatedInstrumentBlock("spruce_log_drum") { Blocks.SPRUCE_LOG }
val JUNGLE_LOG_DRUM = registerRotatedInstrumentBlock("jungle_log_drum") { Blocks.JUNGLE_LOG }
val ACACIA_LOG_DRUM = registerRotatedInstrumentBlock("acacia_log_drum") { Blocks.ACACIA_LOG }
val DARK_OAK_LOG_DRUM = registerRotatedInstrumentBlock("dark_oak_log_drum") { Blocks.DARK_OAK_LOG }
val CRIMSON_STEM_DRUM = registerRotatedInstrumentBlock("crimson_stem_drum") { Blocks.CRIMSON_STEM }
val WARPED_STEM_DRUM = registerRotatedInstrumentBlock("warped_stem_drum") { Blocks.WARPED_STEM }

// Items
val DRUMSTICK: RegistryObject<InstrumentItem> = ITEMS.register("drumstick") {
    InstrumentItem(300, DRUMSTICK_PROPERTIES)
}
val CHICKEN_LEG: RegistryObject<Item> = ITEMS.register("chicken_leg") {
    Item(Item.Properties().group(ItemGroup.FOOD).food(CHICKEN_LEG_FOOD))
}
val COOKED_CHICKEN_LEG: RegistryObject<Item> = ITEMS.register("cooked_chicken_leg") {
    Item(Item.Properties().group(ItemGroup.FOOD).food(COOKED_CHICKEN_LEG_FOOD))
}
val CHICKEN_DRUMSTICK: RegistryObject<GlobalInstrumentItem> = ITEMS.register("chicken_drumstick") {
    GlobalInstrumentItem(lazy { SoundEvents.ENTITY_CHICKEN_HURT }, Item.Properties().group(ItemGroup.MISC).maxStackSize(2).food(CHICKEN_DRUMSTICK_FOOD))
}
val COOKED_CHICKEN_DRUMSTICK: RegistryObject<GlobalInstrumentItem> = ITEMS.register("cooked_chicken_drumstick") {
    GlobalInstrumentItem(lazy { SoundEvents.ENTITY_CHICKEN_AMBIENT }, Item.Properties().group(ItemGroup.MISC).maxStackSize(2).food(COOKED_CHICKEN_DRUMSTICK_FOOD))
}
val OAK_LOG_DRUM_DRUMSTICK: RegistryObject<GlobalInstrumentItem> = ITEMS.register("oak_log_drum_drumstick") {
    GlobalInstrumentItem(lazy { DRUMSTICK_OAK_LOG_HIT.get() }, 500, DRUMSTICK_PROPERTIES)
}
val BIRCH_LOG_DRUM_DRUMSTICK: RegistryObject<GlobalInstrumentItem> = ITEMS.register("birch_log_drum_drumstick") {
    GlobalInstrumentItem(lazy { DRUMSTICK_BIRCH_LOG_HIT.get() }, 500, DRUMSTICK_PROPERTIES)
}
val SPRUCE_LOG_DRUM_DRUMSTICK: RegistryObject<GlobalInstrumentItem> = ITEMS.register("spruce_log_drum_drumstick") {
    GlobalInstrumentItem(lazy { DRUMSTICK_SPRUCE_LOG_HIT.get() }, 500, DRUMSTICK_PROPERTIES)
}
val JUNGLE_LOG_DRUM_DRUMSTICK: RegistryObject<GlobalInstrumentItem> = ITEMS.register("jungle_log_drum_drumstick") {
    GlobalInstrumentItem(lazy { DRUMSTICK_JUNGLE_LOG_HIT.get() }, 500, DRUMSTICK_PROPERTIES)
}
val ACACIA_LOG_DRUM_DRUMSTICK: RegistryObject<GlobalInstrumentItem> = ITEMS.register("acacia_log_drum_drumstick") {
    GlobalInstrumentItem(lazy { DRUMSTICK_ACACIA_LOG_HIT.get() }, 500, DRUMSTICK_PROPERTIES)
}
val DARK_OAK_LOG_DRUM_DRUMSTICK: RegistryObject<GlobalInstrumentItem> = ITEMS.register("dark_oak_log_drum_drumstick") {
    GlobalInstrumentItem(lazy { DRUMSTICK_DARK_OAK_LOG_HIT.get() }, 500, DRUMSTICK_PROPERTIES)
}
val CRIMSON_STEM_DRUM_DRUMSTICK: RegistryObject<GlobalInstrumentItem> = ITEMS.register("crimson_stem_drum_drumstick") {
    GlobalInstrumentItem(lazy { DRUMSTICK_CRIMSON_STEM_HIT.get() }, 150, DRUMSTICK_PROPERTIES)
}
val WARPED_STEM_DRUM_DRUMSTICK: RegistryObject<GlobalInstrumentItem> = ITEMS.register("warped_stem_drum_drumstick") {
    GlobalInstrumentItem(lazy { DRUMSTICK_WARPED_STEM_HIT.get() }, 150, DRUMSTICK_PROPERTIES)
}

// Effects
val INTERNAL_HEMORRHAGE: RegistryObject<Effect> = EFFECTS.register("internal_hemorrhage") {
    DamageEffect(EffectType.HARMFUL, 0x9F0000, INTERNAL_HEMORRHAGE_SOURCE, 40, false)
}

// Sound Events
val DRUMSTICK_OAK_LOG_HIT = registerSoundEvent("instrument.drumstick.oak_log")
val DRUMSTICK_BIRCH_LOG_HIT = registerSoundEvent("instrument.drumstick.birch_log")
val DRUMSTICK_SPRUCE_LOG_HIT = registerSoundEvent("instrument.drumstick.spruce_log")
val DRUMSTICK_JUNGLE_LOG_HIT = registerSoundEvent("instrument.drumstick.jungle_log")
val DRUMSTICK_ACACIA_LOG_HIT = registerSoundEvent("instrument.drumstick.acacia_log")
val DRUMSTICK_DARK_OAK_LOG_HIT = registerSoundEvent("instrument.drumstick.dark_oak_log")
val DRUMSTICK_CRIMSON_STEM_HIT = registerSoundEvent("instrument.drumstick.crimson_stem")
val DRUMSTICK_WARPED_STEM_HIT = registerSoundEvent("instrument.drumstick.warped_stem")
val DRUM_TEST_HIT = registerSoundEvent("instrument.drum.test")

// Global Loot Modifiers
val REPLACE_LOOT: RegistryObject<GlobalLootModifierSerializer<ReplaceLootModifier>> = LOOT_MODIFIER_SERIALIZERS.register("replace", ReplaceLootModifier::Serializer)

// Slave Maps
private val ELEMENT_SOUNDS by lazy {
    mapOf(
        DRUMSTICK.get() to mapOf(
            Blocks.OAK_LOG to DRUMSTICK_OAK_LOG_HIT.get(),
            Blocks.BIRCH_LOG to DRUMSTICK_BIRCH_LOG_HIT.get(),
            Blocks.SPRUCE_LOG to DRUMSTICK_SPRUCE_LOG_HIT.get(),
            Blocks.JUNGLE_LOG to DRUMSTICK_JUNGLE_LOG_HIT.get(),
            Blocks.ACACIA_LOG to DRUMSTICK_ACACIA_LOG_HIT.get(),
            Blocks.DARK_OAK_LOG to DRUMSTICK_DARK_OAK_LOG_HIT.get(),
            Blocks.CRIMSON_STEM to DRUMSTICK_CRIMSON_STEM_HIT.get(),
            Blocks.WARPED_STEM to DRUMSTICK_WARPED_STEM_HIT.get()
        )
    )
}

/**
 * Registers the {@link DeferredRegister}s to the event bus.
 */
internal fun register(modBus: IEventBus) {
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
internal fun registerSlaveMaps() {
    ELEMENT_SOUNDS.values
}

/**
 * @return A collection of registered blocks.
 */
internal fun getBlocks(): Collection<RegistryObject<Block>> = BLOCKS.entries

/**
 * Creates a rotated instrument block using the specified material.
 *
 * @param name     The registry name of the block
 * @param material The material of the instrument block
 * @return The block registry object
 */
private fun registerRotatedInstrumentBlock(name: String, material: () -> Block): RegistryObject<RotatedInstrumentBlock> =
    registerBlock(name, {
        val block = material.invoke()
        RotatedInstrumentBlock(lazy { block.defaultState }, DRUM_SHAPES, AbstractBlock.Properties.from(block).harvestLevel(0).harvestTool(
            ToolType.AXE))
    }) { WrappedBlockItem(it, DECORATIONS) }

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
private fun <T : Block> registerBlock(name: String, block: () -> T, item: ((T) -> Item)?): RegistryObject<T> {
    val obj = BLOCKS.register(name, block)
    if (item != null) ITEMS.register(name) { item.invoke(obj.get()) }
    return obj
}

/**
 * Helper method to create a sound event as the names can be equivalent.
 *
 * @param name The sound name as dictated within {@code sounds.json}.
 * @return The sound event registry object
 */
private fun registerSoundEvent(name: String): RegistryObject<SoundEvent> =
    SOUND_EVENTS.register(name) { SoundEvent(ResourceLocation(MOD_ID, name)) }

/**
 * Grabs the instrument element sound according to the block hit. Returns null
 * if there is no sound present.
 *
 * @param item The item hitting the block
 * @param hitBlock The block being hit
 * @return The sound played if the block is hit, null otherwise
 */
fun getInstrumentElementSounds(item: InstrumentItem, hitBlock: Block): SoundEvent? =
    ELEMENT_SOUNDS.getOrDefault(item, mapOf())[hitBlock]
