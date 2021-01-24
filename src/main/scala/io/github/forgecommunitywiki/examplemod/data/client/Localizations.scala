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
import net.minecraftforge.common.data.LanguageProvider
import io.github.forgecommunitywiki.examplemod.ExampleMod
import java.util.function.Supplier
import net.minecraft.util.SoundEvent
import io.github.forgecommunitywiki.examplemod.GeneralRegistrar
import net.minecraft.util.DamageSource

/**
 * Adds localizations depending on the entered locale. Uses a switch to
 * determine the language such that the localizations can be standardized into
 * one file.
 */
class Localizations(gen: DataGenerator, locale: String)
    extends LanguageProvider(gen, ExampleMod.ID, locale) {

    override protected def addTranslations: Unit =
        getName.replace("Languages: ", "") match {
            case "en_us" => {
                // Damage Sources
                this.addDeathMessage(GeneralRegistrar.INTERNAL_HEMORRHAGE_SOURCE,
                    "%1$s internally bled to death",
                    "%1$s internally bled to death whilst fighting %2$s")

                // Blocks
                this.addBlock(GeneralRegistrar.OAK_LOG_DRUM, "Oak Log Drum")
                this.addBlock(GeneralRegistrar.BIRCH_LOG_DRUM, "Birch Log Drum")
                this.addBlock(GeneralRegistrar.SPRUCE_LOG_DRUM, "Spruce Log Drum")
                this.addBlock(GeneralRegistrar.JUNGLE_LOG_DRUM, "Jungle Log Drum")
                this.addBlock(GeneralRegistrar.ACACIA_LOG_DRUM, "Acacia Log Drum")
                this.addBlock(GeneralRegistrar.DARK_OAK_LOG_DRUM, "Dark Oak Log Drum")
                this.addBlock(GeneralRegistrar.CRIMSON_STEM_DRUM, "Crimson Stem Drum")
                this.addBlock(GeneralRegistrar.WARPED_STEM_DRUM, "Warped Stem Drum")

                // Items
                this.addItem(GeneralRegistrar.DRUMSTICK, "Drumstick")
                this.addItem(GeneralRegistrar.CHICKEN_LEG, "Chicken Leg")
                this.addItem(GeneralRegistrar.COOKED_CHICKEN_LEG, "Cooked Chicken Leg")
                this.addItem(GeneralRegistrar.CHICKEN_DRUMSTICK, "Chicken Drumstick")
                this.addItem(GeneralRegistrar.COOKED_CHICKEN_DRUMSTICK, "Cooked Chicken Drumstick")
                this.addItem(GeneralRegistrar.OAK_LOG_DRUM_DRUMSTICK, "Oak Log \"Drum\"stick")
                this.addItem(GeneralRegistrar.BIRCH_LOG_DRUM_DRUMSTICK, "Birch Log \"Drum\"stick")
                this.addItem(GeneralRegistrar.SPRUCE_LOG_DRUM_DRUMSTICK, "Spruce Log \"Drum\"stick")
                this.addItem(GeneralRegistrar.JUNGLE_LOG_DRUM_DRUMSTICK, "Jungle Log \"Drum\"stick")
                this.addItem(GeneralRegistrar.ACACIA_LOG_DRUM_DRUMSTICK, "Acacia Log \"Drum\"stick")
                this.addItem(GeneralRegistrar.DARK_OAK_LOG_DRUM_DRUMSTICK, "Dark Oak Log \"Drum\"stick")
                this.addItem(GeneralRegistrar.CRIMSON_STEM_DRUM_DRUMSTICK, "Crimson Stem \"Drum\"stick")
                this.addItem(GeneralRegistrar.WARPED_STEM_DRUM_DRUMSTICK, "Warped Stem \"Drum\"stick")

                // Effects
                this.addEffect(GeneralRegistrar.INTERNAL_HEMORRHAGE, "Internal Hemorrhage")

                // Sound Events
                this.addSoundEventSubtitle(GeneralRegistrar.DRUMSTICK_OAK_LOG_HIT.get, "Drumstick Hits Oak Log")
                this.addSoundEventSubtitle(GeneralRegistrar.DRUMSTICK_BIRCH_LOG_HIT.get, "Drumstick Hits Birch Log")
                this.addSoundEventSubtitle(GeneralRegistrar.DRUMSTICK_SPRUCE_LOG_HIT.get, "Drumstick Hits Spruce Log")
                this.addSoundEventSubtitle(GeneralRegistrar.DRUMSTICK_JUNGLE_LOG_HIT.get, "Drumstick Hits Jungle Log")
                this.addSoundEventSubtitle(GeneralRegistrar.DRUMSTICK_ACACIA_LOG_HIT.get, "Drumstick Hits Acacia Log")
                this.addSoundEventSubtitle(GeneralRegistrar.DRUMSTICK_DARK_OAK_LOG_HIT.get, "Drumstick Hits Dark Oak Log")
                this.addSoundEventSubtitle(GeneralRegistrar.DRUMSTICK_CRIMSON_STEM_HIT.get, "Drumstick Hits Crimson Stem")
                this.addSoundEventSubtitle(GeneralRegistrar.DRUMSTICK_WARPED_STEM_HIT.get, "Drumstick Hits Warped Stem")
                this.addSoundEventSubtitle(GeneralRegistrar.DRUM_TEST_HIT.get, "Drum Pitch Shifts")
            }
            case _ => {}
        }
    
    /**
     * Adds a sound event subtitle by concatenating the namespace and path of
     * registry name with a dot. For example, {@code modid:section.subsection.entry}
     * would create a subtitle with translation key
     * {@code modid.section.subsection.entry}.
     *
     * @param key   The sound event to add a subtitle for
     * @param value The localized subtitle
     */
    protected def addSoundEventSubtitle(key: SoundEvent, value: String): Unit =
        add(key.getRegistryName.toString.replace(':', '.'), value)

    /**
     * Adds a death message translation in the default format provided by
     * {@link DamageSource#getDeathMessage(net.minecraft.entity.LivingEntity)}. The
     * parameters for the objects can be specified using %n$s where n is the object
     * number.
     *
     * @param source             The damage source
     * @param deathMessage       The regular death message
     * @param entityDeathMessage The death message when there is an attacking entity
     */
    protected def addDeathMessage(source: DamageSource, deathMessage: String, entityDeathMessage: String): Unit =
        new Some("death.attack." + source.damageType).map(m => {
            add(m, deathMessage)
            add(m + ".player", entityDeathMessage)
        }).get
}
