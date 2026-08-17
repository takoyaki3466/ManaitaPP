package com.takoy3466.manaitapp.item.tool;

import com.takoy3466.manaitapp.dataComponent.SpreadGrowData;
import com.takoy3466.manaitapp.dataComponent.helper.DataHoverHelper;
import com.takoy3466.manaitapp.init.DataInit;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ToolManaitaHoe extends HoeItem {
    public static int RADIUS = 10;

    public ToolManaitaHoe() {
        super(ManaitaToolTier.MANAITA_TIER, new Properties().component(DataInit.SPREAD_GROW_DATA, new SpreadGrowData(RADIUS)));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        tooltipComponents.add(DataHoverHelper.hoverDataText(stack, DataInit.SPREAD_GROW_DATA.get(), data -> ": " + data.getMsg() + "x" + data.getMsg()));
    }
}
