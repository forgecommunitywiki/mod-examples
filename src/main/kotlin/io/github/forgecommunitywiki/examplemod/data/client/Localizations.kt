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

import io.github.forgecommunitywiki.examplemod.ExampleMod
import io.github.forgecommunitywiki.examplemod.GeneralRegistrar
import net.minecraft.data.DataGenerator
import net.minecraft.util.SoundEvent
import net.minecraftforge.common.data.LanguageProvider
import java.util.function.Supplier

/**
 * Adds localizations depending on the entered locale. Uses a switch to
 * determine the language such that the localizations can be standardized into
 * one file.
 */
internal class Localizations(gen: DataGenerator, locale: String)
    : LanguageProvider(gen, ExampleMod.ID, locale) {

    override fun addTranslations() {
        when(name.replace("Languages: ", "")) {
            "en_us" -> {
                this.addItem(GeneralRegistrar.DRUMSTICK, "Drumstick")
                this.addSoundEventSubtitle(GeneralRegistrar.DRUMSTICK_OAK_LOG_HIT, "Drumstick Hits Oak Log")
                this.addSoundEventSubtitle(GeneralRegistrar.DRUMSTICK_BIRCH_LOG_HIT, "Drumstick Hits Birch Log")
                this.addSoundEventSubtitle(GeneralRegistrar.DRUMSTICK_SPRUCE_LOG_HIT, "Drumstick Hits Spruce Log")
                this.addSoundEventSubtitle(GeneralRegistrar.DRUMSTICK_JUNGLE_LOG_HIT, "Drumstick Hits Jungle Log")
                this.addSoundEventSubtitle(GeneralRegistrar.DRUMSTICK_ACACIA_LOG_HIT, "Drumstick Hits Acacia Log")
                this.addSoundEventSubtitle(GeneralRegistrar.DRUMSTICK_DARK_OAK_LOG_HIT, "Drumstick Hits Dark Oak Log")
                this.addSoundEventSubtitle(GeneralRegistrar.DRUMSTICK_CRIMSON_STEM_HIT, "Drumstick Hits Crimson Stem")
                this.addSoundEventSubtitle(GeneralRegistrar.DRUMSTICK_WARPED_STEM_HIT, "Drumstick Hits Warped Stem")
            }
            else -> {}
        }
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
    private fun addSoundEventSubtitle(key: Supplier<out SoundEvent>, value: String)
        = add(key.get().registryName.toString().replace(':', '.'), value)
}