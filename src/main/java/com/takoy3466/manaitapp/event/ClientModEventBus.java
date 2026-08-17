package com.takoy3466.manaitapp.event;

import com.takoy3466.manaitapp.Manaitapp;
import com.takoy3466.manaitapp.core.registry.register.KeyMappingRegister;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(modid = Manaitapp.MOD_ID, value = Dist.CLIENT)
public class ClientModEventBus {

    @SubscribeEvent
    public static void keyRegister(RegisterKeyMappingsEvent event) {
        KeyMappingRegister.entries.forEach(event::register);
    }
}
