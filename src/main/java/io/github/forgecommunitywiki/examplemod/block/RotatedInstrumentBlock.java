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

package io.github.forgecommunitywiki.examplemod.block;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import io.github.forgecommunitywiki.examplemod.GeneralRegistrar;
import io.github.forgecommunitywiki.examplemod.item.InstrumentItem;
import io.github.forgecommunitywiki.examplemod.util.GeneralHelper;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.*;
import net.minecraftforge.common.util.Constants;

/**
 * A block to allow certain sounds to be played when hit. The pitch can be
 * adjusted by hitting the block with a non-valid item. Hitting it on the wrong
 * side will do nothing. Most of the rotation logic is inherited from the
 * superclass.
 */
public class RotatedInstrumentBlock extends RotatedPillarBlock implements IWaterLoggable, IWrappedState {

    // Properties
    public static final IntegerProperty NOTE = BlockStateProperties.NOTE_0_24;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    /**
     * A map of voxel shapes depending on the current axis.
     */
    protected final Map<Axis, VoxelShape> shapes;
    /**
     * The block the instrument was made out of.
     */
    private final Supplier<BlockState> instrumentMaterial;

    public RotatedInstrumentBlock(final Supplier<BlockState> instrumentMaterial, final VoxelShape shape,
            final Properties properties) {
        this(instrumentMaterial, GeneralHelper.createAxisShapes(shape), properties);
    }

    /**
     * Convenience constructor for voxel shapes that are the same across multiple
     * instances.
     *
     * @param instrumentMaterial The instrument material use to determine the
     *                           block's flammability, burn time, and sound played
     *                           when hit
     * @param shapes             The shapes that represent how the model will look
     * @param properties         The associated properties of the block
     */
    public RotatedInstrumentBlock(final Supplier<BlockState> instrumentMaterial, final Map<Axis, VoxelShape> shapes,
            final Properties properties) {
        super(properties);
        this.instrumentMaterial = instrumentMaterial;
        this.setDefaultState(this.getDefaultState().with(RotatedInstrumentBlock.NOTE, 0)
                .with(RotatedInstrumentBlock.WATERLOGGED, false));
        this.shapes = shapes;
    }

    @SuppressWarnings("deprecation") // Mojang deprecation, safe to override
    @Override
    public VoxelShape getShape(final BlockState state, final IBlockReader worldIn, final BlockPos pos,
            final ISelectionContext context) {
        return this.shapes.getOrDefault(state.get(RotatedPillarBlock.AXIS),
                super.getShape(state, worldIn, pos, context));
    }

    @Override
    public BlockState getStateForPlacement(final BlockItemUseContext context) {
        return super.getStateForPlacement(context).with(RotatedInstrumentBlock.WATERLOGGED,
                context.getWorld().getFluidState(context.getPos()).getFluid() == Fluids.WATER);
    }

    /**
     * Performs the logic when the block is hit on a specific side.
     */
    @SuppressWarnings("deprecation") // Mojang deprecation, safe to override
    @Override
    public ActionResultType onBlockActivated(final BlockState state, final World world, final BlockPos pos,
            final PlayerEntity player, final Hand hand, final BlockRayTraceResult hit) {
        if (state.get(RotatedPillarBlock.AXIS) == hit.getFace().getAxis())
            return Optional.of(player.getHeldItem(hand)).filter(s -> s.getItem() instanceof InstrumentItem)
                    .flatMap(s -> ((InstrumentItem) s.getItem()).getInstrumentSound(s, this.getWrappedState()))
                    .map(s -> this.playNote(state, world, pos, player, s)).orElseGet(() -> {
                        final BlockState current = state.func_235896_a_(RotatedInstrumentBlock.NOTE);
                        if (!world.isRemote)
                            world.setBlockState(pos, current, Constants.BlockFlags.DEFAULT);
                        return this.playNote(current, world, pos, player, GeneralRegistrar.DRUM_TEST_HIT.get());
                    });
        else
            return super.onBlockActivated(state, world, pos, player, hand, hit);
    }

    /**
     * Plays the note and spawns particles on top of the block.
     *
     * @param  state  The current block state
     * @param  world  The world instance
     * @param  pos    The current block position
     * @param  player The player hitting the block
     * @param  sound  The sound the block makes
     * @return        A successful or consumed result
     */
    private ActionResultType playNote(final BlockState state, final World world, final BlockPos pos,
            final PlayerEntity player, final SoundEvent sound) {
        final int pitch = state.get(RotatedInstrumentBlock.NOTE);
        world.playSound(player, pos, sound, SoundCategory.BLOCKS, 1.0f, (float) Math.pow(2d, (pitch - 12) / 12d));
        world.addParticle(ParticleTypes.NOTE, pos.getX() + 0.5d, pos.getY() + 1.2d, pos.getZ() + 0.5d, pitch / 24d, 0d,
                0d);
        return ActionResultType.func_233537_a_(world.isRemote);
    }

    /**
     * Updates the fluid ticks associated with the block since it is waterloggable.
     */
    @SuppressWarnings("deprecation") // Mojang deprecation, safe to override
    @Override
    public BlockState updatePostPlacement(final BlockState state, final Direction direction,
            final BlockState facingState, final IWorld world, final BlockPos currentPos, final BlockPos facingPos) {
        if (state.get(RotatedInstrumentBlock.WATERLOGGED))
            world.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        return super.updatePostPlacement(state, direction, facingState, world, currentPos, facingPos);
    }

    /**
     * Gets the fluid state of the block based on if its waterlogged.
     */
    @SuppressWarnings("deprecation") // Mojang deprecation, safe to override
    @Override
    public FluidState getFluidState(final BlockState state) {
        return state.get(RotatedInstrumentBlock.WATERLOGGED) ? Fluids.WATER.getStillFluidState(false)
                : super.getFluidState(state);
    }

    /**
     * Adds additional properties to the block state.
     */
    @Override
    protected void fillStateContainer(final Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder.add(RotatedInstrumentBlock.NOTE, RotatedInstrumentBlock.WATERLOGGED));
    }

    /**
     * Sets the flammability to that of the instrument material.
     */
    @Override
    public int getFlammability(final BlockState state, final IBlockReader world, final BlockPos pos,
            final Direction face) {
        return this.getWrappedState().getFlammability(world, pos, face);
    }

    /**
     * Sets the fire spread speed to that of the instrument material.
     */
    @Override
    public int getFireSpreadSpeed(final BlockState state, final IBlockReader world, final BlockPos pos,
            final Direction face) {
        return this.getWrappedState().getFireSpreadSpeed(world, pos, face);
    }

    @Override
    public BlockState getWrappedState() { return this.instrumentMaterial.get(); }
}
