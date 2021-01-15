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

package io.github.forgecommunitywiki.examplemod.data.client;

import java.util.function.Supplier;

import io.github.forgecommunitywiki.examplemod.ExampleMod;
import io.github.forgecommunitywiki.examplemod.GeneralRegistrar;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;

/**
 * A provider used to generate item models for specifically items. Blocks and
 * their item representations should be handled separately in a different model
 * provider.
 */
public class ItemModels extends ItemModelProvider {

    public ItemModels(final DataGenerator generator, final ExistingFileHelper existingFileHelper) {
        super(generator, ExampleMod.ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        this.simpleItem(GeneralRegistrar.DRUMSTICK);
        this.simpleItem(GeneralRegistrar.CHICKEN_LEG);
        this.simpleItem(GeneralRegistrar.COOKED_CHICKEN_LEG);
        this.simpleItem(GeneralRegistrar.CHICKEN_DRUMSTICK);
        this.simpleItem(GeneralRegistrar.COOKED_CHICKEN_DRUMSTICK);
    }

    /**
     * Adds a simple item to be generated. It creates an {@code item/generated}
     * model with the base texture pointing to its registry name representation.
     *
     * @param itemSupplier The item to generate the model for
     */
    protected void simpleItem(final Supplier<? extends Item> itemSupplier) {
        final ResourceLocation location = itemSupplier.get().getRegistryName();
        this.getBuilder(location.toString()).parent(new ModelFile.UncheckedModelFile("item/generated")).texture(
                "layer0",
                new ResourceLocation(location.getNamespace(), ModelProvider.ITEM_FOLDER + "/" + location.getPath()));
    }
}
