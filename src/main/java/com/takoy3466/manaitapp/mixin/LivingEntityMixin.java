package com.takoy3466.manaitapp.mixin;

import com.takoy3466.manaitapp.core.ManaitaDamageSource;
import com.takoy3466.manaitapp.core.interfaces.IManaitaType;
import com.takoy3466.manaitapp.dataComponent.InvincibleData;
import com.takoy3466.manaitapp.init.DataInit;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements IManaitaType {

    @Unique
    private boolean isManaita = false;

    @Unique
    private static EntityDataAccessor<Boolean> MANAITA;

    @Shadow
    @Final
    private static EntityDataAccessor<Float> DATA_HEALTH_ID;

    @Shadow
    public abstract float getMaxHealth();

    @Override
    public void manaitaPP$setManaitaType(boolean isManaita) {
        this.isManaita = isManaita;
    }

    @Override
    public boolean manaitaPP$isManaitaType() {
        return isManaita;
    }

    @Override
    public void manaitaPP$manaitaKill(LivingEntity attacker) {
        LivingEntity self = (LivingEntity) (Object) this;
        System.out.println("Entity = " + self.getClass() + "\n" + ", ManaitaEntityData = " + self.getEntityData().get(MANAITA));
        if (self.getEntityData().get(MANAITA)) {
            return;
        }
        ManaitaDamageSource source = new ManaitaDamageSource(attacker);

        if (attacker instanceof Player player) {
            self.setLastHurtByPlayer(player);
        }else {
            self.setLastHurtByMob(attacker);
        }

        self.hurt(source, Float.MAX_VALUE);

        self.getEntityData().set(DATA_HEALTH_ID, 0f);

        if (self.level().isClientSide()) {
            return;
        }
        self.die(source);
    }

    @Inject(method = "<clinit>", at = @At("HEAD"))
    private static void staticInit(CallbackInfo ci) {
        MANAITA = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.BOOLEAN);
    }


    @Inject(method = "defineSynchedData", at = @At("HEAD"))
    private void difineData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(MANAITA, false);
    }

    @Inject(method = "getHealth", at = @At("HEAD"), cancellable = true)
    private void getHealth(CallbackInfoReturnable<Float> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (((IManaitaType)self).manaitaPP$isManaitaType() || self.getEntityData().get(MANAITA)) {
            cir.setReturnValue(this.getMaxHealth());
        }
    }

    @Inject(method = "isDeadOrDying", at = @At("HEAD"), cancellable = true)
    private void isDeadOrDying(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (((IManaitaType)self).manaitaPP$isManaitaType() || self.getEntityData().get(MANAITA)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "isAlive", at = @At("HEAD"), cancellable = true)
    private void isAlive(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (((IManaitaType)self).manaitaPP$isManaitaType() || self.getEntityData().get(MANAITA)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "setHealth", at = @At("HEAD"), cancellable = true)
    private void setHealth(float health, CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (((IManaitaType)self).manaitaPP$isManaitaType() || self.getEntityData().get(MANAITA)) {
            ci.cancel();
        }
    }

    @Inject(method = "kill", at = @At("HEAD"), cancellable = true)
    private void kill(CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (((IManaitaType)self).manaitaPP$isManaitaType() || self.getEntityData().get(MANAITA)) {
            ci.cancel();
        }else {
            self.hurt(self.damageSources().genericKill(), Float.MAX_VALUE);
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = self.getItemBySlot(slot);
            InvincibleData data = stack.get(DataInit.INVINCIBLE_DATA);
            if (data != null) {
                data.setMsg(true);
                self.getEntityData().set(MANAITA, data.getMsg());
            }
        }
    }
}
