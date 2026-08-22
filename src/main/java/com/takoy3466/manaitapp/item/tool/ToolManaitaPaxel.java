package com.takoy3466.manaitapp.item.tool;

import com.takoy3466.manaitapp.dataComponent.InstantAllDropBlockData;
import com.takoy3466.manaitapp.dataComponent.ManaitaKillData;
import com.takoy3466.manaitapp.dataComponent.RangeBreakData;
import com.takoy3466.manaitapp.dataComponent.helper.DataHelper;
import com.takoy3466.manaitapp.init.DataInit;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ToolManaitaPaxel extends AbstractManaitaToolItem {


    public ToolManaitaPaxel() {
        super(BlockTags.MINEABLE_WITH_PICKAXE, new Item.Properties().fireResistant()
                .component(DataInit.RANGE_BREAK_DATA, new RangeBreakData(1))
                .component(DataInit.KILL_DATA, new ManaitaKillData(true))
                .component(DataInit.INSTANT_BLOCK_DATA, new InstantAllDropBlockData(true))
                .rarity(Rarity.UNCOMMON)
        );
    }

    @Override
    public boolean isCorrectToolForDrops(@NotNull ItemStack stack, @NotNull BlockState state) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        tooltipComponents.add(DataHelper.hoverDataText(stack, DataInit.RANGE_BREAK_DATA.get(), data -> ": " + data.getMsg() + "x" + data.getMsg()));
    }
}
