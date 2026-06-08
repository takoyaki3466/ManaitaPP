package com.takoy3466.manaitapp.init;

import com.takoy3466.manaitapp.Manaitapp;
import com.takoy3466.manaitapp.item.CrushedManaita;
import com.takoy3466.manaitapp.item.armor.ManaitaBoots;
import com.takoy3466.manaitapp.item.armor.ManaitaChestplate;
import com.takoy3466.manaitapp.item.armor.ManaitaHelmet;
import com.takoy3466.manaitapp.item.armor.ManaitaLeggings;
import com.takoy3466.manaitapp.item.tool.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemsInit {
    public static DeferredRegister.Items ITEMS = DeferredRegister.createItems(Manaitapp.MOD_ID);

    public static DeferredItem<Item> CRUSHED_MANAITA = ITEMS.register("crushed_mtk", CrushedManaita::new);
    public static DeferredHolder<Item, AxeItem> MANAITA_AXE = ITEMS.register("axe_item", ToolManaitaAxe::new);
    public static DeferredHolder<Item, Item> MANAITA_PAXEL = ITEMS.register("manaita_paxel", ToolManaitaPaxel::new);
    public static DeferredHolder<Item, PickaxeItem> MANAITA_PICKAXE = ITEMS.register("manaita_pickaxe", ToolManaitaPickaxe::new);
    public static DeferredHolder<Item, ShovelItem> MANAITA_SHOVEL = ITEMS.register("manaita_shovel", ToolManaitaShovel::new);

    public static DeferredHolder<Item, SwordItem> MANAITA_SWORD = ITEMS.register("manaita_sword", ToolManaitaSword::new);

    public static DeferredHolder<Item, ArmorItem> MANAITA_HELMET = ITEMS.register("manaita_helmet", ManaitaHelmet::new);
    public static DeferredHolder<Item, ArmorItem> MANAITA_CHESTPLATE = ITEMS.register("manaita_chestplate", ManaitaChestplate::new);
    public static DeferredHolder<Item, ArmorItem> MANAITA_LEGGINGS = ITEMS.register("manaita_leggings", ManaitaLeggings::new);
    public static DeferredHolder<Item, ArmorItem> MANAITA_BOOTS = ITEMS.register("manaita_boots", ManaitaBoots::new);
}
