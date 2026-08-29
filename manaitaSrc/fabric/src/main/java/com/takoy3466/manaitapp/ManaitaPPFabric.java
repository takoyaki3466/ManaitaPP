package com.takoy3466.manaitapp;

import com.takoy3466.manaitapp.core.registry.CompatRegistry;
import com.takoy3466.manaitapp.event.ManaitaSubscribeEvent;
import net.fabricmc.api.ModInitializer;

public class ManaitaPPFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        ManaitaSubscribeEvent.registerEvent();

        CompatRegistry.initAll();
    }
}
