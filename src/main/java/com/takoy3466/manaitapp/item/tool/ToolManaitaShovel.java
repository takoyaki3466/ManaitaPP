package com.takoy3466.manaitapp.item.tool;

import net.minecraft.world.item.ShovelItem;

public class ToolManaitaShovel extends ShovelItem {
    public ToolManaitaShovel() {
        super(ManaitaToolTier.MANAITA_TIER, new Properties().fireResistant());
    }
}
