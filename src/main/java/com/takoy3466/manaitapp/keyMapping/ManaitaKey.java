package com.takoy3466.manaitapp.keyMapping;

import com.takoy3466.manaitapp.Manaitapp;
import com.takoy3466.manaitapp.core.registry.register.KeyMappingRegister;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;

import java.awt.event.KeyEvent;

@OnlyIn(Dist.CLIENT)
public class ManaitaKey {

    public static final KeyMappingRegister KEY = KeyMappingRegister.create(Manaitapp.MOD_ID);

    public static KeyMapping HelmetKey = KEY.register("helmet_setting_key",KeyEvent.VK_V);
    public static KeyMapping FlySpeedKey = KEY.register("fly_speed_setting_key",KeyEvent.VK_Z);
    public static KeyMapping MTKSwitcherOpenKey = KEY.register("mtk_switcher_open_key", GLFW.GLFW_KEY_X);
    public static KeyMapping MTKSwitcherSelectKey = KEY.register("mtk_switcher_select_key", KeyEvent.VK_C);
    public static KeyMapping SwitchExterminationKey = KEY.register("mtk_switch_extermination", KeyEvent.VK_M);
}
