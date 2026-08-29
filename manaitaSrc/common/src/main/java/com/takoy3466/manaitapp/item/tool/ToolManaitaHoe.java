package com.takoy3466.manaitapp.item.tool;

import com.takoy3466.manaitapp.dataComponent.SpreadGrowData;
import com.takoy3466.manaitapp.dataComponent.TillSoilData;
import com.takoy3466.manaitapp.dataComponent.helper.DataHelper;
import com.takoy3466.manaitapp.init.CompatData;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ToolManaitaHoe extends AbstractManaitaToolItem {
    public static int RADIUS = 10;

    public ToolManaitaHoe() {
        super(BlockTags.MINEABLE_WITH_HOE, new Properties()
                .component(CompatData.SPREAD_GROW_DATA.get(), new SpreadGrowData(RADIUS))
                .component(CompatData.TILL_SOIL_DATA.get(), new TillSoilData(true))
        );
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        tooltipComponents.add(DataHelper.hoverDataText(stack, CompatData.SPREAD_GROW_DATA.get(), data -> ": " + data.getMsg() + "x" + data.getMsg()));
    }
}
