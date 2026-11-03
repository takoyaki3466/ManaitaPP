package com.takoy3466.manaitapp.block;

import com.mojang.serialization.MapCodec;
import com.takoy3466.manaitapp.block.abstracts.AbstractBlockManaitaFurnace;
import com.takoy3466.manaitapp.block.blockEntity.ManaitaFurnaceBlockEntity;
import com.takoy3466.manaitapp.init.ManaitaTiers;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlockManaitaFurnace {
    public static class Wood extends AbstractBlockManaitaFurnace {
        public static MapCodec<? extends Wood> CODEC = simpleCodec(Wood::new);

        public Wood(Properties properties) {
            super(properties, ManaitaTiers.WOOD);
        }

        @Override
        protected @NotNull MapCodec<? extends AbstractBlockManaitaFurnace> codec() {
            return CODEC;
        }

        @Override
        public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new ManaitaFurnaceBlockEntity.Wood(pos, state, getManaitaTier());
        }

        @Override
        public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
            super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
            tooltipComponents.add(Component.translatable("block.manaitapp.manaita_furnace_wood.hover_text").withStyle(ChatFormatting.GRAY));
        }
    }

    public static class Stone extends AbstractBlockManaitaFurnace {
        public static MapCodec<? extends Stone> CODEC = simpleCodec(Stone::new);

        public Stone(Properties properties) {
           super(properties, ManaitaTiers.STONE);
        }

        @Override
        protected @NotNull MapCodec<? extends AbstractBlockManaitaFurnace> codec() {
            return CODEC;
        }

        @Override
        public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new ManaitaFurnaceBlockEntity.Stone(pos, state, getManaitaTier());
        }
    }

    public static class Iron extends AbstractBlockManaitaFurnace {
        public static MapCodec<? extends Iron> CODEC = simpleCodec(Iron::new);

        public Iron(Properties properties) {
           super(properties, ManaitaTiers.IRON);
        }

        @Override
        protected @NotNull MapCodec<? extends AbstractBlockManaitaFurnace> codec() {
            return CODEC;
        }

        @Override
        public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new ManaitaFurnaceBlockEntity.Iron(pos, state, getManaitaTier());
        }
    }

    public static class Gold extends AbstractBlockManaitaFurnace {
        public static MapCodec<? extends Gold> CODEC = simpleCodec(Gold::new);

        public Gold(Properties properties) {
           super(properties, ManaitaTiers.GOLD);
        }

        @Override
        protected @NotNull MapCodec<? extends AbstractBlockManaitaFurnace> codec() {
            return CODEC;
        }

        @Override
        public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new ManaitaFurnaceBlockEntity.Gold(pos, state, getManaitaTier());
        }
    }

    public static class Diamond extends AbstractBlockManaitaFurnace {
        public static MapCodec<? extends Diamond> CODEC = simpleCodec(Diamond::new);

        public Diamond(Properties properties) {
           super(properties, ManaitaTiers.DIAMOND);
        }

        @Override
        protected @NotNull MapCodec<? extends AbstractBlockManaitaFurnace> codec() {
            return CODEC;
        }

        @Override
        public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new ManaitaFurnaceBlockEntity.Diamond(pos, state, getManaitaTier());
        }
    }

    public static class Mtk extends AbstractBlockManaitaFurnace {
        public static MapCodec<? extends Mtk> CODEC = simpleCodec(Mtk::new);

        public Mtk(Properties properties) {
           super(properties, ManaitaTiers.MTK);
        }

        @Override
        protected @NotNull MapCodec<? extends AbstractBlockManaitaFurnace> codec() {
            return CODEC;
        }

        @Override
        public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new ManaitaFurnaceBlockEntity.Mtk(pos, state, getManaitaTier());
        }
    }
}
