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

import io.github.forgecommunitywiki.examplemod.ACACIA_LOG_DRUM_DRUMSTICK
import io.github.forgecommunitywiki.examplemod.BIRCH_LOG_DRUM_DRUMSTICK
import io.github.forgecommunitywiki.examplemod.CHICKEN_DRUMSTICK
import io.github.forgecommunitywiki.examplemod.CHICKEN_LEG
import io.github.forgecommunitywiki.examplemod.COOKED_CHICKEN_DRUMSTICK
import io.github.forgecommunitywiki.examplemod.COOKED_CHICKEN_LEG
import io.github.forgecommunitywiki.examplemod.CRIMSON_STEM_DRUM_DRUMSTICK
import io.github.forgecommunitywiki.examplemod.DARK_OAK_LOG_DRUM_DRUMSTICK
import io.github.forgecommunitywiki.examplemod.DRUMSTICK
import io.github.forgecommunitywiki.examplemod.JUNGLE_LOG_DRUM_DRUMSTICK
import io.github.forgecommunitywiki.examplemod.MOD_ID
import io.github.forgecommunitywiki.examplemod.OAK_LOG_DRUM_DRUMSTICK
import io.github.forgecommunitywiki.examplemod.SPRUCE_LOG_DRUM_DRUMSTICK
import io.github.forgecommunitywiki.examplemod.WARPED_STEM_DRUM_DRUMSTICK
import net.minecraft.data.DataGenerator
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.generators.ItemModelProvider
import net.minecraftforge.client.model.generators.ModelFile
import net.minecraftforge.common.data.ExistingFileHelper

/**
 * A provider used to generate item models for specifically items. Blocks and
 * their item representations should be handled separately in a different model
 * provider.
 */
internal class ItemModels(generator: DataGenerator, existingFileHelper: ExistingFileHelper) :
    ItemModelProvider(generator, MOD_ID, existingFileHelper) {

    override fun registerModels() {
        simpleItem(DRUMSTICK.get())
        simpleItem(CHICKEN_LEG.get())
        simpleItem(COOKED_CHICKEN_LEG.get())
        simpleItem(CHICKEN_DRUMSTICK.get())
        simpleItem(COOKED_CHICKEN_DRUMSTICK.get())
        simpleItem(OAK_LOG_DRUM_DRUMSTICK.get())
        simpleItem(BIRCH_LOG_DRUM_DRUMSTICK.get())
        simpleItem(SPRUCE_LOG_DRUM_DRUMSTICK.get())
        simpleItem(JUNGLE_LOG_DRUM_DRUMSTICK.get())
        simpleItem(ACACIA_LOG_DRUM_DRUMSTICK.get())
        simpleItem(DARK_OAK_LOG_DRUM_DRUMSTICK.get())
        simpleItem(CRIMSON_STEM_DRUM_DRUMSTICK.get())
        simpleItem(WARPED_STEM_DRUM_DRUMSTICK.get())
    }

    /**
     * Adds a simple item to be generated. It creates an {@code item/generated}
     * model with the base texture pointing to its registry name representation.
     *
     * We do not use () -> Item here as in the Kotlin language due to the
     * {@code RegistryObject} wrapper.
     *
     * @param item The item to generate the model for
     */
    private fun simpleItem(item: Item) =
        item.registryName!!.let {
            this.getBuilder(it.toString()).parent(ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", ResourceLocation(it.namespace, "$ITEM_FOLDER/${it.path}"))
        }
}
