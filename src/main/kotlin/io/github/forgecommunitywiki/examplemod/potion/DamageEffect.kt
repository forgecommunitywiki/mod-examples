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

import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.potion.Effect
import net.minecraft.potion.EffectInstance
import net.minecraft.potion.EffectType
import net.minecraft.util.DamageSource

/**
 * A class used to handle non-instantaneous damage effect logic when applied to
 * a specific entity.
 */
open class DamageEffect(type: EffectType, liquidColor: Int, private val source: DamageSource, private val baseTime: Int, private val shouldRender: Boolean) :
    Effect(type, liquidColor) {

    override fun performEffect(entity: LivingEntity, amplifier: Int) {
        entity.attackEntityFrom(source, 1.0f)
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean =
        (baseTime shr amplifier).let { it <= 0 || duration.rem(it) == 0 }

    /**
     * Since this handles non-instantaneous effects, there should be no logic
     * besides {@link #performEffect(LivingEntity, int)} within this method. This
     * should only be changed if the effect is instant.
     */
    override fun affectEntity(source: Entity?, indirectSource: Entity?, entity: LivingEntity, amplifier: Int, health: Double) = performEffect(entity, amplifier)

    override fun shouldRender(effect: EffectInstance?): Boolean = shouldRender

    override fun shouldRenderInvText(effect: EffectInstance?): Boolean = shouldRender

    override fun shouldRenderHUD(effect: EffectInstance?): Boolean = shouldRender
}
