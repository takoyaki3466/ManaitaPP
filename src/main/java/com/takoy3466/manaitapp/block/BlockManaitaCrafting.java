package com.takoy3466.manaitapp.block;

import com.takoy3466.manaitapp.block.abstracts.AbstractBlockMultiple;
import com.takoy3466.manaitapp.core.ManaitaTier;
import com.takoy3466.manaitapp.core.interfaces.IIgnoreItemUse;
import com.takoy3466.manaitapp.dataComponent.helper.DataHelper;
import com.takoy3466.manaitapp.init.ManaitaTiers;
import com.takoy3466.manaitapp.menu.MenuManaitaCrafting;
import com.takoy3466.manaitapp.util.ManaitaShapeUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlockManaitaCrafting extends AbstractBlockMultiple implements IIgnoreItemUse {
    public static final DirectionProperty FACING = DirectionalBlock.FACING;

    public BlockManaitaCrafting(ManaitaTier manaitaTier) {
        super(Properties.of(), manaitaTier);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));
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

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return ManaitaShapeUtil.getShape(state.getValue(FACING));
    }

    @Override
    protected @NotNull VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return ManaitaShapeUtil.getShape(state.getValue(FACING));
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

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        if (getManaitaTier() == ManaitaTiers.WOOD) {
            tooltipComponents.add(Component.translatable("block.manaitapp.manaita_wood.hover_text").withStyle(ChatFormatting.GRAY));
        } else if (getManaitaTier() == ManaitaTiers.MTK) {
            tooltipComponents.add(Component.translatable("block.manaitapp.manaita_mtk.hover_text").withStyle(ChatFormatting.GRAY));
        }
    }
}
