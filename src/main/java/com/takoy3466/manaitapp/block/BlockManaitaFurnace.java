package com.takoy3466.manaitapp.block;

import com.mojang.serialization.MapCodec;
import com.takoy3466.manaitapp.block.abstracts.AbstractBlockManaitaFurnace;
import com.takoy3466.manaitapp.block.blockEntity.ManaitaFurnaceBlockEntity;
import com.takoy3466.manaitapp.init.ManaitaTiers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockManaitaFurnace {
    public static class Wood extends AbstractBlockManaitaFurnace {
        public static MapCodec<? extends Wood> CODEC = simpleCodec(properties -> new Wood());

        public Wood() {
            super(ManaitaTiers.WOOD);
        }

        @Override
        protected @NotNull MapCodec<? extends AbstractFurnaceBlock> codec() {
            return CODEC;
        }

        @Override
        public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new ManaitaFurnaceBlockEntity.Wood(pos, state, getManaitaTier());
        }
    }

    public static class Stone extends AbstractBlockManaitaFurnace {
        public static MapCodec<? extends Stone> CODEC = simpleCodec(properties -> new Stone());

        public Stone() {
            super(ManaitaTiers.STONE);
        }

        @Override
        protected @NotNull MapCodec<? extends AbstractFurnaceBlock> codec() {
            return CODEC;
        }

        @Override
        public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new ManaitaFurnaceBlockEntity.Stone(pos, state, getManaitaTier());
        }
    }

    public static class Iron extends AbstractBlockManaitaFurnace {
        public static MapCodec<? extends Iron> CODEC = simpleCodec(properties -> new Iron());

        public Iron() {
            super(ManaitaTiers.IRON);
        }

        @Override
        protected @NotNull MapCodec<? extends AbstractFurnaceBlock> codec() {
            return CODEC;
        }

        @Override
        public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new ManaitaFurnaceBlockEntity.Iron(pos, state, getManaitaTier());
        }
    }

    public static class Gold extends AbstractBlockManaitaFurnace {
        public static MapCodec<? extends Gold> CODEC = simpleCodec(properties -> new Gold());

        public Gold() {
            super(ManaitaTiers.GOLD);
        }

        @Override
        protected @NotNull MapCodec<? extends AbstractFurnaceBlock> codec() {
            return CODEC;
        }

        @Override
        public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new ManaitaFurnaceBlockEntity.Gold(pos, state, getManaitaTier());
        }
    }

    public static class Diamond extends AbstractBlockManaitaFurnace {
        public static MapCodec<? extends Diamond> CODEC = simpleCodec(properties -> new Diamond());

        public Diamond() {
            super(ManaitaTiers.DIAMOND);
        }

        @Override
        protected @NotNull MapCodec<? extends AbstractFurnaceBlock> codec() {
            return CODEC;
        }

        @Override
        public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new ManaitaFurnaceBlockEntity.Diamond(pos, state, getManaitaTier());
        }
    }

    public static class Mtk extends AbstractBlockManaitaFurnace {
        public static MapCodec<? extends Mtk> CODEC = simpleCodec(properties -> new Mtk());

        public Mtk() {
            super(ManaitaTiers.MTK);
        }

        @Override
        protected @NotNull MapCodec<? extends AbstractFurnaceBlock> codec() {
            return CODEC;
        }

        @Override
        public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new ManaitaFurnaceBlockEntity.Mtk(pos, state, getManaitaTier());
        }
    }
}
