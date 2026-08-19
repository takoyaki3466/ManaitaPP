package com.takoy3466.manaitapp.core.interfaces;

import com.takoy3466.manaitapp.init.DataInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * すべてのメソッドの戻り値は既存イベントをキャンセルするかどうかを示しています。
 */
public interface IDataAttachment {

    default boolean onRightClickItem(@NotNull Level level, @NotNull BlockPos pos, @NotNull Entity interactEntity, InteractionHand hand) {
        return false;
    }

    default boolean onRightClickBlock(@NotNull Level level, @NotNull BlockPos pos, @NotNull Entity interactEntity, InteractionHand hand) {
        return false;
    }

    default boolean onLeftClickItem(@NotNull Level level, @NotNull BlockPos pos, @NotNull Entity interactEntity, InteractionHand hand) {
        return false;
    }

    default boolean onLeftClickBlock(@NotNull Level level, @NotNull BlockPos pos, @NotNull Entity interactEntity, InteractionHand hand) {
        return false;
    }

    default boolean onKeyDown(@NotNull Level level, @NotNull Entity interactEntity, int key, int scanCode, int action) {
        return false;
    }

    default boolean onInvTick(@NotNull Level level, @NotNull Entity tickEntity) {
        return false;
    }

    default boolean onLivingEquipmentChange(@NotNull Level level, @NotNull Entity interactEntity, ItemStack currentStack, ItemStack previousStack) {
        return false;
    }

    default boolean onAttackEntity(@NotNull Level level, @NotNull Player player, @NotNull Entity target) {
        return false;
    }

    default <T> boolean equipmentChangeHelper(@NotNull Level level, @NotNull Entity interactEntity, ItemStack currentStack, ItemStack previousStack,
                                              @NotNull DataComponentType<T> type, Consumer<Player> noDataToHasDataConsumer, Consumer<Player> hasDataToNoDataConsumer
    ) {
        if (!(interactEntity instanceof Player player)) {
            return false;
        }
        if (level.isClientSide()) {
            return false;
        }
        if (player.isCreative() || player.isSpectator()) {
            return false;
        }
        // ManaitaFlyData (以後データと呼ぶ) が前後アイテムでそれぞれあるかチェック
        boolean hasDataPrevious = previousStack.has(type);
        boolean hasDataCurrent = currentStack.has(type);
        if (!hasDataPrevious) {
            if (hasDataCurrent) {
                noDataToHasDataConsumer.accept(player);

            }
        }else {
            if (!hasDataCurrent) {
                hasDataToNoDataConsumer.accept(player);

            }
        }
        // このイベントはキャンセルが発生しない
        return false;
    }
}
