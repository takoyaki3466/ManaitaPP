package com.takoy3466.manaitapp.event;

import com.takoy3466.manaitapp.ManaitaPPCommon;
import com.takoy3466.manaitapp.keyMapping.ManaitaKey;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ManaitaPPCommon.MOD_ID, value = Dist.CLIENT)
public class ClientEvent {


    @SubscribeEvent
    public static void onKeyDown(InputEvent.Key event) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player == null || minecraft.level == null) {
            return;
        }
        ManaitaEventHelper.attachmentExecute(player.getMainHandItem(), dataAttachment -> dataAttachment.onKeyDown(minecraft.level, minecraft.player, event.getKey(), event.getScanCode(), event.getAction()));

        ManaitaEventHelper.attachmentEquipSlotExecute(player, dataAttachment -> dataAttachment.onKeyDown(minecraft.level, minecraft.player, event.getKey(), event.getScanCode(), event.getAction()));
    }
}
