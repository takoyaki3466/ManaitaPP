package com.takoy3466.manaitapp.item.tool;

import com.takoy3466.manaitapp.dataComponent.RangeBreakData;
import com.takoy3466.manaitapp.init.DataInit;
import net.minecraft.world.item.PickaxeItem;

public class ToolManaitaPickaxe extends PickaxeItem {
    public ToolManaitaPickaxe() {
        super(ManaitaToolTier.MANAITA_TIER, new Properties().fireResistant().component(DataInit.RANGE_BREAK, new RangeBreakData(1)));
    }
}
