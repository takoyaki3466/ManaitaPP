package com.takoy3466.manaitapp.init;

import com.takoy3466.manaitapp.ManaitaPPCommon;
import com.takoy3466.manaitapp.block.BlockManaitaCrafting;
import com.takoy3466.manaitapp.block.BlockManaitaFurnace;
import com.takoy3466.manaitapp.block.ManaitaBlockItem;
import com.takoy3466.manaitapp.block.abstracts.AbstractBlockManaitaFurnace;
import com.takoy3466.manaitapp.core.registry.holder.CompatDoubleHolder;
import com.takoy3466.manaitapp.core.registry.CompatRegistry;
import com.takoy3466.manaitapp.core.ManaitaTier;
import com.takoy3466.manaitapp.core.registry.holder.CompatHolder;
import com.takoy3466.manaitapp.util.RegistryUtil;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;
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
        String name = "manaita_" + manaitaTier.getName();
        ResourceKey<Block> blockId = RegistryUtil.getBlockId(ManaitaPPCommon.MOD_ID, name);
        ResourceKey<Item> itemId = RegistryUtil.getItemId(ManaitaPPCommon.MOD_ID, name);
        return CompatRegistry.registerBlock(name, () -> new BlockManaitaCrafting(BlockBehaviour.Properties.of().setId(blockId), manaitaTier), block -> new ManaitaBlockItem(block, new Item.Properties().setId(itemId)));
    }

    public static <T extends AbstractBlockManaitaFurnace> CompatDoubleHolder.BlockHolder<T> registerFurnace(ManaitaTier manaitaTier, Function<BlockBehaviour.Properties, T> function) {
        String name = "manaita_furnace_" + manaitaTier.getName();
        ResourceKey<Block> blockId = RegistryUtil.getBlockId(ManaitaPPCommon.MOD_ID, name);
        ResourceKey<Item> itemId = RegistryUtil.getItemId(ManaitaPPCommon.MOD_ID, name);
        return CompatRegistry.registerBlock(name, () -> function.apply(BlockBehaviour.Properties.of().setId(blockId)), block -> new ManaitaBlockItem(block, new Item.Properties().setId(itemId)));
    }

    public static <T extends Block> CompatDoubleHolder.BlockHolder<T> register(String name, Function<BlockBehaviour.Properties, T> function) {
        ResourceKey<Block> blockId = RegistryUtil.getBlockId(ManaitaPPCommon.MOD_ID, name);
        ResourceKey<Item> itemId = RegistryUtil.getItemId(ManaitaPPCommon.MOD_ID, name);
        return CompatRegistry.registerBlock(name, () -> function.apply(BlockBehaviour.Properties.of().setId(blockId)), block -> new ManaitaBlockItem(block, new Item.Properties().setId(itemId)));
    }

    public static void init() {
    }
}
