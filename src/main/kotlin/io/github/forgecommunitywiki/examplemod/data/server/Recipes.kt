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

package io.github.forgecommunitywiki.examplemod.data.server

import io.github.forgecommunitywiki.examplemod.ACACIA_LOG_DRUM
import io.github.forgecommunitywiki.examplemod.ACACIA_LOG_DRUM_DRUMSTICK
import io.github.forgecommunitywiki.examplemod.BIRCH_LOG_DRUM
import io.github.forgecommunitywiki.examplemod.BIRCH_LOG_DRUM_DRUMSTICK
import io.github.forgecommunitywiki.examplemod.CHICKEN_DRUMSTICK
import io.github.forgecommunitywiki.examplemod.CHICKEN_LEG
import io.github.forgecommunitywiki.examplemod.COOKED_CHICKEN_DRUMSTICK
import io.github.forgecommunitywiki.examplemod.COOKED_CHICKEN_LEG
import io.github.forgecommunitywiki.examplemod.CRIMSON_STEM_DRUM
import io.github.forgecommunitywiki.examplemod.CRIMSON_STEM_DRUM_DRUMSTICK
import io.github.forgecommunitywiki.examplemod.DARK_OAK_LOG_DRUM
import io.github.forgecommunitywiki.examplemod.DARK_OAK_LOG_DRUM_DRUMSTICK
import io.github.forgecommunitywiki.examplemod.DRUMSTICK
import io.github.forgecommunitywiki.examplemod.JUNGLE_LOG_DRUM
import io.github.forgecommunitywiki.examplemod.JUNGLE_LOG_DRUM_DRUMSTICK
import io.github.forgecommunitywiki.examplemod.OAK_LOG_DRUM
import io.github.forgecommunitywiki.examplemod.OAK_LOG_DRUM_DRUMSTICK
import io.github.forgecommunitywiki.examplemod.SPRUCE_LOG_DRUM
import io.github.forgecommunitywiki.examplemod.SPRUCE_LOG_DRUM_DRUMSTICK
import io.github.forgecommunitywiki.examplemod.WARPED_STEM_DRUM
import io.github.forgecommunitywiki.examplemod.WARPED_STEM_DRUM_DRUMSTICK
import net.minecraft.block.Blocks
import net.minecraft.data.CookingRecipeBuilder
import net.minecraft.data.DataGenerator
import net.minecraft.data.IFinishedRecipe
import net.minecraft.data.RecipeProvider
import net.minecraft.data.ShapedRecipeBuilder
import net.minecraft.item.Items
import net.minecraft.item.crafting.IRecipeSerializer
import net.minecraft.item.crafting.Ingredient
import net.minecraft.util.IItemProvider
import net.minecraft.util.ResourceLocation
import java.util.function.Consumer

/**
 * A provider used to generate recipes for items within the game. It will create
 * the associated advancement as well.
 */
internal class Recipes(generator: DataGenerator) : RecipeProvider(generator) {

    override fun registerRecipes(consumer: Consumer<IFinishedRecipe>) {
        // Shaped Crafting
        this.addDrumRecipe(Blocks.OAK_LOG, OAK_LOG_DRUM.get()) { consumer.accept(it) }
        this.addDrumRecipe(Blocks.BIRCH_LOG, BIRCH_LOG_DRUM.get()) { consumer.accept(it) }
        this.addDrumRecipe(Blocks.SPRUCE_LOG, SPRUCE_LOG_DRUM.get()) { consumer.accept(it) }
        this.addDrumRecipe(Blocks.JUNGLE_LOG, JUNGLE_LOG_DRUM.get()) { consumer.accept(it) }
        this.addDrumRecipe(Blocks.ACACIA_LOG, ACACIA_LOG_DRUM.get()) { consumer.accept(it) }
        this.addDrumRecipe(Blocks.DARK_OAK_LOG, DARK_OAK_LOG_DRUM.get()) { consumer.accept(it) }
        this.addDrumRecipe(Blocks.CRIMSON_STEM, CRIMSON_STEM_DRUM.get()) { consumer.accept(it) }
        this.addDrumRecipe(Blocks.WARPED_STEM, WARPED_STEM_DRUM.get()) { consumer.accept(it) }

        this.addDrumstickRecipe(Items.STICK, Items.STICK, DRUMSTICK.get()) { consumer.accept(it) }
        this.addDrumstickRecipe(CHICKEN_LEG.get(), Items.BONE, CHICKEN_DRUMSTICK.get()) { consumer.accept(it) }
        this.addDrumstickRecipe(COOKED_CHICKEN_LEG.get(), Items.BONE, COOKED_CHICKEN_DRUMSTICK.get()) { consumer.accept(it) }
        this.addDrumstickRecipe(OAK_LOG_DRUM.get(), Items.STICK, OAK_LOG_DRUM_DRUMSTICK.get()) { consumer.accept(it) }
        this.addDrumstickRecipe(BIRCH_LOG_DRUM.get(), Items.STICK, BIRCH_LOG_DRUM_DRUMSTICK.get()) { consumer.accept(it) }
        this.addDrumstickRecipe(SPRUCE_LOG_DRUM.get(), Items.STICK, SPRUCE_LOG_DRUM_DRUMSTICK.get()) { consumer.accept(it) }
        this.addDrumstickRecipe(JUNGLE_LOG_DRUM.get(), Items.STICK, JUNGLE_LOG_DRUM_DRUMSTICK.get()) { consumer.accept(it) }
        this.addDrumstickRecipe(ACACIA_LOG_DRUM.get(), Items.STICK, ACACIA_LOG_DRUM_DRUMSTICK.get()) { consumer.accept(it) }
        this.addDrumstickRecipe(DARK_OAK_LOG_DRUM.get(), Items.STICK, DARK_OAK_LOG_DRUM_DRUMSTICK.get()) { consumer.accept(it) }
        this.addDrumstickRecipe(CRIMSON_STEM_DRUM.get(), Items.STICK, CRIMSON_STEM_DRUM_DRUMSTICK.get()) { consumer.accept(it) }
        this.addDrumstickRecipe(WARPED_STEM_DRUM.get(), Items.STICK, WARPED_STEM_DRUM_DRUMSTICK.get()) { consumer.accept(it) }

        // Smelting
        this.addFoodRecipes(CHICKEN_LEG.get(), COOKED_CHICKEN_LEG.get(), 0.18f, 100) { consumer.accept(it) }
        this.addFoodRecipes(CHICKEN_DRUMSTICK.get(), COOKED_CHICKEN_DRUMSTICK.get(), 0.09f, 175) { consumer.accept(it) }
    }

