package com.takoy3466.manaitapp.util;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

public class WeaponUtil {
    public static String IS_TARGET_ALL = "is_target_all";
    public static int RADIUS = 100;

    public static void lightningStriker(LivingEntity target, Level level, Player player) {
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

        die(target);

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

    public static void die(@NotNull LivingEntity entity) {
        try (Level level = entity.level()){
            Holder<DamageType> holder = level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.PLAYER_ATTACK);
            DamageSource source = new DamageSource(holder);
            if (!level.isClientSide()) {
                entity.setHealth(0.0f);
                if (!entity.isDeadOrDying()) {
                    entity.hurt(source, Float.MAX_VALUE);
                    if (entity.isAlive()) {
                        entity.die(source);
                    }
                }
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
