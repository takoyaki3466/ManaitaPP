package com.takoy3466.manaitapp.event;

import com.takoy3466.manaitapp.ManaitaPPCommon;
import com.takoy3466.manaitapp.keyMapping.ManaitaKey;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(modid = ManaitaPPCommon.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEventBus {

    @SubscribeEvent
    public static void keyRegister(RegisterKeyMappingsEvent event) {
        ManaitaKey.KEY.entries.forEach(event::register);
    }
}
