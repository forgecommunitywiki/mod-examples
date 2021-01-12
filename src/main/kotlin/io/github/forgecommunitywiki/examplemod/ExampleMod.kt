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

package io.github.forgecommunitywiki.examplemod

import io.github.forgecommunitywiki.examplemod.client.ClientHandler
import io.github.forgecommunitywiki.examplemod.data.client.ItemModels
import io.github.forgecommunitywiki.examplemod.data.client.Localizations
import io.github.forgecommunitywiki.examplemod.data.server.Recipes
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import java.util.stream.Stream

/**
 * The main class used to handle any registration or common events associated
 * with the mod.
 */
@Mod(ExampleMod.ID)
internal class ExampleMod {

    init {
        val mod = FMLJavaModLoadingContext.get().modEventBus
        val forge = MinecraftForge.EVENT_BUS

        // Initialize physical client
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT) { Runnable { ClientHandler.init(mod, forge) } }

        // Initialize registries
        GeneralRegistrar.register(mod)

        // Attach common events
        mod.addListener { event: FMLCommonSetupEvent -> commonSetup(event) }
        mod.addListener { event: GatherDataEvent -> attachProviders(event) }
    }

    /**
     * Handles items that should occur directly after registry events including map
     * introductions and vanilla registrations. Only add if {@link InterModComms},
     * will not be needed. Otherwise, use {@link FMLLoadCompleteEvent}.
     *
     * @param event The common setup event
     */
    private fun commonSetup(event: FMLCommonSetupEvent) {
        event.enqueueWork(GeneralRegistrar::registerSlaveMaps)
    }

    /**
     * Attaches all providers to be used with data generation.
     *
     * @param event The data generator event
     */
    private fun attachProviders(event: GatherDataEvent) {
        val gen = event.generator
        val helper = event.existingFileHelper
        if(event.includeClient()) {
            Stream.of("en_us").forEach { locale -> gen.addProvider(Localizations(gen, locale)) }
            gen.addProvider(ItemModels(gen, helper))
        }
        if(event.includeServer())
            gen.addProvider(Recipes(gen))
    }

    companion object {
        /**
         * The id or namespace associated with this mod.
         */
        const val ID = "examplemod"
    }
}