package com.takoy3466.manaitapp.init;

import com.takoy3466.manaitapp.block.BlockManaitaCrafting;
import com.takoy3466.manaitapp.block.BlockManaitaFurnace;
import com.takoy3466.manaitapp.block.abstracts.AbstractBlockManaitaFurnace;
import com.takoy3466.manaitapp.core.registry.holder.CompatDoubleHolder;
import com.takoy3466.manaitapp.core.registry.CompatRegistry;
import com.takoy3466.manaitapp.core.ManaitaTier;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class CompatBlocks {

    public static final CompatDoubleHolder.BlockHolder<BlockManaitaCrafting> MANAITA_WOOD = registerManaita(ManaitaTiers.WOOD);
    public static final CompatDoubleHolder.BlockHolder<BlockManaitaCrafting> MANAITA_STONE = registerManaita(ManaitaTiers.STONE);
    public static final CompatDoubleHolder.BlockHolder<BlockManaitaCrafting> MANAITA_IRON = registerManaita(ManaitaTiers.IRON);
    public static final CompatDoubleHolder.BlockHolder<BlockManaitaCrafting> MANAITA_GOLD = registerManaita(ManaitaTiers.GOLD);
    public static final CompatDoubleHolder.BlockHolder<BlockManaitaCrafting> MANAITA_DIAMOND = registerManaita(ManaitaTiers.DIAMOND);
    public static final CompatDoubleHolder.BlockHolder<BlockManaitaCrafting> MANAITA_MTK = registerManaita(ManaitaTiers.MTK);

    public static final CompatDoubleHolder.BlockHolder<BlockManaitaFurnace.Wood> MANAITA_FURNACE_WOOD = registerFurnace(ManaitaTiers.WOOD, BlockManaitaFurnace.Wood::new);
    public static final CompatDoubleHolder.BlockHolder<BlockManaitaFurnace.Stone> MANAITA_FURNACE_STONE = registerFurnace(ManaitaTiers.STONE, BlockManaitaFurnace.Stone::new);
    public static final CompatDoubleHolder.BlockHolder<BlockManaitaFurnace.Iron> MANAITA_FURNACE_IRON = registerFurnace(ManaitaTiers.IRON, BlockManaitaFurnace.Iron::new);
    public static final CompatDoubleHolder.BlockHolder<BlockManaitaFurnace.Gold> MANAITA_FURNACE_GOLD = registerFurnace(ManaitaTiers.GOLD, BlockManaitaFurnace.Gold::new);
    public static final CompatDoubleHolder.BlockHolder<BlockManaitaFurnace.Diamond> MANAITA_FURNACE_DIAMOND = registerFurnace(ManaitaTiers.DIAMOND, BlockManaitaFurnace.Diamond::new);
    public static final CompatDoubleHolder.BlockHolder<BlockManaitaFurnace.Mtk> MANAITA_FURNACE_MTK = registerFurnace(ManaitaTiers.MTK, BlockManaitaFurnace.Mtk::new);

    public static CompatDoubleHolder.BlockHolder<BlockManaitaCrafting> registerManaita(ManaitaTier manaitaTier) {
        return CompatRegistry.registerBlock("manaita_" + manaitaTier.getName(), () -> new BlockManaitaCrafting(manaitaTier), new Item.Properties());
    }

    public static <T extends AbstractBlockManaitaFurnace> CompatDoubleHolder.BlockHolder<T> registerFurnace(ManaitaTier manaitaTier, Supplier<T> sup) {
        return CompatRegistry.registerBlock("manaita_furnace_" + manaitaTier.getName(), sup, new Item.Properties());
    }

    public static void init() {
    }
}
