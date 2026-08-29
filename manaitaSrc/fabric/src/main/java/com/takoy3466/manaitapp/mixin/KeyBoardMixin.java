package com.takoy3466.manaitapp.mixin;

import com.takoy3466.manaitapp.event.ManaitaEventHelper;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public class KeyBoardMixin {

    @Inject(method = "keyPress", at = @At("HEAD"))
    private void keyPress(long windowPointer, int key, int scanCode, int action, int modifiers, CallbackInfo ci) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player == null || minecraft.level == null) {
            return;
        }
        ManaitaEventHelper.attachmentExecute(player.getMainHandItem(), dataAttachment -> dataAttachment.onKeyDown(minecraft.level, minecraft.player, key, scanCode, action));

        ManaitaEventHelper.attachmentEquipSlotExecute(player, dataAttachment -> dataAttachment.onKeyDown(minecraft.level, minecraft.player, key, scanCode, action));

    }
}
