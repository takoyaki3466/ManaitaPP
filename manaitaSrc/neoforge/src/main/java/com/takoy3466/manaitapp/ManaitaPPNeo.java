package com.takoy3466.manaitapp;


import com.takoy3466.manaitapp.core.registry.CompatRegistry;
import com.takoy3466.manaitapp.init.CompatData;
import com.takoy3466.manaitapp.platform.NeoRegistryPlatform;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(ManaitaPPCommon.MOD_ID)
public class ManaitaPPNeo {

    public ManaitaPPNeo(IEventBus bus) {

        NeoRegistryPlatform.register(bus);

        CompatRegistry.initAll();
    }
}