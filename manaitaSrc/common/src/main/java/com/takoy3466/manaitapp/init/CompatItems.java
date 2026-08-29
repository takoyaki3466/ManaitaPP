package com.takoy3466.manaitapp.init;

import com.takoy3466.manaitapp.core.registry.CompatRegistry;
import com.takoy3466.manaitapp.core.registry.holder.CompatHolder;
import com.takoy3466.manaitapp.item.CrushedManaita;
import com.takoy3466.manaitapp.item.ManaitaBow;
import com.takoy3466.manaitapp.item.ManaitaOrigin;
import com.takoy3466.manaitapp.item.armor.ManaitaBoots;
import com.takoy3466.manaitapp.item.armor.ManaitaChestplate;
import com.takoy3466.manaitapp.item.armor.ManaitaHelmet;
import com.takoy3466.manaitapp.item.armor.ManaitaLeggings;
import com.takoy3466.manaitapp.item.tool.*;
import net.minecraft.world.item.BowItem;

public class CompatItems {
    
    public static final CompatHolder<ManaitaOrigin> MANAITA_ORIGIN = CompatRegistry.registerItem("manaita_origin", ManaitaOrigin::new);

    public static final CompatHolder<CrushedManaita> CRUSHED_MANAITA = CompatRegistry.registerItem("crushed_manaita", CrushedManaita::new);
    public static final CompatHolder<AbstractManaitaToolItem> MANAITA_AXE = CompatRegistry.registerItem("manaita_axe", ToolManaitaAxe::new);
    public static final CompatHolder<AbstractManaitaToolItem> MANAITA_PAXEL = CompatRegistry.registerItem("manaita_paxel", ToolManaitaPaxel::new);
    public static final CompatHolder<AbstractManaitaToolItem> MANAITA_PICKAXE = CompatRegistry.registerItem("manaita_pickaxe", ToolManaitaPickaxe::new);
    public static final CompatHolder<AbstractManaitaToolItem> MANAITA_SHOVEL = CompatRegistry.registerItem("manaita_shovel", ToolManaitaShovel::new);
    public static final CompatHolder<AbstractManaitaToolItem> MANAITA_HOE = CompatRegistry.registerItem("manaita_hoe", ToolManaitaHoe::new);

    public static final CompatHolder<AbstractManaitaToolItem> MANAITA_SWORD = CompatRegistry.registerItem("manaita_sword", ToolManaitaSword::new);
    
    public static final CompatHolder<BowItem> MANAITA_BOW = CompatRegistry.registerItem("manaita_bow", ManaitaBow::new);

    public static final CompatHolder<ManaitaHelmet> MANAITA_HELMET = CompatRegistry.registerItem("manaita_helmet", ManaitaHelmet::new);
    public static final CompatHolder<ManaitaChestplate> MANAITA_CHESTPLATE = CompatRegistry.registerItem("manaita_chestplate", ManaitaChestplate::new);
    public static final CompatHolder<ManaitaLeggings> MANAITA_LEGGINGS = CompatRegistry.registerItem("manaita_leggings", ManaitaLeggings::new);
    public static final CompatHolder<ManaitaBoots> MANAITA_BOOTS = CompatRegistry.registerItem("manaita_boots", ManaitaBoots::new);

    public static void init() {
    }
}
