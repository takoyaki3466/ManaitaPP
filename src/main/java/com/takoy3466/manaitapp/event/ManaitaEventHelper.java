package com.takoy3466.manaitapp.event;

import com.takoy3466.manaitapp.core.interfaces.IDataAttachment;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class ManaitaEventHelper {

    public static boolean attachmentExecute(ItemStack stack, Function<IDataAttachment, Boolean> func) {
        if (stack.isEmpty()) {
            return false;
        }

        boolean isCancelEvent = false;
        for (TypedDataComponent<?> component : stack.getComponents()) {
            Object value = component.value();
            if (value instanceof IDataAttachment dataAttachment) {
                isCancelEvent = func.apply(dataAttachment);
            }
        }
        return isCancelEvent;
    }

    public static boolean attachmentEquipSlotExecute(@NotNull LivingEntity entity, Function<IDataAttachment, Boolean> func) {
        boolean isCancelEvent = false;


        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack itemBySlot = entity.getItemBySlot(slot);
            isCancelEvent = attachmentExecute(itemBySlot, func);
        }

        return isCancelEvent;
    }
}
