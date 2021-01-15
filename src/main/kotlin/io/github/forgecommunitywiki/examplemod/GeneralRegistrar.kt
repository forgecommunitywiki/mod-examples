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
import io.github.forgecommunitywiki.examplemod.loot.ReplaceLootModifier
import io.github.forgecommunitywiki.examplemod.potion.DamageEffect
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.potion.EffectType
import net.minecraft.util.DamageSource
import net.minecraft.util.ResourceLocation
import net.minecraft.util.SoundEvent
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraft.potion.EffectInstance

import net.minecraft.item.Food
import net.minecraft.item.Item
import net.minecraft.potion.Effect

import net.minecraft.potion.Effects
import net.minecraft.util.SoundEvents
import net.minecraftforge.common.loot.GlobalLootModifierSerializer


// Registers
private val BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID)
private val ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID)
private val EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, MOD_ID)
private val SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MOD_ID)
private val LOOT_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, MOD_ID)

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
    override fun getBurnTime(stack: ItemStack?): Int = 300
} }
val CHICKEN_LEG: RegistryObject<Item> = ITEMS.register("chicken_leg")
    { Item(Item.Properties().group(ItemGroup.FOOD).food(CHICKEN_LEG_FOOD)) }
val COOKED_CHICKEN_LEG: RegistryObject<Item> = ITEMS.register("cooked_chicken_leg")
    { Item(Item.Properties().group(ItemGroup.FOOD).food(COOKED_CHICKEN_LEG_FOOD)) }
val CHICKEN_DRUMSTICK: RegistryObject<InstrumentElementItem> = ITEMS.register("chicken_drumstick")
    { InstrumentElementItem(Item.Properties().group(ItemGroup.MISC).maxStackSize(2).food(CHICKEN_DRUMSTICK_FOOD))}
val COOKED_CHICKEN_DRUMSTICK: RegistryObject<InstrumentElementItem> = ITEMS.register("cooked_chicken_drumstick")
    { InstrumentElementItem(Item.Properties().group(ItemGroup.MISC).maxStackSize(2).food(COOKED_CHICKEN_DRUMSTICK_FOOD))}

// Effects
val INTERNAL_HEMORRHAGE: RegistryObject<Effect> = EFFECTS.register("internal_hemorrhage")
    { DamageEffect(EffectType.HARMFUL, 0x9F0000, INTERNAL_HEMORRHAGE_SOURCE, 40, false) }

// Sound Events
val DRUMSTICK_OAK_LOG_HIT = registerSoundEvent("instrument.drumstick.oak_log")
val DRUMSTICK_BIRCH_LOG_HIT = registerSoundEvent("instrument.drumstick.birch_log")
val DRUMSTICK_SPRUCE_LOG_HIT = registerSoundEvent("instrument.drumstick.spruce_log")
val DRUMSTICK_JUNGLE_LOG_HIT = registerSoundEvent("instrument.drumstick.jungle_log")
val DRUMSTICK_ACACIA_LOG_HIT = registerSoundEvent("instrument.drumstick.acacia_log")
val DRUMSTICK_DARK_OAK_LOG_HIT = registerSoundEvent("instrument.drumstick.dark_oak_log")
val DRUMSTICK_CRIMSON_STEM_HIT = registerSoundEvent("instrument.drumstick.crimson_stem")
val DRUMSTICK_WARPED_STEM_HIT = registerSoundEvent("instrument.drumstick.warped_stem")

// Global Loot Modifiers
val REPLACE_LOOT: RegistryObject<GlobalLootModifierSerializer<ReplaceLootModifier>> = LOOT_MODIFIER_SERIALIZERS.register("replace") { ReplaceLootModifier.Serializer() }

// Slave Maps
private val ELEMENT_SOUNDS by lazy { mapOf(DRUMSTICK.get() to mapOf(
    Blocks.OAK_LOG to DRUMSTICK_OAK_LOG_HIT.get(),
    Blocks.BIRCH_LOG to DRUMSTICK_BIRCH_LOG_HIT.get(),
    Blocks.SPRUCE_LOG to DRUMSTICK_SPRUCE_LOG_HIT.get(),
    Blocks.JUNGLE_LOG to DRUMSTICK_JUNGLE_LOG_HIT.get(),
    Blocks.ACACIA_LOG to DRUMSTICK_ACACIA_LOG_HIT.get(),
    Blocks.DARK_OAK_LOG to DRUMSTICK_DARK_OAK_LOG_HIT.get(),
    Blocks.CRIMSON_STEM to DRUMSTICK_CRIMSON_STEM_HIT.get(),
    Blocks.WARPED_STEM to DRUMSTICK_WARPED_STEM_HIT.get()
), CHICKEN_DRUMSTICK.get() to mapOf(
    Blocks.STONE to SoundEvents.ENTITY_CHICKEN_HURT,
    Blocks.GRANITE to SoundEvents.ENTITY_CHICKEN_HURT,
    Blocks.DIORITE to SoundEvents.ENTITY_CHICKEN_HURT,
    Blocks.ANDESITE to SoundEvents.ENTITY_CHICKEN_HURT
), COOKED_CHICKEN_DRUMSTICK.get() to mapOf(
    Blocks.GRASS_BLOCK to SoundEvents.ENTITY_CHICKEN_AMBIENT,
    Blocks.DIRT to SoundEvents.ENTITY_CHICKEN_AMBIENT
)) }

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
 * Helper method to create a sound event as the names can be equivalent.
 *
 * @param  name The sound name as dictated within {@code sounds.json}.
 * @return      The sound event registry object
 */
private fun registerSoundEvent(name: String) : RegistryObject<SoundEvent>
        = SOUND_EVENTS.register(name) { SoundEvent(ResourceLocation(MOD_ID, name)) }

/**
 * Grabs the instrument element sound according to the block hit. Returns null
 * if there is no sound present.
 *
 * @param  item     The item hitting the block
 * @param  hitBlock The block being hit
 * @return          The sound played if the block is hit, null otherwise
 */
fun getInstrumentElementSounds(item: InstrumentElementItem, hitBlock: Block): SoundEvent?
        = ELEMENT_SOUNDS.getOrDefault(item, mapOf())[hitBlock]
