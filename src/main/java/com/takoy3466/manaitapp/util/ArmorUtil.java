package com.takoy3466.manaitapp.util;

import com.takoy3466.manaitapp.dataComponent.InvincibleData;
import com.takoy3466.manaitapp.init.AttachmentsInit;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ArmorUtil {

    public static boolean invincible(Level level, Entity entity) {
        if (entity == null) {
            return false;
        }
        setInvincibleData(entity);

        if (!(entity instanceof Player player)) {
            return false;
        }

        if (level == null || level.isClientSide()) {
            return false;
        }

        InvincibleData data = player.getData(AttachmentsInit.INVINCIBLE_ATTACHMENT);
        if (!data.getMsg()) {
            return false;
        }
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 2, false, false, false));
        if (player.getFoodData().getFoodLevel() < 20) {
            player.getFoodData().setFoodLevel(20);
        }
        player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 20*20, 0));
        player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 20*20, 0));
        return true;
    }

    public static void setInvincibleData(Entity entity) {
        if (!entity.hasData(AttachmentsInit.INVINCIBLE_ATTACHMENT)) {
            entity.setData(AttachmentsInit.INVINCIBLE_ATTACHMENT, new InvincibleData(true));
        }

    }

}
