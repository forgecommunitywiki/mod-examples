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

package io.github.forgecommunitywiki.examplemod.data.server;

import java.util.function.Consumer;

import io.github.forgecommunitywiki.examplemod.GeneralRegistrar;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

/**
 * A provider used to generate recipes for items within the game. It will create
 * the associated advancement as well.
 */
public class Recipes extends RecipeProvider {

    public Recipes(final DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(final Consumer<IFinishedRecipe> consumer) {
        // Shaped Crafting
        this.addDrumRecipe(Blocks.OAK_LOG, GeneralRegistrar.OAK_LOG_DRUM.get(), consumer);
        this.addDrumRecipe(Blocks.BIRCH_LOG, GeneralRegistrar.BIRCH_LOG_DRUM.get(), consumer);
        this.addDrumRecipe(Blocks.SPRUCE_LOG, GeneralRegistrar.SPRUCE_LOG_DRUM.get(), consumer);
        this.addDrumRecipe(Blocks.JUNGLE_LOG, GeneralRegistrar.JUNGLE_LOG_DRUM.get(), consumer);
        this.addDrumRecipe(Blocks.ACACIA_LOG, GeneralRegistrar.ACACIA_LOG_DRUM.get(), consumer);
        this.addDrumRecipe(Blocks.DARK_OAK_LOG, GeneralRegistrar.DARK_OAK_LOG_DRUM.get(), consumer);
        this.addDrumRecipe(Blocks.CRIMSON_STEM, GeneralRegistrar.CRIMSON_STEM_DRUM.get(), consumer);
        this.addDrumRecipe(Blocks.WARPED_STEM, GeneralRegistrar.WARPED_STEM_DRUM.get(), consumer);

        this.addDrumstickRecipe(Items.STICK, Items.STICK, GeneralRegistrar.DRUMSTICK.get(), consumer);
        this.addDrumstickRecipe(GeneralRegistrar.CHICKEN_LEG.get(), Items.BONE,
                GeneralRegistrar.CHICKEN_DRUMSTICK.get(), consumer);
        this.addDrumstickRecipe(GeneralRegistrar.COOKED_CHICKEN_LEG.get(), Items.BONE,
                GeneralRegistrar.COOKED_CHICKEN_DRUMSTICK.get(), consumer);
        this.addDrumstickRecipe(GeneralRegistrar.OAK_LOG_DRUM.get(), Items.STICK,
                GeneralRegistrar.OAK_LOG_DRUM_DRUMSTICK.get(), consumer);
        this.addDrumstickRecipe(GeneralRegistrar.BIRCH_LOG_DRUM.get(), Items.STICK,
                GeneralRegistrar.BIRCH_LOG_DRUM_DRUMSTICK.get(), consumer);
        this.addDrumstickRecipe(GeneralRegistrar.SPRUCE_LOG_DRUM.get(), Items.STICK,
                GeneralRegistrar.SPRUCE_LOG_DRUM_DRUMSTICK.get(), consumer);
        this.addDrumstickRecipe(GeneralRegistrar.JUNGLE_LOG_DRUM.get(), Items.STICK,
                GeneralRegistrar.JUNGLE_LOG_DRUM_DRUMSTICK.get(), consumer);
        this.addDrumstickRecipe(GeneralRegistrar.ACACIA_LOG_DRUM.get(), Items.STICK,
                GeneralRegistrar.ACACIA_LOG_DRUM_DRUMSTICK.get(), consumer);
        this.addDrumstickRecipe(GeneralRegistrar.DARK_OAK_LOG_DRUM.get(), Items.STICK,
                GeneralRegistrar.DARK_OAK_LOG_DRUM_DRUMSTICK.get(), consumer);
        this.addDrumstickRecipe(GeneralRegistrar.CRIMSON_STEM_DRUM.get(), Items.STICK,
                GeneralRegistrar.CRIMSON_STEM_DRUM_DRUMSTICK.get(), consumer);
        this.addDrumstickRecipe(GeneralRegistrar.WARPED_STEM_DRUM.get(), Items.STICK,
                GeneralRegistrar.WARPED_STEM_DRUM_DRUMSTICK.get(), consumer);

        // Smelting
        this.addFoodRecipes(GeneralRegistrar.CHICKEN_LEG.get(), GeneralRegistrar.COOKED_CHICKEN_LEG.get(), 0.18f, 100,
                consumer);
        this.addFoodRecipes(GeneralRegistrar.CHICKEN_DRUMSTICK.get(), GeneralRegistrar.COOKED_CHICKEN_DRUMSTICK.get(),
                0.09f, 175, consumer);
    }

    /**
     * Creates a drum for the associated recipe.
     *
     * @param material The material used to make the drum
     * @param result   The resulting drum
     * @param consumer A consumer to provide the finished recipe
     */
    protected void addDrumRecipe(final IItemProvider material, final IItemProvider result,
            final Consumer<IFinishedRecipe> consumer) {
        if (material.asItem() != Blocks.NOTE_BLOCK.asItem())
            ShapedRecipeBuilder.shapedRecipe(result).patternLine("XXX").patternLine("XNX").patternLine("XXX")
                    .key('X', material).key('N', Blocks.NOTE_BLOCK)
                    .addCriterion("has_material", RecipeProvider.hasItem(material))
                    .addCriterion("has_note_block", RecipeProvider.hasItem(Blocks.NOTE_BLOCK)).build(consumer);
        else
            ShapedRecipeBuilder.shapedRecipe(result).patternLine("XXX").patternLine("XXX").patternLine("XXX")
                    .key('X', Blocks.NOTE_BLOCK)
                    .addCriterion("has_note_block", RecipeProvider.hasItem(Blocks.NOTE_BLOCK)).build(consumer);
    }

    /**
     * Creates a drumstick for the associated recipe.
     *
     * @param head     The item used for the head of the drumstick
     * @param handle   The handle item of the drumstick
     * @param result   The resulting drumstick
     * @param consumer A consumer to provide the finished recipe
     */
    protected void addDrumstickRecipe(final IItemProvider head, final IItemProvider handle, final IItemProvider result,
            final Consumer<IFinishedRecipe> consumer) {
        if (head.asItem() != handle.asItem())
            ShapedRecipeBuilder.shapedRecipe(result).patternLine("  C").patternLine(" X ").patternLine("X  ")
                    .key('X', handle).key('C', head).addCriterion("has_head", RecipeProvider.hasItem(head))
                    .addCriterion("has_handle", RecipeProvider.hasItem(handle)).build(consumer);
        else
            ShapedRecipeBuilder.shapedRecipe(result).patternLine("  X").patternLine(" X ").patternLine("X  ")
                    .key('X', handle).addCriterion("has_handle", RecipeProvider.hasItem(handle)).build(consumer);
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
    protected void addFoodRecipes(final IItemProvider input, final IItemProvider result, final float experience,
            final int smeltingTime, final Consumer<IFinishedRecipe> consumer) {
        final ResourceLocation loc = result.asItem().getRegistryName();
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(input), result, experience, smeltingTime)
                .addCriterion("has_food", RecipeProvider.hasItem(input))
                .build(consumer, new ResourceLocation(loc.getNamespace(), loc.getPath() + "_from_smelting"));
        CookingRecipeBuilder
                .cookingRecipe(Ingredient.fromItems(input), result, experience, smeltingTime / 2,
                        IRecipeSerializer.SMOKING)
                .addCriterion("has_food", RecipeProvider.hasItem(input))
                .build(consumer, new ResourceLocation(loc.getNamespace(), loc.getPath() + "_from_smoking"));
        CookingRecipeBuilder
                .cookingRecipe(Ingredient.fromItems(input), result, experience, smeltingTime * 3,
                        IRecipeSerializer.CAMPFIRE_COOKING)
                .addCriterion("has_food", RecipeProvider.hasItem(input))
                .build(consumer, new ResourceLocation(loc.getNamespace(), loc.getPath() + "_from_campfire_cooking"));
    }
}
