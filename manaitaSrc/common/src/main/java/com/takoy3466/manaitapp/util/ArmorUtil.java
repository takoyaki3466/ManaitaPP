package com.takoy3466.manaitapp.util;

import com.takoy3466.manaitapp.dataComponent.AbstractManaitaData;
import com.takoy3466.manaitapp.dataComponent.InvincibleData;
import com.takoy3466.manaitapp.init.CompatData;
import net.minecraft.Util;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.trim.ArmorTrim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;

public class ArmorUtil {

    public static boolean invincible(@NotNull Level level,@NotNull Entity entity) {
        if (!(entity instanceof Player player)) {
            return false;
        }

        if (level.isClientSide()) {
            return false;
        }

        ManaitaUnsafeUtil.applyAbsoluteShield(player);

        boolean isInvincible = true;
        boolean hasData = false;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack itemBySlot = player.getItemBySlot(slot);
            InvincibleData data = itemBySlot.get(CompatData.INVINCIBLE_DATA.get());
            if (data != null) {
                isInvincible = data.getMsg();
                hasData = data.getMsg();
            }
        }

        if (!hasData || !isInvincible) {
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

    public static <T extends AbstractManaitaData<?>> boolean playerEquipDataItem(LivingEntity livingEntity, DataComponentType<T> type) {
        boolean hasData = false;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack itemBySlot = livingEntity.getItemBySlot(slot);
            T data = itemBySlot.get(type);
            if (data != null) {
                hasData = true;
            }
        }

        return hasData;
    }

    public static Map<ArmorType, Integer> getSimpleDefence(int armorDefence) {
        return Util.make(new EnumMap<>(ArmorType.class), map -> {
            map.put(ArmorType.HELMET, armorDefence);
            map.put(ArmorType.CHESTPLATE, armorDefence);
            map.put(ArmorType.LEGGINGS, armorDefence);
            map.put(ArmorType.BOOTS, armorDefence);
            map.put(ArmorType.BODY, armorDefence);
        });
    }

}
