package com.takoy3466.manaitapp.item.tool;

import com.takoy3466.manaitapp.Manaitapp;
import com.takoy3466.manaitapp.dataComponent.LightningStrikerData;
import com.takoy3466.manaitapp.init.DataInit;
import com.takoy3466.manaitapp.util.WeaponUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class ToolManaitaSword extends SwordItem {
    public ToolManaitaSword() {
        super(ManaitaToolTier.MANAITA_TIER, new Item.Properties().fireResistant().component(DataInit.STRIKER_DATA, new LightningStrikerData(false)));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity player) {
        WeaponUtil.die(target);
        return super.hurtEnemy(stack, target, player);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (entity instanceof LivingEntity target) {
            WeaponUtil.die(target);
        }
        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getMainHandItem();
        if (level.isClientSide()) {
            return InteractionResultHolder.pass(stack);
        }
        if (player.isSteppingCarefully()) {
            CompoundTag compoundTag = Objects.requireNonNull(stack.get(DataComponents.CUSTOM_DATA)).copyTag();
            CompoundTag tag = compoundTag.getCompound(Manaitapp.MOD_ID);
            if (tag.contains(WeaponUtil.IS_TARGET_ALL)) {
                tag.putBoolean(WeaponUtil.IS_TARGET_ALL, tag.getBoolean(WeaponUtil.IS_TARGET_ALL));
            }else {
                tag.putBoolean(WeaponUtil.IS_TARGET_ALL, false);
            }
        }else {
            List<LivingEntity> targets = WeaponUtil.selectTargets(LivingEntity.class, level, player, WeaponUtil.RADIUS, entity -> (entity != player) && (entity instanceof Enemy));
            WeaponUtil.lightningStriker(targets, level, player);

        }
        return InteractionResultHolder.success(stack);
    }
}
