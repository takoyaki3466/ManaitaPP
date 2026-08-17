package com.takoy3466.manaitapp.util;

import com.takoy3466.manaitapp.core.interfaces.IManaitaType;
import com.takoy3466.manaitapp.mixin.LivingEntityMixin;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.attachment.AttachmentHolder;
import net.neoforged.neoforge.attachment.AttachmentType;
import org.jetbrains.annotations.NotNull;
import sun.misc.Unsafe;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Predicate;

public class WeaponUtil {
    public static String IS_TARGET_ALL = "is_target_all";
    public static int RADIUS = 100;

    public static void lightningStriker(LivingEntity target, Level level, Player player) {
        if (level.isClientSide()) {
            return;
        }
        LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(level);
        if (lightning == null) {
            return;
        }
        lightning.wasOnFire = false;
        kill(target, player);
        if (player instanceof ServerPlayer sPlayer) {
            lightning.moveTo(target.position());
            lightning.setCause(sPlayer);
            if (!target.isDeadOrDying()) {
                level.addFreshEntity(lightning);
            }
        }

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
            System.out.println("Entity = " + entity.getClass() + ", IManaitaType = " + (entity instanceof IManaitaType));
            if (entity instanceof IManaitaType manaitaType) {
                manaitaType.manaitaPP$manaitaKill(player);
            }
        }
    }
}
