package com.takoy3466.manaitapp.item;

import com.takoy3466.manaitapp.dataComponent.CrushedManaitaData;
import com.takoy3466.manaitapp.init.CompatData;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class CrushedManaita extends Item {
    public static int MULTIPLE = 64;

    public CrushedManaita() {
        super(new Item.Properties().component(CompatData.CRUSHED_DATA.get(), new CrushedManaitaData(MULTIPLE)));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.translatable("item.manaitapp.crushed_manaita.hover_text").withStyle(ChatFormatting.GRAY));
    }
}
