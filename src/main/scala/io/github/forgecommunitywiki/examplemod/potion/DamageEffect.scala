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

package io.github.forgecommunitywiki.examplemod.potion

import net.minecraft.potion.Effect
import net.minecraft.potion.EffectType
import net.minecraft.util.DamageSource
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.Entity
import net.minecraft.potion.EffectInstance

/**
 * A class used to handle non-instantaneous damage effect logic when applied to
 * a specific entity.
 */
class DamageEffect(`type`: EffectType, liquidColor: Int, private final val source: DamageSource, private final val baseTime: Int, private final val shouldRender: Boolean)
    extends Effect(`type`, liquidColor) {

    override def performEffect(entity: LivingEntity, amplifier: Int): Unit =
        entity.attackEntityFrom(source, 1.0f)

    override def isReady(duration: Int, amplifier: Int): Boolean =
        new Some(baseTime >> amplifier).map(i => i <= 0 || duration % i == 0).get

    /**
     * Since this handles non-instantaneous effects, there should be no logic
     * besides {@link #performEffect(LivingEntity, int)} within this method. This
     * should only be changed if the effect is instant.
     */
    override def affectEntity(source: Entity, indirectSource: Entity, entity: LivingEntity, amplifier: Int, health: Double): Unit =
        performEffect(entity, amplifier)

    override def shouldRender(effect: EffectInstance): Boolean = shouldRender

    override def shouldRenderInvText(effect: EffectInstance): Boolean = shouldRender

    override def shouldRenderHUD(effect: EffectInstance): Boolean = shouldRender
}
