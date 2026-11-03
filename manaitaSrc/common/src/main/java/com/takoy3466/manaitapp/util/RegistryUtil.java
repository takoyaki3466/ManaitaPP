package com.takoy3466.manaitapp.util;

import com.takoy3466.manaitapp.core.Identifier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class RegistryUtil {

    public static ResourceKey<Item> getItemId(String modId, String path) {
        return getId(Registries.ITEM, modId, path);
    }

    public static ResourceKey<Block> getBlockId(String modId, String path) {
        return getId(Registries.BLOCK, modId, path);
    }

    public static <T> ResourceKey<T> getId(ResourceKey<Registry<T>> resourceKey, String modId, String path) {
        return ResourceKey.create(resourceKey, new Identifier(modId, path).get());
    }
}
