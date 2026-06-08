package com.takoy3466.manaitapp.core.registry.register;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class TieredDoubleRegister<T, U, TIER> {
    private final DoubleRegister<T, U> register;

    public TieredDoubleRegister(ResourceKey<? extends Registry<T>> key, ResourceKey<? extends Registry<U>> key1, String modId) {
        this.register = DoubleRegister.create(key, key1, modId);
    }


}
