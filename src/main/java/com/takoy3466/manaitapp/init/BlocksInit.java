package com.takoy3466.manaitapp.init;

import com.takoy3466.manaitapp.Manaitapp;
import com.takoy3466.manaitapp.block.BlockManaitaCrafting;
import com.takoy3466.manaitapp.block.BlockManaitaFurnace;
import com.takoy3466.manaitapp.block.abstracts.AbstractBlockManaitaFurnace;
import com.takoy3466.manaitapp.core.ManaitaTier;
import com.takoy3466.manaitapp.core.registry.holder.DoubleHolder;
import com.takoy3466.manaitapp.core.registry.register.DoubleRegister;
import com.takoy3466.manaitapp.core.registry.register.TieredDeferredRegister;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class BlocksInit {
    public static final TieredDeferredRegister<ManaitaTier, Block> TIER_REGISTER = TieredDeferredRegister.createBlocks(Manaitapp.MOD_ID);
    public static final DoubleRegister.BlockRegister BLOCKS = DoubleRegister.createBlock(Manaitapp.MOD_ID);

    public static DoubleHolder.BlockHolder<BlockManaitaCrafting> MANAITA_WOOD = registerManaita(ManaitaTiers.WOOD);
    public static DoubleHolder.BlockHolder<BlockManaitaCrafting> MANAITA_STONE = registerManaita(ManaitaTiers.STONE);
    public static DoubleHolder.BlockHolder<BlockManaitaCrafting> MANAITA_IRON = registerManaita(ManaitaTiers.IRON);
    public static DoubleHolder.BlockHolder<BlockManaitaCrafting> MANAITA_GOLD = registerManaita(ManaitaTiers.GOLD);
    public static DoubleHolder.BlockHolder<BlockManaitaCrafting> MANAITA_DIAMOND = registerManaita(ManaitaTiers.DIAMOND);
    public static DoubleHolder.BlockHolder<BlockManaitaCrafting> MANAITA_MTK = registerManaita(ManaitaTiers.MTK);

    public static DoubleHolder.BlockHolder<BlockManaitaFurnace.Wood> MANAITA_FURNACE_WOOD = registerFurnace(ManaitaTiers.WOOD, BlockManaitaFurnace.Wood::new);
    public static DoubleHolder.BlockHolder<BlockManaitaFurnace.Stone> MANAITA_FURNACE_STONE = registerFurnace(ManaitaTiers.STONE, BlockManaitaFurnace.Stone::new);
    public static DoubleHolder.BlockHolder<BlockManaitaFurnace.Iron> MANAITA_FURNACE_IRON = registerFurnace(ManaitaTiers.IRON, BlockManaitaFurnace.Iron::new);
    public static DoubleHolder.BlockHolder<BlockManaitaFurnace.Gold> MANAITA_FURNACE_GOLD = registerFurnace(ManaitaTiers.GOLD, BlockManaitaFurnace.Gold::new);
    public static DoubleHolder.BlockHolder<BlockManaitaFurnace.Diamond> MANAITA_FURNACE_DIAMOND = registerFurnace(ManaitaTiers.DIAMOND, BlockManaitaFurnace.Diamond::new);
    public static DoubleHolder.BlockHolder<BlockManaitaFurnace.Mtk> MANAITA_FURNACE_MTK = registerFurnace(ManaitaTiers.MTK, BlockManaitaFurnace.Mtk::new);

    public static DoubleHolder.BlockHolder<BlockManaitaCrafting> registerManaita(ManaitaTier manaitaTier) {
        return BLOCKS.register("manaita_" + manaitaTier.getName(), () -> new BlockManaitaCrafting(manaitaTier), new Item.Properties());
    }

    public static <T extends AbstractBlockManaitaFurnace> DoubleHolder.BlockHolder<T> registerFurnace(ManaitaTier manaitaTier, Supplier<T> sup) {
        return BLOCKS.register("manaita_furnace_" + manaitaTier.getName(), sup, new Item.Properties());
    }

}
