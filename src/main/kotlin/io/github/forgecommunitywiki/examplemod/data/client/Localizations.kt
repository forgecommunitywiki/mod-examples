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
import io.github.forgecommunitywiki.examplemod.ACACIA_LOG_DRUM_DRUMSTICK
import io.github.forgecommunitywiki.examplemod.BIRCH_LOG_DRUM
import io.github.forgecommunitywiki.examplemod.BIRCH_LOG_DRUM_DRUMSTICK
import io.github.forgecommunitywiki.examplemod.CHICKEN_DRUMSTICK
import io.github.forgecommunitywiki.examplemod.CHICKEN_LEG
import io.github.forgecommunitywiki.examplemod.COOKED_CHICKEN_DRUMSTICK
import io.github.forgecommunitywiki.examplemod.COOKED_CHICKEN_LEG
import io.github.forgecommunitywiki.examplemod.CRIMSON_STEM_DRUM
import io.github.forgecommunitywiki.examplemod.CRIMSON_STEM_DRUM_DRUMSTICK
import io.github.forgecommunitywiki.examplemod.DARK_OAK_LOG_DRUM
import io.github.forgecommunitywiki.examplemod.DARK_OAK_LOG_DRUM_DRUMSTICK
import io.github.forgecommunitywiki.examplemod.DRUMSTICK
import io.github.forgecommunitywiki.examplemod.DRUMSTICK_ACACIA_LOG_HIT
import io.github.forgecommunitywiki.examplemod.DRUMSTICK_BIRCH_LOG_HIT
import io.github.forgecommunitywiki.examplemod.DRUMSTICK_CRIMSON_STEM_HIT
import io.github.forgecommunitywiki.examplemod.DRUMSTICK_DARK_OAK_LOG_HIT
import io.github.forgecommunitywiki.examplemod.DRUMSTICK_JUNGLE_LOG_HIT
import io.github.forgecommunitywiki.examplemod.DRUMSTICK_OAK_LOG_HIT
import io.github.forgecommunitywiki.examplemod.DRUMSTICK_SPRUCE_LOG_HIT
import io.github.forgecommunitywiki.examplemod.DRUMSTICK_WARPED_STEM_HIT
import io.github.forgecommunitywiki.examplemod.DRUM_TEST_HIT
import io.github.forgecommunitywiki.examplemod.INTERNAL_HEMORRHAGE
import io.github.forgecommunitywiki.examplemod.INTERNAL_HEMORRHAGE_SOURCE
import io.github.forgecommunitywiki.examplemod.JUNGLE_LOG_DRUM
import io.github.forgecommunitywiki.examplemod.JUNGLE_LOG_DRUM_DRUMSTICK
import io.github.forgecommunitywiki.examplemod.MOD_ID
import io.github.forgecommunitywiki.examplemod.OAK_LOG_DRUM
import io.github.forgecommunitywiki.examplemod.OAK_LOG_DRUM_DRUMSTICK
import io.github.forgecommunitywiki.examplemod.SPRUCE_LOG_DRUM
import io.github.forgecommunitywiki.examplemod.SPRUCE_LOG_DRUM_DRUMSTICK
import io.github.forgecommunitywiki.examplemod.WARPED_STEM_DRUM
import io.github.forgecommunitywiki.examplemod.WARPED_STEM_DRUM_DRUMSTICK
import net.minecraft.data.DataGenerator
import net.minecraft.util.DamageSource
import net.minecraft.util.SoundEvent
import net.minecraftforge.common.data.LanguageProvider

/**
 * Adds localizations depending on the entered locale. Uses a switch to
 * determine the language such that the localizations can be standardized into
 * one file.
 */
internal class Localizations(gen: DataGenerator, locale: String) : LanguageProvider(gen, MOD_ID, locale) {

