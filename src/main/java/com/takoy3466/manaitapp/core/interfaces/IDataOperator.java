package com.takoy3466.manaitapp.core.interfaces;

import com.takoy3466.manaitapp.dataComponent.AbstractManaitaData;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;

public interface IDataOperator {

    default <DATA> boolean isNonNull(ItemStack stack, DataComponentType<DATA> type) {
        return stack.get(type) != null;
    }

    default <DATA> boolean isNonNull(ItemStack stack, DeferredHolder<DataComponentType<?>, DataComponentType<DATA>> typeHolder) {
        return stack.get(typeHolder.get()) != null;
    }

    default <T, DATA extends AbstractManaitaData<T>> void ifPresentSet(DATA data, T value) {
        if (data != null) {
            data.setMsg(value);
        }
    }

    default <T, DATA extends AbstractManaitaData<T>> void ifPresentSet(ItemStack stack, DataComponentType<DATA> type, T value) {
        DATA data = stack.get(type);
        ifPresentSet(data, value);
    }

    default <T, DATA extends AbstractManaitaData<T>> void ifPresentSet(ItemStack stack, DeferredHolder<DataComponentType<?>, DataComponentType<DATA>> type, T value) {
        ifPresentSet(stack, type.get(), value);
    }
}
