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

import net.minecraft.data.DataGenerator
import net.minecraftforge.common.data.ExistingFileHelper
import net.minecraftforge.client.model.generators.BlockStateProvider
import io.github.forgecommunitywiki.examplemod.ExampleMod
import net.minecraft.block.Block
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.generators.ModelProvider
import net.minecraft.block.Blocks
import net.minecraft.block.RotatedPillarBlock
import io.github.forgecommunitywiki.examplemod.GeneralRegistrar

/**
 * A provider used to generate block states/models and sometimes item models for
 * specifically blocks.
 */
class BlockStates(generator: DataGenerator, fileHelper: ExistingFileHelper)
    extends BlockStateProvider(generator, ExampleMod.ID, fileHelper) {

    override protected def registerStatesAndModels: Unit = {
        this.createDrumBlock(GeneralRegistrar.OAK_LOG_DRUM.get, BlockStates.toTexture(Blocks.OAK_LOG))
        this.createDrumBlock(GeneralRegistrar.BIRCH_LOG_DRUM.get, BlockStates.toTexture(Blocks.BIRCH_LOG))
        this.createDrumBlock(GeneralRegistrar.SPRUCE_LOG_DRUM.get, BlockStates.toTexture(Blocks.SPRUCE_LOG))
        this.createDrumBlock(GeneralRegistrar.JUNGLE_LOG_DRUM.get, BlockStates.toTexture(Blocks.JUNGLE_LOG))
        this.createDrumBlock(GeneralRegistrar.ACACIA_LOG_DRUM.get, BlockStates.toTexture(Blocks.ACACIA_LOG))
        this.createDrumBlock(GeneralRegistrar.DARK_OAK_LOG_DRUM.get, BlockStates.toTexture(Blocks.DARK_OAK_LOG))
        this.createDrumBlock(GeneralRegistrar.CRIMSON_STEM_DRUM.get, BlockStates.toTexture(Blocks.CRIMSON_STEM))
        this.createDrumBlock(GeneralRegistrar.WARPED_STEM_DRUM.get, BlockStates.toTexture(Blocks.WARPED_STEM))
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
    private def createDrumBlock(block: RotatedPillarBlock, side: ResourceLocation): Unit =
        this.createDrumBlock(block, side, BlockStates.QUARTZ_BLOCK_SIDE, BlockStates.CYAN_TERRACOTTA)

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
    private def createDrumBlock(block: RotatedPillarBlock, side: ResourceLocation, drum: ResourceLocation, rim: ResourceLocation): Unit = {
        val model = this.models.withExistingParent(block.getRegistryName.toString, BlockStates.DRUM_BLOCK_TEMPLATE)
            .texture("side", side).texture("drum", drum).texture("rim", rim)
        this.axisBlock(block, model, model)
        this.simpleBlockItem(block, model)
    }
}
object BlockStates {

    // Textures
    private final val QUARTZ_BLOCK_SIDE = extend(toTexture(Blocks.QUARTZ_BLOCK), "_side")
    private final val CYAN_TERRACOTTA = toTexture(Blocks.CYAN_TERRACOTTA)

    // Model Locations
    private final val DRUM_BLOCK_TEMPLATE = new ResourceLocation(ExampleMod.ID, ModelProvider.BLOCK_FOLDER + "/drum_block_template")

    /**
     * Extends the path of the resource location with a suffix
     *
     * @param location The original resource location
     * @param suffix The appended suffix
     * @return A concatenation of the original location and the suffix
     */
    private def extend(loc: ResourceLocation, suffix: String): ResourceLocation =
        new ResourceLocation(loc.getNamespace, loc.getPath + suffix)

    /**
     * Converts the block to its associated texture name.
     *
     * @param block The block
     * @return The texture name prefixed with the block texture folder
     */
    private def toTexture(block: Block): ResourceLocation =
        Some(block.getRegistryName()).map(loc => new ResourceLocation(loc.getNamespace, ModelProvider.BLOCK_FOLDER + "/" + loc.getPath)).get
}
