package com.takoy3466.manaitapp.item.tool;

import com.takoy3466.manaitapp.dataComponent.LightningStrikerData;
import com.takoy3466.manaitapp.dataComponent.ManaitaKillData;
import com.takoy3466.manaitapp.dataComponent.helper.DataHoverHelper;
import com.takoy3466.manaitapp.init.DataInit;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ToolManaitaSword extends SwordItem {
    public ToolManaitaSword() {
        super(ManaitaToolTier.MANAITA_TIER, new Item.Properties().fireResistant()
                .component(DataInit.STRIKER_DATA, new LightningStrikerData(false))
                .component(DataInit.KILL_DATA, new ManaitaKillData(true))
        );
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        tooltipComponents.add(DataHoverHelper.hoverDataText(stack, DataInit.STRIKER_DATA.get(), data -> ": " + data.getMsg()));
    }
}
