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

package io.github.forgecommunitywiki.examplemod.util;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

import net.minecraft.util.Direction.Axis;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * Holds non-specific utilities used within the mod.
 */
public class GeneralHelper {

    /**
     * Global gson instance.
     */
    public static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
    /**
     * Global logger instance.
     */
    public static final Logger LOGGER = LogManager.getLogger("Forge Community Wiki - Example Mod");

    /**
     * Creates a map of voxel shapes rotated to their specific axis.
     *
     * @param  shape The {@link Axis#Y} voxel shape
     * @return       A axis to voxel shape map
     */
    public static Map<Axis, VoxelShape> createAxisShapes(final VoxelShape shape) {
        return Util.make(new EnumMap<>(Axis.class), map -> {
            final List<AxisAlignedBB> boxes = shape.toBoundingBoxList().stream()
                    .map(box -> new AxisAlignedBB(box.minX, 1 - box.maxZ, box.minY, box.maxX, 1 - box.minZ, box.maxY))
                    .collect(Collectors.toList());

            map.put(Axis.X, boxes.stream()
                    .map(box -> VoxelShapes.create(1 - box.maxZ, box.minY, box.minX, 1 - box.minZ, box.maxY, box.maxX))
                    .reduce(VoxelShapes.empty(), VoxelShapes::or));
            map.put(Axis.Y, shape);
            map.put(Axis.Z, boxes.stream().map(VoxelShapes::create).reduce(VoxelShapes.empty(), VoxelShapes::or));
        });
    }

    /**
     * Creates a codec that converts a string to the specified registry.
     *
     * @param  <V>                  A forge supported registry entry type
     * @param  registry             The forge registry
     * @return                      A codec for the specific registry
     * @throws NullPointerException If the registry is null
     */
    public static <V extends IForgeRegistryEntry<V>> Codec<V> registryCodec(final IForgeRegistry<V> registry) {
        Objects.requireNonNull(registry);
        return ResourceLocation.CODEC.comapFlatMap(
                loc -> registry.containsKey(loc) ? DataResult.success(registry.getValue(loc))
                        : DataResult
                                .error("Not a valid registry object within " + registry.getRegistryName() + ": " + loc),
                IForgeRegistryEntry::getRegistryName);
    }
}
