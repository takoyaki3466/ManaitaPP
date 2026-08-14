package com.takoy3466.manaitapp.init;

import com.takoy3466.manaitapp.Manaitapp;
import com.takoy3466.manaitapp.item.CrushedManaita;
import com.takoy3466.manaitapp.item.ManaitaBow;
import com.takoy3466.manaitapp.item.ManaitaOrigin;
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
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Manaitapp.MOD_ID);

    public static final DeferredItem<Item> MANAITA_ORIGIN = ITEMS.register("manaita_origin", ManaitaOrigin::new);

    public static final DeferredItem<Item> CRUSHED_MANAITA = ITEMS.register("crushed_mtk", CrushedManaita::new);
    public static final DeferredHolder<Item, AxeItem> MANAITA_AXE = ITEMS.register("manaita_axe", ToolManaitaAxe::new);
    public static final DeferredHolder<Item, Item> MANAITA_PAXEL = ITEMS.register("manaita_paxel", ToolManaitaPaxel::new);
    public static final DeferredHolder<Item, PickaxeItem> MANAITA_PICKAXE = ITEMS.register("manaita_pickaxe", ToolManaitaPickaxe::new);
    public static final DeferredHolder<Item, ShovelItem> MANAITA_SHOVEL = ITEMS.register("manaita_shovel", ToolManaitaShovel::new);
    public static final DeferredHolder<Item, HoeItem> MANAITA_HOE = ITEMS.register("manaita_hoe", ToolManaitaHoe::new);

    public static final DeferredHolder<Item, SwordItem> MANAITA_SWORD = ITEMS.register("manaita_sword", ToolManaitaSword::new);
    
    public static final DeferredHolder<Item, BowItem> MANAITA_BOW = ITEMS.register("manaita_bow", ManaitaBow::new);

    public static final DeferredHolder<Item, ArmorItem> MANAITA_HELMET = ITEMS.register("manaita_helmet", ManaitaHelmet::new);
    public static final DeferredHolder<Item, ArmorItem> MANAITA_CHESTPLATE = ITEMS.register("manaita_chestplate", ManaitaChestplate::new);
    public static final DeferredHolder<Item, ArmorItem> MANAITA_LEGGINGS = ITEMS.register("manaita_leggings", ManaitaLeggings::new);
    public static final DeferredHolder<Item, ArmorItem> MANAITA_BOOTS = ITEMS.register("manaita_boots", ManaitaBoots::new);
}
