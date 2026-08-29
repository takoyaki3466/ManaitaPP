package com.takoy3466.manaitapp.init;

import com.takoy3466.manaitapp.core.registry.holder.CompatHolder;
import com.takoy3466.manaitapp.core.registry.CompatRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CompatTabs {

    public static final List<CompatHolder<? extends Item>> ITEMS = new ArrayList<>();

    public static final CompatHolder<CreativeModeTab> MANAITA_TAB = CompatRegistry.registerCreativeTab(  "manaita_tab",() -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
            .title(Component.translatable("itemGroup.manaitapp"))
            .icon(() -> new ItemStack(CompatBlocks.MANAITA_MTK.getItem()))
            .displayItems((itemDisplayParameters, output) ->
                    ITEMS.forEach(compatHolder -> output.accept(compatHolder.get()))
            ).build());

    public static void init() {
    }

}
