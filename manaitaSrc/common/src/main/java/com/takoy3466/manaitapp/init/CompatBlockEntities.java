package com.takoy3466.manaitapp.init;

import com.takoy3466.manaitapp.block.abstracts.AbstractBlockManaitaFurnace;
import com.takoy3466.manaitapp.block.blockEntity.ManaitaFurnaceBlockEntity;
import com.takoy3466.manaitapp.block.blockEntity.abstracts.AbstractManaitaFurnaceBlockEntity;
import com.takoy3466.manaitapp.core.registry.holder.CompatDoubleHolder;
import com.takoy3466.manaitapp.core.registry.holder.CompatHolder;
import com.takoy3466.manaitapp.core.registry.CompatRegistry;
import com.takoy3466.manaitapp.core.ManaitaTier;
import com.takoy3466.manaitapp.core.interfaces.ManaitaBlockEntitySupplier;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class CompatBlockEntities {
    
    public static final CompatHolder<BlockEntityType<ManaitaFurnaceBlockEntity.Wood>> MANAITA_FURNACE_WOOD = registerFurnace(ManaitaTiers.WOOD, ManaitaFurnaceBlockEntity.Wood::new, CompatBlocks.MANAITA_FURNACE_WOOD);
    public static final CompatHolder<BlockEntityType<ManaitaFurnaceBlockEntity.Stone>> MANAITA_FURNACE_STONE = registerFurnace(ManaitaTiers.STONE, ManaitaFurnaceBlockEntity.Stone::new, CompatBlocks.MANAITA_FURNACE_STONE);
    public static final CompatHolder<BlockEntityType<ManaitaFurnaceBlockEntity.Iron>> MANAITA_FURNACE_IRON = registerFurnace(ManaitaTiers.IRON, ManaitaFurnaceBlockEntity.Iron::new, CompatBlocks.MANAITA_FURNACE_IRON);
    public static final CompatHolder<BlockEntityType<ManaitaFurnaceBlockEntity.Gold>> MANAITA_FURNACE_GOLD = registerFurnace(ManaitaTiers.GOLD, ManaitaFurnaceBlockEntity.Gold::new, CompatBlocks.MANAITA_FURNACE_GOLD);
    public static final CompatHolder<BlockEntityType<ManaitaFurnaceBlockEntity.Diamond>> MANAITA_FURNACE_DIAMOND = registerFurnace(ManaitaTiers.DIAMOND, ManaitaFurnaceBlockEntity.Diamond::new, CompatBlocks.MANAITA_FURNACE_DIAMOND);
    public static final CompatHolder<BlockEntityType<ManaitaFurnaceBlockEntity.Mtk>> MANAITA_FURNACE_EMERALD = registerFurnace(ManaitaTiers.MTK, ManaitaFurnaceBlockEntity.Mtk::new, CompatBlocks.MANAITA_FURNACE_MTK);

    public static <T extends AbstractManaitaFurnaceBlockEntity, U extends AbstractBlockManaitaFurnace> CompatHolder<BlockEntityType<T>> registerFurnace(ManaitaTier manaitaTier, ManaitaBlockEntitySupplier<T> sup, CompatDoubleHolder.BlockHolder<U> block) {
        String name = "manaita_furnace_" + manaitaTier.getName();
        return CompatRegistry.registerBlockEntityType(name, sup, manaitaTier, block);
    }

    public static void init() {
    }
}
