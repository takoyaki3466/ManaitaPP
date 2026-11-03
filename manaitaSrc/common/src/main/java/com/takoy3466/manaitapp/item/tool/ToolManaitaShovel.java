package com.takoy3466.manaitapp.item.tool;

import net.minecraft.tags.BlockTags;

public class ToolManaitaShovel extends AbstractManaitaToolItem {
    public ToolManaitaShovel(Properties properties) {
        super(BlockTags.MINEABLE_WITH_HOE, properties.fireResistant());
    }


}
