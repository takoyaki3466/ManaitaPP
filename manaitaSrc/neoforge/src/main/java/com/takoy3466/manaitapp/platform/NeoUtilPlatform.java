package com.takoy3466.manaitapp.platform;

import com.takoy3466.manaitapp.core.platform.IUtilPlatform;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.EventHooks;

public class NeoUtilPlatform implements IUtilPlatform {
    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return !stack.getCraftingRemainder().isEmpty();
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        return stack.getCraftingRemainder();
    }

    @Override
    public int onArrowLoose(ItemStack stack, Level level, Player player, int charge, boolean hasAmmo) {
        return EventHooks.onArrowLoose(stack, level, player, charge, hasAmmo);
    }
}
