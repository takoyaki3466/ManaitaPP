package com.takoy3466.manaitapp.item.tool;

import com.takoy3466.manaitapp.dataComponent.RangeBreakData;
import com.takoy3466.manaitapp.dataComponent.helper.DataHoverHelper;
import com.takoy3466.manaitapp.init.DataInit;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ToolManaitaPickaxe extends PickaxeItem {
    public ToolManaitaPickaxe() {
        super(ManaitaToolTier.MANAITA_TIER, new Properties().fireResistant().component(DataInit.RANGE_BREAK_DATA, new RangeBreakData(1)));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        tooltipComponents.add(DataHoverHelper.hoverDataText(stack, DataInit.RANGE_BREAK_DATA.get(), data -> ": " + data.getMsg() + "x" + data.getMsg()));
    }
}
