package com.takoy3466.manaitapp;

import com.takoy3466.manaitapp.keyMapping.ManaitaKey;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

public class ManaitaPPFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        registerKey();
    }

    public static void registerKey() {
        ManaitaKey.KEY.entries.forEach(KeyBindingHelper::registerKeyBinding);
    }
}
