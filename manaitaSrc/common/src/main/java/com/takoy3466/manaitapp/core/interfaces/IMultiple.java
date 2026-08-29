package com.takoy3466.manaitapp.core.interfaces;

import com.takoy3466.manaitapp.core.ManaitaTier;
import net.minecraft.world.item.ItemStack;

public interface IMultiple {

    ManaitaTier getManaitaTier();

    int getMultiple();


    default void multipler(ItemStack stack) {
        stack.setCount(stack.getCount() * getMultiple());
    }
}
