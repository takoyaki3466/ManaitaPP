package com.takoy3466.manaitapp.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class MTKUtil {

    public static Component renderRainbow(String text) {
        long time = System.currentTimeMillis();
        MutableComponent component = Component.literal("");

        for (int i = 0; i < text.length(); i++) {
            float hue = (time / 10f + i * 20) % 360 / 360f;
            int color = java.awt.Color.HSBtoRGB(hue, 1.0f, 1.0f);
            component.append(
                    Component.literal(String.valueOf(text.charAt(i)))
                            .withStyle(style -> style.withColor(color))
            );
        }

        return component;
    }
}
