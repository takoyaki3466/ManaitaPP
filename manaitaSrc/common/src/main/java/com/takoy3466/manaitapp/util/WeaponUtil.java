package com.takoy3466.manaitapp.util;

import com.takoy3466.manaitapp.core.interfaces.IManaitaType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;

public class WeaponUtil {

    public static void lightningStriker(LivingEntity target, Level level, Player player) {
        if (level.isClientSide()) {
            return;
        }
        LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(level);
        if (lightning == null) {
            return;
        }
        lightning.wasOnFire = false;
        if (player instanceof ServerPlayer sPlayer) {
            lightning.moveTo(target.position());
            lightning.setCause(sPlayer);
            if (!target.isDeadOrDying()) {
                level.addFreshEntity(lightning);
            }
        }
        kill(target, player);
    }

    public static void lightningStriker(List<LivingEntity> targets, Level level, Player player) {
        for (LivingEntity target : targets) {
            lightningStriker(target, level, player);
        }
    }

    public static Predicate<LivingEntity> ALL_LIVING = living -> !(living instanceof Player) && (living instanceof LivingEntity);
    public static Predicate<LivingEntity> ONLY_ENEMY = living -> !(living instanceof Player) && (living instanceof Enemy);

    public static <T extends Entity> List<T> selectTargets(Class<T> targetClass, Level level, Entity entity, double radius , Predicate<? super T> predicate) {
        return level.getEntitiesOfClass(targetClass, entity.getBoundingBox().inflate(radius), predicate);
    }

    public static void kill(@NotNull LivingEntity entity, @NotNull Player player) {
        if (!entity.level().isClientSide()) {
            if (entity instanceof IManaitaType manaitaType) {
                manaitaType.manaitaPP$manaitaKill(player);
            }
        }
    }
}
