package com.takoy3466.manaitapp.init;

import com.takoy3466.manaitapp.ManaitaPPCommon;
import com.takoy3466.manaitapp.core.Identifier;
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
import com.takoy3466.manaitapp.util.RegistryUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;

import java.util.function.Function;
import java.util.function.Supplier;

public class CompatItems {
    
    public static final CompatHolder<ManaitaOrigin> MANAITA_ORIGIN = register("manaita_origin", ManaitaOrigin::new);

    public static final CompatHolder<CrushedManaita> CRUSHED_MANAITA = register("crushed_manaita", CrushedManaita::new);
    public static final CompatHolder<AbstractManaitaToolItem> MANAITA_AXE = register("manaita_axe", ToolManaitaAxe::new);
    public static final CompatHolder<AbstractManaitaToolItem> MANAITA_PAXEL = register("manaita_paxel", ToolManaitaPaxel::new);
    public static final CompatHolder<AbstractManaitaToolItem> MANAITA_PICKAXE = register("manaita_pickaxe", ToolManaitaPickaxe::new);
    public static final CompatHolder<AbstractManaitaToolItem> MANAITA_SHOVEL = register("manaita_shovel", ToolManaitaShovel::new);
    public static final CompatHolder<AbstractManaitaToolItem> MANAITA_HOE = register("manaita_hoe", ToolManaitaHoe::new);

    public static final CompatHolder<AbstractManaitaToolItem> MANAITA_SWORD = register("manaita_sword", ToolManaitaSword::new);
    
    public static final CompatHolder<BowItem> MANAITA_BOW = register("manaita_bow", ManaitaBow::new);

    public static final CompatHolder<ManaitaHelmet> MANAITA_HELMET = register("manaita_helmet", ManaitaHelmet::new);
    public static final CompatHolder<ManaitaChestplate> MANAITA_CHESTPLATE = register("manaita_chestplate", ManaitaChestplate::new);
    public static final CompatHolder<ManaitaLeggings> MANAITA_LEGGINGS = register("manaita_leggings", ManaitaLeggings::new);
    public static final CompatHolder<ManaitaBoots> MANAITA_BOOTS = register("manaita_boots", ManaitaBoots::new);

    public static <T extends Item> CompatHolder<T> register(String name, Function<Item.Properties, T> function) {
        ResourceKey<Item> itemId = RegistryUtil.getItemId(ManaitaPPCommon.MOD_ID, name);
        return CompatRegistry.registerItem(name, () -> function.apply(new Item.Properties().setId(itemId)));
    }

    public static void init() {
    }
}
