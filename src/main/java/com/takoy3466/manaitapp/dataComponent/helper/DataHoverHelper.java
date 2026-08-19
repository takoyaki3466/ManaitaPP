package com.takoy3466.manaitapp.dataComponent.helper;

import com.takoy3466.manaitapp.dataComponent.AbstractManaitaData;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

import java.util.function.Function;

public class DataHoverHelper {

    public static <T, DATA extends AbstractManaitaData<T>> Component hoverDataText(ItemStack stack, DataComponentType<DATA> type, Function<DATA, String> anyText) {
        DATA manaitaData = stack.get(type);
        if (manaitaData != null) {
            MutableComponent translatable = Component.translatable(stack.getDescriptionId() + ".hover_text");
            return Component.literal(translatable.getString() + anyText.apply(manaitaData)).withStyle(ChatFormatting.GRAY);
        }
        return Component.empty();
    }
}
