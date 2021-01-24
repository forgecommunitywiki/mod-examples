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

package io.github.forgecommunitywiki.examplemod.block

import io.github.forgecommunitywiki.examplemod.DRUM_TEST_HIT
import io.github.forgecommunitywiki.examplemod.item.InstrumentItem
import io.github.forgecommunitywiki.examplemod.util.createAxisShapes
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.IWaterLoggable
import net.minecraft.block.RotatedPillarBlock
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.fluid.FluidState
import net.minecraft.fluid.Fluids
import net.minecraft.item.BlockItemUseContext
import net.minecraft.particles.ParticleTypes
import net.minecraft.state.BooleanProperty
import net.minecraft.state.IntegerProperty
import net.minecraft.state.StateContainer
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.util.ActionResultType
import net.minecraft.util.Direction
import net.minecraft.util.Hand
import net.minecraft.util.SoundCategory
import net.minecraft.util.SoundEvent
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.util.math.shapes.ISelectionContext
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraft.world.IBlockReader
import net.minecraft.world.IWorld
import net.minecraft.world.World
import net.minecraftforge.common.util.Constants
import kotlin.math.pow

/**
 * A block to allow certain sounds to be played when hit. The pitch can be
 * adjusted by hitting the block with a non-valid item. Hitting it on the wrong
 * side will do nothing. Most of the rotation logic is inherited from the
 * superclass.
 */
open class RotatedInstrumentBlock(private val instrumentMaterial: Lazy<BlockState>, private val shapes: Map<Direction.Axis, VoxelShape>, properties: Properties) :
    RotatedPillarBlock(properties), IWaterLoggable, IWrappedState {

    constructor(instrumentMaterial: Lazy<BlockState>, shape: VoxelShape, properties: Properties) :
            this(instrumentMaterial, createAxisShapes(shape), properties)

    init {
        this.defaultState = this.defaultState.with(NOTE, 0).with(WATERLOGGED, false)
    }

    @Suppress("DEPRECATION") // Mojang deprecation, safe to override
    override fun getShape(state: BlockState, world: IBlockReader, pos: BlockPos, context: ISelectionContext): VoxelShape =
        this.shapes.getOrDefault(state[AXIS], super.getShape(state, world, pos, context))

    override fun getStateForPlacement(context: BlockItemUseContext): BlockState? =
        super.getStateForPlacement(context)?.with(WATERLOGGED, context.world.getFluidState(context.pos).fluid == Fluids.WATER)

    @Suppress("DEPRECATION") // Mojang deprecation, safe to override
    override fun onBlockActivated(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockRayTraceResult): ActionResultType {
        if (state[AXIS] == hit.face.axis) {
            val stack = player.getHeldItem(hand)
            val sound = (stack.item as? InstrumentItem)?.getInstrumentSound(stack, this.getWrappedState())
            val currentState = if (sound == null) state.func_235896_a_(NOTE).also { if (!world.isRemote) world.setBlockState(pos, it, Constants.BlockFlags.DEFAULT) }
                else state

            return this.playNote(currentState, world, pos, player, sound ?: DRUM_TEST_HIT.get())
        }
        return super.onBlockActivated(state, world, pos, player, hand, hit)
    }

    /**
     * Plays the note and spawns particles on top of the block.
     *
     * @param state  The current block state
     * @param world  The world instance
     * @param pos    The current block position
     * @param player The player hitting the block
     * @param sound  The sound the block makes
     * @return A successful or consumed result
     */
    private fun playNote(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, sound: SoundEvent): ActionResultType {
        val pitch = state[NOTE]
        world.playSound(player, pos, sound, SoundCategory.BLOCKS, 1.0f, 2.0.pow((pitch - 12) / 12.0).toFloat())
        world.addParticle(ParticleTypes.NOTE, pos.x + 0.5, pos.y + 1.2, pos.z + 0.5, pitch / 24.0, 0.0, 0.0)
        return ActionResultType.func_233537_a_(world.isRemote)
    }

    /**
     * Updates the fluid ticks associated with the block since it is waterloggable.
     */
    @Suppress("DEPRECATION") // Mojang deprecation, safe to override
    override fun updatePostPlacement(state: BlockState, facing: Direction, facingState: BlockState, world: IWorld, currentPos: BlockPos, facingPos: BlockPos): BlockState {
        if (state[WATERLOGGED]) world.pendingFluidTicks.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(world))
        return super.updatePostPlacement(state, facing, facingState, world, currentPos, facingPos)
    }

    /**
     * Gets the fluid state of the block based on if its waterlogged.
     */
    @Suppress("DEPRECATION") // Mojang deprecation, safe to override
    override fun getFluidState(state: BlockState): FluidState =
        if (state[WATERLOGGED]) Fluids.WATER.getStillFluidState(false) else super.getFluidState(state)

    /**
     * Adds additional properties to the block state.
     */
    override fun fillStateContainer(builder: StateContainer.Builder<Block, BlockState>) =
        super.fillStateContainer(builder.add(NOTE, WATERLOGGED))

    /**
     * Sets the flammability to that of the instrument material.
     */
    override fun getFlammability(state: BlockState?, world: IBlockReader?, pos: BlockPos?, face: Direction?): Int =
        this.getWrappedState().getFlammability(world, pos, face)

    /**
     * Sets the fire spread speed to that of the instrument material.
     */
    override fun getFireSpreadSpeed(state: BlockState?, world: IBlockReader?, pos: BlockPos?, face: Direction?): Int =
        this.getWrappedState().getFireSpreadSpeed(world, pos, face)

    override fun getWrappedState(): BlockState = instrumentMaterial.value
}

// Properties
val NOTE: IntegerProperty = BlockStateProperties.NOTE_0_24
val WATERLOGGED: BooleanProperty = BlockStateProperties.WATERLOGGED
