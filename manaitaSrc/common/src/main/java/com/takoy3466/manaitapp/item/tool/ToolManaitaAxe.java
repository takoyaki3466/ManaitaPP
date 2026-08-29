package com.takoy3466.manaitapp.item.tool;

import com.takoy3466.manaitapp.dataComponent.StripWoodData;
import com.takoy3466.manaitapp.dataComponent.WoodReverseData;
import com.takoy3466.manaitapp.init.CompatData;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ToolManaitaAxe extends AbstractManaitaToolItem {
    public ToolManaitaAxe() {
        super(BlockTags.MINEABLE_WITH_AXE, new Properties().fireResistant()
                .component(CompatData.WOOD_REVERSE_DATA.get(), new WoodReverseData(true))
                .component(CompatData.STRIP_WOOD_DATA.get(), new StripWoodData(true))
        );
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        tooltipComponents.add(Component.translatable("item.manaitapp.manaita_axe.hover_text").withStyle(ChatFormatting.GRAY));
    }
}
