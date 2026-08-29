package com.takoy3466.manaitapp.core.interfaces;

import com.takoy3466.manaitapp.core.ManaitaTier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

@FunctionalInterface
public interface ManaitaBlockEntitySupplier<T extends BlockEntity> {
    T create(BlockPos var1, BlockState var2, ManaitaTier manaitaTier);
}
