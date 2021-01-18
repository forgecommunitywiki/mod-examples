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

package io.github.forgecommunitywiki.examplemod.util

import com.google.gson.GsonBuilder
import com.mojang.serialization.Codec
import com.mojang.serialization.DataResult
import net.minecraft.util.ResourceLocation
import net.minecraftforge.registries.IForgeRegistry
import net.minecraftforge.registries.IForgeRegistryEntry
import org.apache.logging.log4j.LogManager

/**
 * Global gson instance.
 */
internal val GSON = GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create()

/**
 * Global logger instance.
 */
internal val LOGGER = LogManager.getLogger("Forge Community Wiki - Example Mod")

/**
 * Creates a codec that converts a string to the specified registry.
 *
 * @param <V> A forge supported registry entry type
 * @param registry The forge registry
 * @return A codec for the specific registry
 */
internal fun <V : IForgeRegistryEntry<V>> registryCodec(registry: IForgeRegistry<out V>): Codec<V> =
    ResourceLocation.CODEC.comapFlatMap({ loc ->
        if (registry.containsKey(loc)) DataResult.success(registry.getValue(loc))
        else DataResult.error("Not a valid registry object within ${registry.registryName}: $loc")
    }, IForgeRegistryEntry<V>::getRegistryName)
