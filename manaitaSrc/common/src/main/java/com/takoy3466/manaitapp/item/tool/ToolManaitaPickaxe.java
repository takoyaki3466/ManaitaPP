package com.takoy3466.manaitapp.item.tool;

import com.takoy3466.manaitapp.dataComponent.InstantAllDropBlockData;
import com.takoy3466.manaitapp.dataComponent.RangeBreakData;
import com.takoy3466.manaitapp.dataComponent.helper.DataHelper;
import com.takoy3466.manaitapp.init.CompatData;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ToolManaitaPickaxe extends AbstractManaitaToolItem {
    public ToolManaitaPickaxe() {
        super(BlockTags.MINEABLE_WITH_PICKAXE, new Properties().fireResistant()
                .component(CompatData.RANGE_BREAK_DATA.get(), new RangeBreakData(1))
                .component(CompatData.INSTANT_BLOCK_DATA.get(), new InstantAllDropBlockData(true))
        );
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        tooltipComponents.add(DataHelper.hoverDataText(stack, CompatData.RANGE_BREAK_DATA.get(), data -> ": " + data.getMsg() + "x" + data.getMsg()));
    }
}
