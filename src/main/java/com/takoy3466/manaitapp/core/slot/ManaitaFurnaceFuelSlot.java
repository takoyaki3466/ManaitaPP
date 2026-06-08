package com.takoy3466.manaitapp.core.slot;

import com.takoy3466.manaitapp.menu.ManaitaFurnaceMenu;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public class ManaitaFurnaceFuelSlot extends Slot {
    private final AbstractContainerMenu menu;

    public ManaitaFurnaceFuelSlot(AbstractContainerMenu menu, Container container, int slot, int x, int y) {
        super(container, slot, x, y);
        this.menu = menu;
    }

    public boolean mayPlace(@NotNull ItemStack stack) {
        if (menu instanceof ManaitaFurnaceMenu furnaceMenu) {
            return furnaceMenu.isFuel(stack) || isBucket(stack);
        }else return false;
    }

    public int getMaxStackSize(@NotNull ItemStack stack) {
        return isBucket(stack) ? 1 : super.getMaxStackSize(stack);
    }

    public static boolean isBucket(ItemStack stack) {
        return stack.is(Items.BUCKET);
    }
}
