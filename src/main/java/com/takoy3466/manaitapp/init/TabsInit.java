package com.takoy3466.manaitapp.init;

import com.takoy3466.manaitapp.Manaitapp;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TabsInit {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Manaitapp.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MANAITA_TAB = TABS.register("manaita_tab",() -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .title(Component.translatable("itemGroup.manaitapp"))
            .icon(() -> new ItemStack(BlocksInit.MANAITA_MTK.getItem()))
            .displayItems(((parameters, output) -> {
                BlocksInit.BLOCKS.getRegister().getBehind().getEntries().stream().map(DeferredHolder::get).forEach(output::accept);
                ItemsInit.ITEMS.getEntries().stream().map(DeferredHolder::get).forEach(output::accept);
            })
            ).build());
}
