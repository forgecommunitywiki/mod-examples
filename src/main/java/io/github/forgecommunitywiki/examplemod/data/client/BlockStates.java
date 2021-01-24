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

package io.github.forgecommunitywiki.examplemod.data.client;

import java.util.function.Supplier;

import io.github.forgecommunitywiki.examplemod.ExampleMod;
import io.github.forgecommunitywiki.examplemod.GeneralRegistrar;
import net.minecraft.block.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;

/**
 * A provider used to generate block states/models and sometimes item models for
 * specifically blocks.
 */
public class BlockStates extends BlockStateProvider {

    // Textures
    private static final ResourceLocation QUARTZ_BLOCK_SIDE = BlockStates
            .extend(BlockStates.toTexture(Blocks.QUARTZ_BLOCK), "_side");
    private static final ResourceLocation CYAN_TERRACOTTA = BlockStates.toTexture(Blocks.CYAN_TERRACOTTA);

    // Model Locations
    private static final ResourceLocation DRUM_BLOCK_TEMPLATE = new ResourceLocation(ExampleMod.ID,
            ModelProvider.BLOCK_FOLDER + "/drum_block_template");

    public BlockStates(final DataGenerator gen, final ExistingFileHelper exFileHelper) {
        super(gen, ExampleMod.ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.createDrumBlock(GeneralRegistrar.OAK_LOG_DRUM, BlockStates.toTexture(Blocks.OAK_LOG));
        this.createDrumBlock(GeneralRegistrar.BIRCH_LOG_DRUM, BlockStates.toTexture(Blocks.BIRCH_LOG));
        this.createDrumBlock(GeneralRegistrar.SPRUCE_LOG_DRUM, BlockStates.toTexture(Blocks.SPRUCE_LOG));
        this.createDrumBlock(GeneralRegistrar.JUNGLE_LOG_DRUM, BlockStates.toTexture(Blocks.JUNGLE_LOG));
        this.createDrumBlock(GeneralRegistrar.ACACIA_LOG_DRUM, BlockStates.toTexture(Blocks.ACACIA_LOG));
        this.createDrumBlock(GeneralRegistrar.DARK_OAK_LOG_DRUM, BlockStates.toTexture(Blocks.DARK_OAK_LOG));
        this.createDrumBlock(GeneralRegistrar.CRIMSON_STEM_DRUM, BlockStates.toTexture(Blocks.CRIMSON_STEM));
        this.createDrumBlock(GeneralRegistrar.WARPED_STEM_DRUM, BlockStates.toTexture(Blocks.WARPED_STEM));
    }

    /**
     * Creates a drum block state and associated model files. Supplies the drum and
     * rim texture.
     *
     * @param drumSupplier The supplier drum block instance, must extend
     *                     {@link RotatedPillarBlock}
     * @param side         The block texture the drum was made from, the side
     *                     texture
     */
    protected void createDrumBlock(final Supplier<? extends RotatedPillarBlock> drumSupplier,
            final ResourceLocation side) {
        this.createDrumBlock(drumSupplier, side, BlockStates.QUARTZ_BLOCK_SIDE, BlockStates.CYAN_TERRACOTTA);
    }

    /**
     * Creates a drum block state and associated model files.
     *
     * @param drumSupplier The supplier drum block instance, must extend
     *                     {@link RotatedPillarBlock}
     * @param side         The block texture the drum was made from, the side
     *                     texture
     * @param drum         The drum skin texture for the top and bottom
     * @param rim          The drum rim texture that lines the model
     */
    protected void createDrumBlock(final Supplier<? extends RotatedPillarBlock> drumSupplier,
            final ResourceLocation side, final ResourceLocation drum, final ResourceLocation rim) {
        final RotatedPillarBlock block = drumSupplier.get();
        final BlockModelBuilder model = this.models()
                .withExistingParent(block.getRegistryName().toString(), BlockStates.DRUM_BLOCK_TEMPLATE)
                .texture("side", side).texture("drum", drum).texture("rim", rim);
        this.axisBlock(block, model, model);
        this.simpleBlockItem(block, model);
    }

    /**
     * Extends the path of the resource location with a suffix
     *
     * @param  rl     The original resource location
     * @param  suffix The appended suffix
     * @return        A concatenation of the original location and the suffix
     */
    private static ResourceLocation extend(final ResourceLocation rl, final String suffix) {
        return new ResourceLocation(rl.getNamespace(), rl.getPath() + suffix);
    }

    /**
     * Converts the block to its associated texture name.
     *
     * @param  block The block
     * @return       The texture name prefixed with the block texture folder
     */
    private static ResourceLocation toTexture(final Block block) {
        final ResourceLocation name = block.getRegistryName();
        return new ResourceLocation(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + name.getPath());
    }
}