    /**
     * Creates a drum for the associated recipe.
     *
     * @param material The material used to make the drum
     * @param result   The resulting drum
     * @param consumer A consumer to provide the finished recipe
     */
    private fun addDrumRecipe(material: IItemProvider, result: IItemProvider, consumer: (IFinishedRecipe) -> Unit) =
        if (material.asItem() != Blocks.NOTE_BLOCK.asItem()) {
            ShapedRecipeBuilder.shapedRecipe(result)
                .patternLine("XXX")
                .patternLine("XNX")
                .patternLine("XXX")
                .key('X', material)
                .key('N', Blocks.NOTE_BLOCK)
                .addCriterion("has_material", hasItem(material))
                .addCriterion("has_note_block", hasItem(Blocks.NOTE_BLOCK))
                .build(consumer)
        } else {
            ShapedRecipeBuilder.shapedRecipe(result)
                .patternLine("XXX")
                .patternLine("XXX")
                .patternLine("XXX")
                .key('X', Blocks.NOTE_BLOCK)
                .addCriterion("has_note_block", hasItem(Blocks.NOTE_BLOCK))
                .build(consumer)
        }

    /**
     * Creates a drumstick for the associated recipe.
     *
     * @param head     The item used for the head of the drumstick
     * @param handle   The handle item of the drumstick
     * @param result   The resulting drumstick
     * @param consumer A consumer to provide the finished recipe
     */
    private fun addDrumstickRecipe(head: IItemProvider, handle: IItemProvider, result: IItemProvider, consumer: (IFinishedRecipe) -> Unit) =
        if (head.asItem() != handle.asItem()) {
            ShapedRecipeBuilder.shapedRecipe(result)
                .patternLine("  C")
                .patternLine(" X ")
                .patternLine("X  ")
                .key('X', handle).key('C', head)
                .addCriterion("has_head", hasItem(head))
                .addCriterion("has_handle", hasItem(handle))
                .build(consumer)
        } else {
            ShapedRecipeBuilder.shapedRecipe(result)
                .patternLine("  X")
                .patternLine(" X ")
                .patternLine("X  ")
                .key('X', handle)
                .addCriterion("has_handle", hasItem(handle))
                .build(consumer)
        }

    /**
     * Adds the associated food smelting recipes for the specified input and output
     * (smelting, smoking, and campfire cooking).
     *
     * @param input        The item being smelted
     * @param result       The smelting result
     * @param experience   The amount of experience for smelting
     * @param smeltingTime The amount of time it takes to smelt the item in a
     *                     furnace, all other times are calculated from this number
     * @param consumer     A consumer to provide the finished recipe
     */
    private fun addFoodRecipes(input: IItemProvider, result: IItemProvider, experience: Float, smeltingTime: Int, consumer: (IFinishedRecipe) -> Unit) {
        val loc = result.asItem().registryName!!
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(input), result, experience, smeltingTime)
            .addCriterion("has_food", hasItem(input))
            .build(consumer, ResourceLocation(loc.namespace, loc.path + "_from_smelting"))
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(input), result, experience, smeltingTime / 2, IRecipeSerializer.SMOKING)
            .addCriterion("has_food", hasItem(input))
            .build(consumer, ResourceLocation(loc.namespace, loc.path + "_from_smoking"))
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(input), result, experience, smeltingTime * 3, IRecipeSerializer.CAMPFIRE_COOKING)
            .addCriterion("has_food", hasItem(input))
            .build(consumer, ResourceLocation(loc.namespace, loc.path + "_from_campfire_cooking"))
    }
}
