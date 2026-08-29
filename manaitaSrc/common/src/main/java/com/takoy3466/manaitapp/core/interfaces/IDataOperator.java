package com.takoy3466.manaitapp.core.interfaces;

import com.takoy3466.manaitapp.core.registry.holder.CompatHolder;
import com.takoy3466.manaitapp.dataComponent.AbstractManaitaData;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;

public interface IDataOperator {

    default <T, DATA extends AbstractManaitaData<T>> void ifPresentSet(DATA data, T value) {
        if (data != null) {
            data.setMsg(value);
        }
    }

    default <T, DATA extends AbstractManaitaData<T>> void ifPresentSet(ItemStack stack, DataComponentType<DATA> type, T value) {
        DATA data = stack.get(type);
        ifPresentSet(data, value);
    }

    default <T, DATA extends AbstractManaitaData<T>> void ifPresentSet(ItemStack stack, CompatHolder<DataComponentType<DATA>> type, T value) {
        ifPresentSet(stack, type.get(), value);
    }
}
