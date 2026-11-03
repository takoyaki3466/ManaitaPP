package com.takoy3466.manaitapp.core.platform;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface IUtilPlatform {

    boolean hasCraftingRemainingItem(ItemStack stack);

    ItemStack getCraftingRemainingItem(ItemStack stack);

    int onArrowLoose(ItemStack stack, Level level, Player player, int charge, boolean hasAmmo);
}
