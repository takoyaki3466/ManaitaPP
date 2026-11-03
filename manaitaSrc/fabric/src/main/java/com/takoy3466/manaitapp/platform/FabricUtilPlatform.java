package com.takoy3466.manaitapp.platform;

import com.takoy3466.manaitapp.core.platform.IUtilPlatform;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FabricUtilPlatform implements IUtilPlatform {
    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return getCraftingRemainingItem(stack) != null;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        return stack.getRecipeRemainder();
    }

    @Override
    public int onArrowLoose(ItemStack stack, Level level, Player player, int charge, boolean hasAmmo) {
        return charge;
    }
}
