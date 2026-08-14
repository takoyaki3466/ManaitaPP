package com.takoy3466.manaitapp.item.tool;

import com.takoy3466.manaitapp.util.MTKUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MTKSword extends Item {
    private final Component HOVER_TEXT = Component.translatable("");
    public MTKSword() {
        super(new Properties());
    }

    @Override
    public @NotNull Component getName(ItemStack stack) {
        return MTKUtil.renderRainbow(super.getName(stack).getString());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        Component rainbow = MTKUtil.renderRainbow(HOVER_TEXT.getString());
        tooltipComponents.add(rainbow);
    }
}
