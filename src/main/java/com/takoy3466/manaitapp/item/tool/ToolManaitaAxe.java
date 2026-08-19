package com.takoy3466.manaitapp.item.tool;

import com.takoy3466.manaitapp.dataComponent.WoodReverseData;
import com.takoy3466.manaitapp.dataComponent.helper.DataHoverHelper;
import com.takoy3466.manaitapp.init.DataInit;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ToolManaitaAxe extends AxeItem {
    public ToolManaitaAxe() {
        super(ManaitaToolTier.MANAITA_TIER, new Properties().fireResistant().component(DataInit.WOOD_REVERSE_DATA, new WoodReverseData(true)));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        tooltipComponents.add(DataHoverHelper.hoverDataText(stack, DataInit.RANGE_BREAK_DATA.get(), data -> ""));
    }
}
