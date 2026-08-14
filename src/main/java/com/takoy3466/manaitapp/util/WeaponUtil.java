package com.takoy3466.manaitapp.util;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.neoforged.neoforge.attachment.AttachmentSync;
import net.neoforged.neoforge.attachment.AttachmentType;
import org.jetbrains.annotations.NotNull;
import sun.misc.Unsafe;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.jar.Attributes;

public class WeaponUtil {
    public static String IS_TARGET_ALL = "is_target_all";
    public static int RADIUS = 100;

    private static final Unsafe unsafe;

    static {
        try {
            Field theUnsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafeField.setAccessible(true);
            unsafe = (Unsafe) theUnsafeField.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

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

        die(target, player);

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

    public static void die(@NotNull LivingEntity entity, @NotNull Player player) {
        try (Level level = entity.level()){
            if (!level.isClientSide()) {
                manaitaKill(entity);
                List<ItemStack> stackList = getEntityDrops(entity, entity.level().damageSources().generic());
                stackList.forEach(player::addItem);

            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void manaitaKill(Entity target) {
        boolean isAlive = true;
        target.setInvisible(false);
        if (target instanceof LivingEntity livingEntity) {
            livingEntity.setHealth(0);
            livingEntity.hurt(target.level().damageSources().generic(), Float.MAX_VALUE);
            isAlive = livingEntity.isAlive() || livingEntity.getHealth() > 0 || !livingEntity.isDeadOrDying();

        }
        if (isAlive) {
            return;
        }

        try {
            Class<?> dataItemClass = Class.forName("net.minecraft.network.syncher.SynchedEntityData$DataItem");

            Field valueField = dataItemClass.getDeclaredField("value");
            long dataItemValueOffset = unsafe.objectFieldOffset(valueField);

            Field dirtyField = dataItemClass.getDeclaredField("dirty");
            long dataItemDirtyOffset = unsafe.objectFieldOffset(dirtyField);

            SynchedEntityData entityData = target.getEntityData();

            java.lang.reflect.Method getItemMethod = SynchedEntityData.class.getDeclaredMethod("getItem", net.minecraft.network.syncher.EntityDataAccessor.class);
            getItemMethod.setAccessible(true);

            Field healthIdField = LivingEntity.class.getDeclaredField("DATA_HEALTH_ID"); // 難読化名注意
            healthIdField.setAccessible(true);

            EntityDataAccessor<Float> healthId = (EntityDataAccessor<Float>) healthIdField.get(null);

            Object dataItem = getItemMethod.invoke(entityData, healthId);

            if (dataItem != null) {
                unsafe.putObject(dataItem, dataItemValueOffset, 0.0f);
                unsafe.putBoolean(dataItem, dataItemDirtyOffset, true);

            }

        } catch (NoSuchFieldException | ClassNotFoundException | InvocationTargetException | NoSuchMethodException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<ItemStack> getEntityDrops(LivingEntity entity, DamageSource damageSource) {
        if (!(entity.level() instanceof ServerLevel serverLevel)) {
            return Collections.emptyList();
        }

        ResourceKey<LootTable> lootTableKey = entity.getLootTable();

        LootTable lootTable = serverLevel.getServer().reloadableRegistries().getLootTable(lootTableKey);

        LootParams lootParams = new LootParams.Builder(serverLevel)
                .withParameter(LootContextParams.THIS_ENTITY, entity)
                .withParameter(LootContextParams.ORIGIN, entity.position())
                .withParameter(LootContextParams.DAMAGE_SOURCE, damageSource)
                .withOptionalParameter(LootContextParams.ATTACKING_ENTITY, damageSource.getEntity())
                .create(LootContextParamSets.ENTITY);

        return lootTable.getRandomItems(lootParams);
    }
}
