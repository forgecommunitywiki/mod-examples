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

package io.github.forgecommunitywiki.examplemod.data.client

import io.github.forgecommunitywiki.examplemod.ACACIA_LOG_DRUM
import io.github.forgecommunitywiki.examplemod.BIRCH_LOG_DRUM
import io.github.forgecommunitywiki.examplemod.CRIMSON_STEM_DRUM
import io.github.forgecommunitywiki.examplemod.DARK_OAK_LOG_DRUM
import io.github.forgecommunitywiki.examplemod.JUNGLE_LOG_DRUM
import io.github.forgecommunitywiki.examplemod.MOD_ID
import io.github.forgecommunitywiki.examplemod.OAK_LOG_DRUM
import io.github.forgecommunitywiki.examplemod.SPRUCE_LOG_DRUM
import io.github.forgecommunitywiki.examplemod.WARPED_STEM_DRUM
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.block.RotatedPillarBlock
import net.minecraft.data.DataGenerator
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.generators.BlockStateProvider
import net.minecraftforge.client.model.generators.ModelProvider
import net.minecraftforge.common.data.ExistingFileHelper

/**
 * A provider used to generate block states/models and sometimes item models for
 * specifically blocks.
 */
class BlockStates(generator: DataGenerator, fileHelper: ExistingFileHelper) :
    BlockStateProvider(generator, MOD_ID, fileHelper) {

    override fun registerStatesAndModels() {
        this.createDrumBlock(OAK_LOG_DRUM.get(), toTexture(Blocks.OAK_LOG))
        this.createDrumBlock(BIRCH_LOG_DRUM.get(), toTexture(Blocks.BIRCH_LOG))
        this.createDrumBlock(SPRUCE_LOG_DRUM.get(), toTexture(Blocks.SPRUCE_LOG))
        this.createDrumBlock(JUNGLE_LOG_DRUM.get(), toTexture(Blocks.JUNGLE_LOG))
        this.createDrumBlock(ACACIA_LOG_DRUM.get(), toTexture(Blocks.ACACIA_LOG))
        this.createDrumBlock(DARK_OAK_LOG_DRUM.get(), toTexture(Blocks.DARK_OAK_LOG))
        this.createDrumBlock(CRIMSON_STEM_DRUM.get(), toTexture(Blocks.CRIMSON_STEM))
        this.createDrumBlock(WARPED_STEM_DRUM.get(), toTexture(Blocks.WARPED_STEM))
    }

    /**
     * Creates a drum block state and associated model files. Supplies the drum and
     * rim texture.
     *
     * @param block The drum block instance, must extend
     *                     {@link RotatedPillarBlock}
     * @param side         The block texture the drum was made from, the side
     *                     texture
     */
    private fun createDrumBlock(block: RotatedPillarBlock, side: ResourceLocation) =
        this.createDrumBlock(block, side, QUARTZ_BLOCK_SIDE, CYAN_TERRACOTTA)

    /**
     * Creates a drum block state and associated model files.
     *
     * @param block The drum block instance, must extend
     *                     {@link RotatedPillarBlock}
     * @param side         The block texture the drum was made from, the side
     *                     texture
     * @param drum         The drum skin texture for the top and bottom
     * @param rim          The drum rim texture that lines the model
     */
    private fun createDrumBlock(block: RotatedPillarBlock, side: ResourceLocation, drum: ResourceLocation, rim: ResourceLocation) {
        val model = this.models().withExistingParent(block.registryName.toString(), DRUM_BLOCK_TEMPLATE)
            .texture("side", side).texture("drum", drum).texture("rim", rim)
        this.axisBlock(block, model, model)
        this.simpleBlockItem(block, model)
    }
}

// Textures
private val QUARTZ_BLOCK_SIDE = extend(toTexture(Blocks.QUARTZ_BLOCK), "_side")
private val CYAN_TERRACOTTA = toTexture(Blocks.CYAN_TERRACOTTA)

// Model Locations
private val DRUM_BLOCK_TEMPLATE = ResourceLocation(MOD_ID, ModelProvider.BLOCK_FOLDER + "/drum_block_template")

/**
 * Extends the path of the resource location with a suffix
 *
 * @param location The original resource location
 * @param suffix The appended suffix
 * @return A concatenation of the original location and the suffix
 */
private fun extend(location: ResourceLocation, suffix: String): ResourceLocation =
    ResourceLocation(location.namespace, location.path + suffix)

/**
 * Converts the block to its associated texture name.
 *
 * @param block The block
 * @return The texture name prefixed with the block texture folder
 */
private fun toTexture(block: Block): ResourceLocation =
    block.registryName!!.let { ResourceLocation(it.namespace, ModelProvider.BLOCK_FOLDER + "/" + it.path) }
