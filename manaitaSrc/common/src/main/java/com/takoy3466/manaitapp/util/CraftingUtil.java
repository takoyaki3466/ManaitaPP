package com.takoy3466.manaitapp.util;

import com.takoy3466.manaitapp.dataComponent.CrushedManaitaData;
import com.takoy3466.manaitapp.init.CompatData;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;

import java.util.List;

public class CraftingUtil {

    public static boolean matches(CraftingContainer container) {
        return matches(container.getItems());
    }

    public static boolean matches(CraftingInput input) {
        return matches(input.items());
    }

    public static boolean matches(List<ItemStack> stacks) {
        boolean source = false;
        boolean item = false;

        for (ItemStack stack : stacks) {
            if (!stack.isEmpty()) {
                CrushedManaitaData crushedManaitaData = stack.get(CompatData.CRUSHED_DATA.get());
                if (crushedManaitaData != null) {
                    if (!source) {
                        source = true;
                    } else {
                        if (item) {
                            return false;
                        }
                        item = true;
                    }
                } else {
                    if (item) {
                        return false;
                    }
                    item = true;
                }
            }
        }
        return source && item;
    }

    public static ItemStack assemble(CraftingContainer container) {
        return assemble(container.getItems());
    }

    public static ItemStack assemble(CraftingInput input) {
        return assemble(input.items());
    }

    public static ItemStack assemble(List<ItemStack> stacks) {
        ItemStack targetStack = ItemStack.EMPTY;
        int source = 0;
        ItemStack dataStack = ItemStack.EMPTY;
        int multiple = 1;

        for (ItemStack stack : stacks) {
            CrushedManaitaData data = stack.get(CompatData.CRUSHED_DATA.get());
            if (!stack.isEmpty() && data == null) {
                targetStack = stack;
            }

            if (!stack.isEmpty() && data != null) {
                ++source;
                dataStack = stack;
                multiple = data.getMsg();
            }
        }

        ItemStack result;
        if (source == 2) {
            result = dataStack.copy();
            result.setCount(multiple);
            return result;
        } else if (targetStack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            result = targetStack.copy();
            result.setCount(multiple);
            return result;
        }
    }

    public static ItemStack multipleAssemble(List<ItemStack> stacks, int multipler) {
        ItemStack targetStack = ItemStack.EMPTY;
        int source = 0;
        ItemStack dataStack = ItemStack.EMPTY;

        for (ItemStack stack : stacks) {
            CrushedManaitaData data = stack.get(CompatData.CRUSHED_DATA.get());
            if (!stack.isEmpty() && data == null) {
                targetStack = stack;
            }

            if (!stack.isEmpty() && data != null) {
                ++source;
                dataStack = stack;
            }
        }

        ItemStack result;
        if (source == 2) {
            result = dataStack.copy();
            result.setCount(multipler);
            return result;
        } else if (targetStack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            result = targetStack.copy();
            result.setCount(multipler);
            return result;
        }
    }
}
