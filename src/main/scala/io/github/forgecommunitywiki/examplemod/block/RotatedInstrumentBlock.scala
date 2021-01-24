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

import net.minecraft.block.BlockState
import net.minecraft.util.Direction.Axis
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraft.block.AbstractBlock.Properties
import net.minecraft.block.RotatedPillarBlock
import net.minecraft.block.IWaterLoggable
import io.github.forgecommunitywiki.examplemod.util.GeneralHelper
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.shapes.ISelectionContext
import net.minecraft.world.IBlockReader
import net.minecraft.item.BlockItemUseContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.{ActionResultType, Hand}
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.world.World
import net.minecraft.util.Direction
import net.minecraft.world.IWorld
import net.minecraft.fluid.FluidState
import net.minecraft.block.Block
import net.minecraft.state.StateContainer.Builder
import net.minecraft.util.SoundEvent
import net.minecraft.fluid.Fluids
import net.minecraft.util.SoundCategory
import net.minecraft.particles.ParticleTypes
import java.awt.Desktop.Action
import io.github.forgecommunitywiki.examplemod.item.InstrumentItem
import io.github.forgecommunitywiki.examplemod.GeneralRegistrar

/**
 * A block to allow certain sounds to be played when hit. The pitch can be
 * adjusted by hitting the block with a non-valid item. Hitting it on the wrong
 * side will do nothing. Most of the rotation logic is inherited from the
 * superclass.
 */
class RotatedInstrumentBlock(_instrumentMaterial: => BlockState, private final val shapes: Map[Axis, VoxelShape], properties: Properties)
    extends RotatedPillarBlock(properties) with IWaterLoggable with IWrappedState {
    private final lazy val instrumentMaterial = _instrumentMaterial

    def this(_instrumentMaterial: => BlockState, shape: VoxelShape, properties: Properties) =
        this(_instrumentMaterial, GeneralHelper.createAxisShapes(shape), properties)
    
    this.setDefaultState(this.getDefaultState()
        .`with`(RotatedInstrumentBlock.NOTE, Int.box(0))
        .`with`(RotatedInstrumentBlock.WATERLOGGED, Boolean.box(false)))

    override def getShape(state: BlockState, world: IBlockReader, pos: BlockPos, context: ISelectionContext): VoxelShape =
        this.shapes.getOrElse(state.get(RotatedPillarBlock.AXIS), super.getShape(state, world, pos, context))

    override def getStateForPlacement(context: BlockItemUseContext): BlockState =
        super.getStateForPlacement(context).`with`(RotatedInstrumentBlock.WATERLOGGED, Boolean.box(context.getWorld.getFluidState(context.getPos).getFluid == Fluids.WATER))

    //@nowarn // Mojang deprecation, safe to override
    override def onBlockActivated(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockRayTraceResult): ActionResultType = {
        if(state.get(RotatedPillarBlock.AXIS) == hit.getFace.getAxis) {
            val stack = player.getHeldItem(hand)
            val sound = if (stack.getItem.isInstanceOf[InstrumentItem])
                    stack.getItem.asInstanceOf[InstrumentItem].getInstrumentSound(stack, this.getWrappedState)
                else None
            val currentState = if (sound.isEmpty) {
                val temp = state.func_235896_a_(RotatedInstrumentBlock.NOTE)
                if(!world.isRemote) world.setBlockState(pos, temp)
                temp
            } else state
            this.playNote(currentState, world, pos, player, sound.getOrElse(GeneralRegistrar.DRUM_TEST_HIT.get))
        }
        else super.onBlockActivated(state, world, pos, player, hand, hit)
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
    private def playNote(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, sound: SoundEvent): ActionResultType = {
        val pitch = state.get(RotatedInstrumentBlock.NOTE)
        world.playSound(player, pos, sound, SoundCategory.BLOCKS, 1.0f, Math.pow(2.0d, (pitch - 12) / 12d).toFloat)
        world.addParticle(ParticleTypes.NOTE, pos.getX + 0.5d, pos.getY + 1.2d, pos.getZ + 0.5d, pitch / 24d, 0d, 0d)
        ActionResultType.func_233537_a_(world.isRemote)
    }

    /**
     * Updates the fluid ticks associated with the block since it is waterloggable.
     */
    //@nowarn // Mojang deprecation, safe to override
    override def updatePostPlacement(state: BlockState, facing: Direction, facingState: BlockState, world: IWorld, currentPos: BlockPos, facingPos: BlockPos): BlockState = {
        if(state.get(RotatedInstrumentBlock.WATERLOGGED)) world.getPendingFluidTicks.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(world))
        super.updatePostPlacement(state, facing, facingState, world, currentPos, facingPos)
    }

    /**
     * Gets the fluid state of the block based on if its waterlogged.
     */
    //@nowarn // Mojang deprecation, safe to override
    override def getFluidState(state: BlockState): FluidState =
        if(state.get(RotatedInstrumentBlock.WATERLOGGED)) Fluids.WATER.getStillFluidState(false)
        else super.getFluidState(state)

    /**
     * Adds additional properties to the block state.
     */
    override protected def fillStateContainer(builder: Builder[Block,BlockState]): Unit =
        super.fillStateContainer(builder.add(RotatedInstrumentBlock.NOTE, RotatedInstrumentBlock.WATERLOGGED))

    /**
     * Sets the flammability to that of the instrument material.
     */
    override def getFlammability(state: BlockState, world: IBlockReader, pos: BlockPos, face: Direction): Int =
        this.getWrappedState.getFlammability(world, pos, face)

    /**
     * Sets the fire spread speed to that of the instrument material.
     */
    override def getFireSpreadSpeed(state: BlockState, world: IBlockReader, pos: BlockPos, face: Direction): Int =
        this.getWrappedState.getFireSpreadSpeed(world, pos, face)
    
    override def getWrappedState: BlockState = instrumentMaterial
}
object RotatedInstrumentBlock {
    // Properties
    final val NOTE = BlockStateProperties.NOTE_0_24
    final val WATERLOGGED = BlockStateProperties.WATERLOGGED
}
