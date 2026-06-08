package com.takoy3466.manaitapp.block;

import com.takoy3466.manaitapp.block.abstracts.AbstractBlockMultiple;
import com.takoy3466.manaitapp.core.ManaitaTier;
import com.takoy3466.manaitapp.core.interfaces.IIgnoreItemUse;
import com.takoy3466.manaitapp.menu.MenuManaitaCrafting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class BlockManaitaCrafting extends AbstractBlockMultiple implements IIgnoreItemUse {
    public BlockManaitaCrafting(ManaitaTier manaitaTier) {
        super(Properties.of(), manaitaTier);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (use(player.getMainHandItem(), state, level, pos, player, InteractionHand.MAIN_HAND, hitResult)) {
            return InteractionResult.SUCCESS;
        }else {
            return InteractionResult.PASS;
        }
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (use(stack, state, level, pos, player, hand, hitResult)) {
            return ItemInteractionResult.SUCCESS;
        }else {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
    }

    @Override
    public boolean use(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(createMenuProvider(level, pos));
        }
        return true;
    }

    private MenuProvider createMenuProvider(Level level, BlockPos pos) {
        BlockManaitaCrafting blockManaitaCrafting = this;
        return new MenuProvider() {
            @Override
            public @NotNull Component getDisplayName() {
                return Component.literal("test");
            }

            @Override
            public @NotNull AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                return new MenuManaitaCrafting(id, inventory, ContainerLevelAccess.create(level, pos), getManaitaTier(), blockManaitaCrafting);
            }
        };
    }
}
