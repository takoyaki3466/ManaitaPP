package com.takoy3466.manaitapp.item.tool;

import net.minecraft.tags.BlockTags;

public class ToolManaitaShovel extends AbstractManaitaToolItem {
    public ToolManaitaShovel() {
        super(BlockTags.MINEABLE_WITH_HOE, new Properties().fireResistant());
    }


}
