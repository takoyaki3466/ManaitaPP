package com.takoy3466.manaitapp.util;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ManaitaUnsafe {
    private static Unsafe unsafe;

    // あらかじめ「絶対に死なない固定用HPオブジェクト」をキャッシュしておく
    private static final Float IMMUTABLE_HP = 20.0f;

    static {
        try {
            Field theUnsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafeField.setAccessible(true);
            unsafe = (Unsafe) theUnsafeField.get(null);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void applyAbsoluteShield(Player player) {
        try {
            SynchedEntityData entityData = player.getEntityData();

            // 提示されたコードに対応する正確なフィールド名からオフセットを取得
            Class<?> dataItemClass = SynchedEntityData.DataItem.class;

            Field valueField = dataItemClass.getDeclaredField("value");
            long valueOffset = unsafe.objectFieldOffset(valueField);

            Field dirtyField = dataItemClass.getDeclaredField("dirty");
            long dirtyOffset = unsafe.objectFieldOffset(dirtyField);

            // 1. DATA_HEALTH_ID の取得
            Field healthIdField = LivingEntity.class.getDeclaredField("DATA_HEALTH_ID");
            healthIdField.setAccessible(true);
            EntityDataAccessor<Float> healthId = (EntityDataAccessor<Float>) healthIdField.get(null);

            // 2. 生の DataItem インスタンスを取得
            java.lang.reflect.Method getItemMethod = SynchedEntityData.class.getDeclaredMethod("getItem", EntityDataAccessor.class);
            getItemMethod.setAccessible(true);
            SynchedEntityData.DataItem<Float> healthDataItem = (SynchedEntityData.DataItem<Float>) getItemMethod.invoke(entityData, healthId);

            if (healthDataItem != null) {
                // 3. 【超高速ロック】value フィールドの参照を、強制的に 20.0f のオブジェクトに固定
                // 相手が 0.0f を書き込んだ直後であっても、ここで 20.0f に強制上書きされます
                unsafe.putObject(healthDataItem, valueOffset, IMMUTABLE_HP);

                // 4. 【ネットワーク遮断】dirty フラグを強制的に false に固定
                // 相手が dirty=true にしてパケットを送ろうとしても、ここで false に戻すため、
                // サーバーは「HPに変更はない」と判断し、周囲に死亡パケットが飛ばなくなります
                unsafe.putBoolean(healthDataItem, dirtyOffset, false);
            }

        } catch (NoSuchFieldException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}