package com.takoy3466.manaitapp.core.registry.register;

import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class KeyMappingRegister {
    private final String MOD_ID;
    private final String KEY_CATEGORIES;

    private final List<KeyMapping> entries = new ArrayList<>();

    public KeyMappingRegister(String modId) {
        this.MOD_ID = modId;
        this.KEY_CATEGORIES = "key." +  "categories." + modId;
    }

    public static KeyMappingRegister create(String modId) {
        return new KeyMappingRegister(modId);
    }

    public void register(RegisterKeyMappingsEvent event) {
        entries.forEach(event::register);
    }

    public KeyMapping register(@NotNull String keyName, int key) {
        String KEY_NAME = "key." + MOD_ID + "." + keyName;
        KeyMapping keyMapping = new KeyMapping(KEY_NAME, key, KEY_CATEGORIES);
        entries.add(keyMapping);
        return keyMapping;

    }

    public String getCategories() {
        return KEY_CATEGORIES;
    }
}