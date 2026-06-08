package com.takoy3466.manaitapp.item.tool;

import com.takoy3466.manaitapp.dataComponent.WoodReverseData;
import com.takoy3466.manaitapp.init.DataInit;
import net.minecraft.world.item.AxeItem;

public class ToolManaitaAxe extends AxeItem {
    public ToolManaitaAxe() {
        super(ManaitaToolTier.MANAITA_TIER, new Properties().fireResistant().component(DataInit.WOOD_REVERSE, new WoodReverseData(true)));
    }
}
