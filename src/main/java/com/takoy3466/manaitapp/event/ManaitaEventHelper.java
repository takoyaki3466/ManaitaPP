package com.takoy3466.manaitapp.event;

import com.takoy3466.manaitapp.dataComponent.InvincibleData;
import com.takoy3466.manaitapp.core.interfaces.IDataAttachment;
import com.takoy3466.manaitapp.init.AttachmentsInit;
import net.minecraft.core.Registry;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

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

    public static boolean attachmentExecute(Entity entity, Function<IDataAttachment, Boolean> func) {
        if (entity == null) {
            return false;
        }

        boolean isCancelEvent = false;
        Registry<AttachmentType<?>> attachmentTypeRegistry = entity.level().registryAccess().registryOrThrow(NeoForgeRegistries.Keys.ATTACHMENT_TYPES);
        for (AttachmentType<?> attachmentType : attachmentTypeRegistry) {
            if (entity.hasData(attachmentType)) {
                Object data = entity.getData(attachmentType);
                if (data instanceof IDataAttachment dataAttachment) {
                    isCancelEvent = func.apply(dataAttachment);
                }
            }
        }
        return isCancelEvent;
    }

    public static boolean attachmentExecute(boolean isExecute, ItemStack stack, Function<IDataAttachment, Boolean> func) {
        if (!isExecute) {
            return false;
        }
        return attachmentExecute(stack, func);
    }

    public static boolean isCancelFromLiving(LivingEntity livingEntity) {
        InvincibleData data = livingEntity.getData(AttachmentsInit.INVINCIBLE_ATTACHMENT);
        return data.getMsg();
    }
}
