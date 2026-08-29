package com.takoy3466.manaitapp.mixin;

import com.takoy3466.manaitapp.dataComponent.AbstractManaitaData;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.PatchedDataComponentMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PatchedDataComponentMap.class)
public class PatchedDataComponentMapMixin {

    @Inject(method = "remove", at = @At("HEAD"), cancellable = true)
    private <T> void remove(DataComponentType<? extends T> component, CallbackInfoReturnable<T> cir) {
        if (component instanceof AbstractManaitaData<?>) {
            cir.cancel();
        }
    }
}
