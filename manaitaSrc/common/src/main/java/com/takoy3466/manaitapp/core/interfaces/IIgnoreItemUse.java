package com.takoy3466.manaitapp.core.interfaces;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public interface IIgnoreItemUse {

    /**
     * アイテムの有無に関係ないuseを実装するときに使用します。
     * @param stack 手に持っているスタック、または任意のアイテムです。
     * @return trueの場合はSUCCESS、falseの場合はPASSを返すようここでは統一していますが、好きに変えてもらってかまいません。
     */
    boolean use(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult);
}
