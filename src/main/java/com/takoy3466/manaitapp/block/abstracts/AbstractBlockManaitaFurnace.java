package com.takoy3466.manaitapp.block.abstracts;

import com.takoy3466.manaitapp.block.blockEntity.abstracts.AbstractManaitaFurnaceBlockEntity;
import com.takoy3466.manaitapp.core.ManaitaTier;
import com.takoy3466.manaitapp.core.interfaces.IMultiple;
import com.takoy3466.manaitapp.core.interfaces.ITickableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractBlockManaitaFurnace extends AbstractFurnaceBlock implements IMultiple {
    private final ManaitaTier manaitaTier;

    protected AbstractBlockManaitaFurnace(ManaitaTier manaitaTier) {
        super(Properties.of());
        this.manaitaTier = manaitaTier;
    }

    @Override
    protected void openContainer(Level level, BlockPos pos, Player player) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof AbstractManaitaFurnaceBlockEntity furnaceBlockEntity) {
            player.openMenu(furnaceBlockEntity);
        }
    }
    // 今後書く
    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide()) {
            return null;
        }
        return ITickableBlockEntity.getTickerHelper(level);
    }

    @Override
    public abstract @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state);

    @Override
    public ManaitaTier getManaitaTier() {
        return manaitaTier;
    }

    @Override
    public int getMultiple() {
        return manaitaTier.getMultiple();
    }
}
