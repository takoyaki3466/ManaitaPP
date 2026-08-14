package com.takoy3466.manaitapp.event;

import com.takoy3466.manaitapp.Manaitapp;
import com.takoy3466.manaitapp.init.BlocksInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.InputEvent;

@EventBusSubscriber(modid = Manaitapp.MOD_ID, value = Dist.CLIENT)
public class ClientEvent {

    @SubscribeEvent
    public static void onKeyDown(InputEvent.Key event) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player == null || minecraft.level == null) {
            return;
        }
        ManaitaEventHelper.attachmentExecute(player.getMainHandItem(), dataAttachment -> dataAttachment.onKeyDown(minecraft.level, minecraft.player, event.getKey(), event.getScanCode(), event.getAction()));
    }
}
