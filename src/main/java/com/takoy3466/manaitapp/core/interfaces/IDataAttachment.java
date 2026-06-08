package com.takoy3466.manaitapp.core.interfaces;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * すべてのメソッドの戻り値は既存イベントをキャンセルするかどうかを示しています。
 */
public interface IDataAttachment {

    default boolean onRightClickItem(Level level, BlockPos pos, Entity entity, InteractionHand hand) {
        return false;
    }

    default boolean onRightClickBlock(Level level, BlockPos pos, Entity entity, InteractionHand hand) {
        return false;
    }

    default boolean onLeftClickItem(Level level, BlockPos pos, Entity entity, InteractionHand hand) {
        return false;
    }

    default boolean onLeftClickBlock(Level level, BlockPos pos, Entity entity, InteractionHand hand) {
        return false;
    }

    default boolean onKeyDown(Level level, Entity entity, int key, int scanCode, int action) {
        return false;
    }

    default boolean onInvTick(Level level, Entity entity) {
        return false;
    }

    default boolean onLivingEquipmentChange(Level level, Entity entity, ItemStack currentStack, ItemStack previousStack) {
        return false;
    }
}