    override fun addTranslations() =
        when (name.replace("Languages: ", "")) {
            "en_us" -> {
                // Damage Sources
                this.addDeathMessage(
                    INTERNAL_HEMORRHAGE_SOURCE,
                    "%1\$s internally bled to death",
                    "%1\$s internally bled to death whilst fighting %2\$s"
                )

                // Blocks
                this.addBlock(OAK_LOG_DRUM, "Oak Log Drum")
                this.addBlock(BIRCH_LOG_DRUM, "Birch Log Drum")
                this.addBlock(SPRUCE_LOG_DRUM, "Spruce Log Drum")
                this.addBlock(JUNGLE_LOG_DRUM, "Jungle Log Drum")
                this.addBlock(ACACIA_LOG_DRUM, "Acacia Log Drum")
                this.addBlock(DARK_OAK_LOG_DRUM, "Dark Oak Log Drum")
                this.addBlock(CRIMSON_STEM_DRUM, "Crimson Stem Drum")
                this.addBlock(WARPED_STEM_DRUM, "Warped Stem Drum")

                // Items
                this.addItem(DRUMSTICK, "Drumstick")
                this.addItem(CHICKEN_LEG, "Chicken Leg")
                this.addItem(COOKED_CHICKEN_LEG, "Cooked Chicken Leg")
                this.addItem(CHICKEN_DRUMSTICK, "Chicken Drumstick")
                this.addItem(COOKED_CHICKEN_DRUMSTICK, "Cooked Chicken Drumstick")
                this.addItem(OAK_LOG_DRUM_DRUMSTICK, "Oak Log \"Drum\"stick")
                this.addItem(BIRCH_LOG_DRUM_DRUMSTICK, "Birch Log \"Drum\"stick")
                this.addItem(SPRUCE_LOG_DRUM_DRUMSTICK, "Spruce Log \"Drum\"stick")
                this.addItem(JUNGLE_LOG_DRUM_DRUMSTICK, "Jungle Log \"Drum\"stick")
                this.addItem(ACACIA_LOG_DRUM_DRUMSTICK, "Acacia Log \"Drum\"stick")
                this.addItem(DARK_OAK_LOG_DRUM_DRUMSTICK, "Dark Oak Log \"Drum\"stick")
                this.addItem(CRIMSON_STEM_DRUM_DRUMSTICK, "Crimson Stem \"Drum\"stick")
                this.addItem(WARPED_STEM_DRUM_DRUMSTICK, "Warped Stem \"Drum\"stick")

                // Effects
                this.addEffect(INTERNAL_HEMORRHAGE, "Internal Hemorrhage")

                // Sound Events
                this.addSoundEventSubtitle(DRUMSTICK_OAK_LOG_HIT.get(), "Drumstick Hits Oak Log")
                this.addSoundEventSubtitle(DRUMSTICK_BIRCH_LOG_HIT.get(), "Drumstick Hits Birch Log")
                this.addSoundEventSubtitle(DRUMSTICK_SPRUCE_LOG_HIT.get(), "Drumstick Hits Spruce Log")
                this.addSoundEventSubtitle(DRUMSTICK_JUNGLE_LOG_HIT.get(), "Drumstick Hits Jungle Log")
                this.addSoundEventSubtitle(DRUMSTICK_ACACIA_LOG_HIT.get(), "Drumstick Hits Acacia Log")
                this.addSoundEventSubtitle(DRUMSTICK_DARK_OAK_LOG_HIT.get(), "Drumstick Hits Dark Oak Log")
                this.addSoundEventSubtitle(DRUMSTICK_CRIMSON_STEM_HIT.get(), "Drumstick Hits Crimson Stem")
                this.addSoundEventSubtitle(DRUMSTICK_WARPED_STEM_HIT.get(), "Drumstick Hits Warped Stem")
                this.addSoundEventSubtitle(DRUM_TEST_HIT.get(), "Drum Pitch Shifts")
            }
            else -> {}
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
    private fun addSoundEventSubtitle(key: SoundEvent, value: String) =
        add(key.registryName.toString().replace(':', '.'), value)

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
    private fun addDeathMessage(source: DamageSource, deathMessage: String, entityDeathMessage: String) =
        "death.attack.${source.damageType}".let {
            add(it, deathMessage)
            add("$it.player", entityDeathMessage)
        }
}
