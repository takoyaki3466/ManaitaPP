package com.takoy3466.manaitapp.dataComponent.helper;

import net.minecraft.network.chat.Component;

public class ManaitaDataComponents {

    public static final Component INVINCIBLE_TEXT = Component.translatable("misc.manaitapp.invincible_data.message_text");
    public static Component FLY_TEXT = Component.translatable("misc.manaitapp.fly_data.message_text");
    public static Component STRIKER_TEXT = Component.translatable("item.manaitapp.manaita_sword.hover_text");
    public static Component IS_KILL_ALL = Component.translatable("gui.overlay.sword.all_die");
    public static Component ONLY_ENEMY = Component.translatable("gui.overlay.sword.enemy_die");

    public static final Component TRUE_TEXT = Component.translatable("misc.manaitapp.all_data.data_true");
    public static final Component FALSE_TEXT = Component.translatable("misc.manaitapp.all_data.data_false");

    public static Component getCommonTextTF(boolean bool) {
        return bool ? TRUE_TEXT : FALSE_TEXT;
    }
}
