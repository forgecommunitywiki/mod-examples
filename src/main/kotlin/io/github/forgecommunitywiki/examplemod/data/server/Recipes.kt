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

import io.github.forgecommunitywiki.examplemod.CHICKEN_DRUMSTICK
import io.github.forgecommunitywiki.examplemod.CHICKEN_LEG
import io.github.forgecommunitywiki.examplemod.COOKED_CHICKEN_DRUMSTICK
import io.github.forgecommunitywiki.examplemod.COOKED_CHICKEN_LEG
import io.github.forgecommunitywiki.examplemod.DRUMSTICK
import io.github.forgecommunitywiki.examplemod.MOD_ID
import net.minecraft.data.CookingRecipeBuilder
import net.minecraft.data.DataGenerator
import net.minecraft.data.IFinishedRecipe
import net.minecraft.data.RecipeProvider
import net.minecraft.data.ShapedRecipeBuilder
import net.minecraft.item.Items
import net.minecraft.item.crafting.IRecipeSerializer
import net.minecraft.item.crafting.Ingredient
import net.minecraft.util.ResourceLocation
import java.util.function.Consumer

/**
 * A provider used to generate recipes for items within the game. It will create
 * the associated advancement as well.
 */
internal class Recipes(generator: DataGenerator) : RecipeProvider(generator) {

    override fun registerRecipes(consumer: Consumer<IFinishedRecipe>) {
        // Shaped Crafting
        ShapedRecipeBuilder.shapedRecipe(DRUMSTICK.get())
            .patternLine("  X")
            .patternLine(" X ")
            .patternLine("X  ")
            .key('X', Items.STICK)
            .addCriterion("has_item", hasItem(Items.STICK))
            .build(consumer)
        ShapedRecipeBuilder.shapedRecipe(CHICKEN_DRUMSTICK.get())
            .patternLine("  C")
            .patternLine(" X ")
            .patternLine("X  ")
            .key('X', Items.BONE).key('C', CHICKEN_LEG.get())
            .addCriterion("has_item", hasItem(Items.BONE)).addCriterion("has_item_2", hasItem(CHICKEN_LEG.get()))
            .build(consumer)
        ShapedRecipeBuilder.shapedRecipe(COOKED_CHICKEN_DRUMSTICK.get())
            .patternLine("  C")
            .patternLine(" X ")
            .patternLine("X  ")
            .key('X', Items.BONE).key('C', COOKED_CHICKEN_LEG.get())
            .addCriterion("has_item", hasItem(Items.BONE)).addCriterion("has_item_2", hasItem(COOKED_CHICKEN_LEG.get()))
            .build(consumer)

        // Smelting
        CookingRecipeBuilder
            .smeltingRecipe(Ingredient.fromItems(CHICKEN_LEG.get()),
                COOKED_CHICKEN_LEG.get(), 0.18f, 100)
            .addCriterion("has_item", hasItem(CHICKEN_LEG.get())).build(consumer)
        CookingRecipeBuilder
            .smeltingRecipe(Ingredient.fromItems(CHICKEN_DRUMSTICK.get()),
                COOKED_CHICKEN_DRUMSTICK.get(), 0.09f, 175)
            .addCriterion("has_item", hasItem(CHICKEN_DRUMSTICK.get()))
            .build(consumer, ResourceLocation(MOD_ID, "cooked_chicken_drumstick_from_smelting"))

        // Smoking
        CookingRecipeBuilder
            .cookingRecipe(Ingredient.fromItems(CHICKEN_LEG.get()),
                COOKED_CHICKEN_LEG.get(), 0.18f, 50, IRecipeSerializer.SMOKING)
            .addCriterion("has_item", hasItem(CHICKEN_LEG.get()))
            .build(consumer, ResourceLocation(MOD_ID, "cooked_chicken_leg_from_smoking"))
        CookingRecipeBuilder
            .cookingRecipe(Ingredient.fromItems(CHICKEN_DRUMSTICK.get()),
                COOKED_CHICKEN_DRUMSTICK.get(), 0.09f, 88, IRecipeSerializer.SMOKING)
            .addCriterion("has_item", hasItem(CHICKEN_DRUMSTICK.get()))
            .build(consumer, ResourceLocation(MOD_ID, "cooked_chicken_drumstick_from_smoking"))

        // Campfire Cooking
        CookingRecipeBuilder
            .cookingRecipe(Ingredient.fromItems(CHICKEN_LEG.get()),
                COOKED_CHICKEN_LEG.get(), 0.18f, 300, IRecipeSerializer.CAMPFIRE_COOKING)
            .addCriterion("has_item", hasItem(CHICKEN_LEG.get()))
            .build(consumer, ResourceLocation(MOD_ID, "cooked_chicken_leg_from_campfire_cooking"))
        CookingRecipeBuilder
            .cookingRecipe(Ingredient.fromItems(CHICKEN_DRUMSTICK.get()),
                COOKED_CHICKEN_DRUMSTICK.get(), 0.09f, 525, IRecipeSerializer.CAMPFIRE_COOKING)
            .addCriterion("has_item", hasItem(CHICKEN_DRUMSTICK.get()))
            .build(consumer, ResourceLocation(MOD_ID, "cooked_chicken_drumstick_from_campfire_cooking"))
    }
}
