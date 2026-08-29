package com.takoy3466.manaitapp;

import com.takoy3466.manaitapp.core.registry.CompatRegistry;
import com.takoy3466.manaitapp.platform.ForgeRegistryPlatform;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ManaitaPPCommon.MOD_ID)
public class ManaitaPPForge {

    public ManaitaPPForge(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();

        ForgeRegistryPlatform.register(bus);

        CompatRegistry.initAll();
    }
}