package io.github.forgecommunitywiki.examplemod.potion;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.*;
import net.minecraft.util.DamageSource;

/**
 * A class used to handle non-instantaneous damage effect logic when applied to
 * a specific entity.
 */
public class DamageEffect extends Effect {

    /**
     * The damage source of the effect is triggered.
     */
    private final DamageSource source;
    /**
     * The amount of time between each tick duration.
     */
    private final int baseTime;
    /**
     * If the effect should render on screen.
     */
    private final boolean shouldRender;

    /**
     * A constructor used to create an effect.
     *
     * @param typeIn        What the effect is classified as
     * @param liquidColorIn The color of the associated effect liquid
     * @param source        The damage source to use when the effect triggers
     * @param baseTime      The amount of time between each trigger with no
     *                      amplifier
     * @param shouldRender  If the effect should render to the screen
     */
    public DamageEffect(final EffectType typeIn, final int liquidColorIn, final DamageSource source, final int baseTime,
            final boolean shouldRender) {
        super(typeIn, liquidColorIn);
        this.source = source;
        this.baseTime = baseTime;
        this.shouldRender = shouldRender;
    }

    @Override
    public void performEffect(final LivingEntity entityLivingBaseIn, final int amplifier) {
        entityLivingBaseIn.attackEntityFrom(this.source, 1.0f);
    }

    /**
     * Since this handles non-instantaneous effects, there should be no logic
     * besides {@link #performEffect(LivingEntity, int)} within this method. This
     * should only be changed if the effect is instant.
     */
    @Override
    public void affectEntity(final Entity source, final Entity indirectSource, final LivingEntity entityLivingBaseIn,
            final int amplifier, final double health) {
        this.performEffect(entityLivingBaseIn, amplifier);
    }

    @Override
    public boolean isReady(final int duration, final int amplifier) {
        final int time = this.baseTime >> amplifier;
        return time > 0 ? duration % time == 0 : true;
    }

    @Override
    public boolean shouldRender(final EffectInstance effect) {
        return this.shouldRender;
    }

    @Override
    public boolean shouldRenderInvText(final EffectInstance effect) {
        return this.shouldRender;
    }

    @Override
    public boolean shouldRenderHUD(final EffectInstance effect) {
        return this.shouldRender;
    }
}
