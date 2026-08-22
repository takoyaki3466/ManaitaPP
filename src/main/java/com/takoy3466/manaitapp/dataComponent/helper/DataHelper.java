package com.takoy3466.manaitapp.dataComponent.helper;

import com.takoy3466.manaitapp.dataComponent.AbstractManaitaData;
import com.takoy3466.manaitapp.dataComponent.ManaitaFlyData;
import com.takoy3466.manaitapp.init.DataInit;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Function;

public class DataHelper {

    public static <T, DATA extends AbstractManaitaData<T>> Component hoverDataText(ItemStack stack, DataComponentType<DATA> type, Function<DATA, String> anyText) {
        DATA manaitaData = stack.get(type);
        if (manaitaData != null) {
            MutableComponent translatable = Component.translatable(stack.getDescriptionId() + ".hover_text");
            return Component.literal(translatable.getString() + anyText.apply(manaitaData)).withStyle(ChatFormatting.GRAY);
        }
        return Component.empty();
    }

    public static <T, DATA extends AbstractManaitaData<T>> Component hoverDataText(ItemStack stack, DataComponentType<DATA> type, Function<DATA, String> anyText, int index) {
        DATA manaitaData = stack.get(type);
        if (manaitaData != null) {
            MutableComponent translatable = Component.translatable(stack.getDescriptionId() + ".hover_text_" + index);
            return Component.literal(translatable.getString() + anyText.apply(manaitaData)).withStyle(ChatFormatting.GRAY);
        }
        return Component.empty();
    }
}
