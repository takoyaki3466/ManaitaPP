package com.takoy3466.manaitapp.util;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class RenderUtil {

    public static void blitGuiTexture(GuiGraphics graphics, ResourceLocation atlasLocation, int x, int y, float uOffset, float vOffset, int uWidth, int vHeight, int textureWidth, int textureHeight) {
        graphics.blit(RenderType::guiTextured, atlasLocation, x, y, uOffset, vOffset, uWidth, vHeight, uWidth, vHeight, textureWidth, textureHeight);
    }
}
