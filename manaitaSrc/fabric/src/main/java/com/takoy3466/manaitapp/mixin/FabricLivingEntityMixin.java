package com.takoy3466.manaitapp.mixin;


import com.takoy3466.manaitapp.event.ManaitaEventHelper;
import com.takoy3466.manaitapp.init.CompatData;
import com.takoy3466.manaitapp.util.ArmorUtil;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class FabricLivingEntityMixin {

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void tick(CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        boolean isCancel = ManaitaEventHelper.attachmentEquipSlotExecute(livingEntity, dataAttachment -> dataAttachment.onInvTick(livingEntity.level(), livingEntity));
        if (isCancel) {
            ci.cancel();
        }
    }

    @Inject(method = "knockback", at = @At("HEAD"), cancellable = true)
    private void knockBack(double strength, double x, double z, CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        boolean equipDataItem = ArmorUtil.playerEquipDataItem(livingEntity, CompatData.INVINCIBLE_DATA.get());
        if (equipDataItem) {
            ci.cancel();
        }
    }
}
