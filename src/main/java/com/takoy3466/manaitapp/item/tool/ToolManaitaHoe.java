package com.takoy3466.manaitapp.item.tool;

import com.takoy3466.manaitapp.dataComponent.SpreadGrowData;
import com.takoy3466.manaitapp.init.DataInit;
import net.minecraft.world.item.HoeItem;

public class ToolManaitaHoe extends HoeItem {
    public static int RADIUS = 10;

    public ToolManaitaHoe() {
        super(ManaitaToolTier.MANAITA_TIER, new Properties().component(DataInit.SPREAD_GROW_DATA, new SpreadGrowData(RADIUS)));
    }
}
