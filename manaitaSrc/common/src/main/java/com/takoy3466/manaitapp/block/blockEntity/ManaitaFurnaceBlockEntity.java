package com.takoy3466.manaitapp.block.blockEntity;

import com.takoy3466.manaitapp.block.blockEntity.abstracts.AbstractManaitaFurnaceBlockEntity;
import com.takoy3466.manaitapp.core.ManaitaTier;
import com.takoy3466.manaitapp.init.CompatBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class ManaitaFurnaceBlockEntity {
    public static class Wood extends AbstractManaitaFurnaceBlockEntity {
        public Wood(BlockPos pos, BlockState blockState, ManaitaTier manaitaTier) {
            super(CompatBlockEntities.MANAITA_FURNACE_WOOD.get(), pos, blockState, manaitaTier);
        }
    }

    public static class Stone extends AbstractManaitaFurnaceBlockEntity {
        public Stone(BlockPos pos, BlockState blockState, ManaitaTier manaitaTier) {
            super(CompatBlockEntities.MANAITA_FURNACE_STONE.get(), pos, blockState, manaitaTier);
        }
    }

    public static class Iron extends AbstractManaitaFurnaceBlockEntity {
        public Iron(BlockPos pos, BlockState blockState, ManaitaTier manaitaTier) {
            super(CompatBlockEntities.MANAITA_FURNACE_IRON.get(), pos, blockState, manaitaTier);
        }
    }

    public static class Gold extends AbstractManaitaFurnaceBlockEntity {
        public Gold(BlockPos pos, BlockState blockState, ManaitaTier manaitaTier) {
            super(CompatBlockEntities.MANAITA_FURNACE_GOLD.get(), pos, blockState, manaitaTier);
        }
    }

    public static class Diamond extends AbstractManaitaFurnaceBlockEntity {
        public Diamond(BlockPos pos, BlockState blockState, ManaitaTier manaitaTier) {
            super(CompatBlockEntities.MANAITA_FURNACE_DIAMOND.get(), pos, blockState, manaitaTier);
        }
    }

    public static class Mtk extends AbstractManaitaFurnaceBlockEntity {
        public Mtk(BlockPos pos, BlockState blockState, ManaitaTier manaitaTier) {
            super(CompatBlockEntities.MANAITA_FURNACE_EMERALD.get(), pos, blockState, manaitaTier);
        }
    }
}
