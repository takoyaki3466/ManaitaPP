package com.takoy3466.manaitapp.event;

import com.takoy3466.manaitapp.ManaitaPPCommon;
import com.takoy3466.manaitapp.keyMapping.ManaitaKey;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ManaitaPPCommon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEventBus {

    @SubscribeEvent
    public static void keyRegister(RegisterKeyMappingsEvent event) {
        ManaitaKey.KEY.entries.forEach(event::register);
    }
}
