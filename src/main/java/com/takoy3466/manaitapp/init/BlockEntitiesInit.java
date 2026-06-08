package com.takoy3466.manaitapp.init;

import com.takoy3466.manaitapp.Manaitapp;
import com.takoy3466.manaitapp.block.abstracts.AbstractBlockManaitaFurnace;
import com.takoy3466.manaitapp.block.blockEntity.ManaitaFurnaceBlockEntity;
import com.takoy3466.manaitapp.block.blockEntity.abstracts.AbstractManaitaFurnaceBlockEntity;
import com.takoy3466.manaitapp.core.ManaitaTier;
import com.takoy3466.manaitapp.core.interfaces.ManaitaBlockEntitySupplier;
import com.takoy3466.manaitapp.core.registry.holder.DoubleHolder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockEntitiesInit {
    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Manaitapp.MOD_ID);

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<ManaitaFurnaceBlockEntity.Wood>> MANAITA_FURNACE_WOOD = registerFurnace(ManaitaTiers.WOOD, ManaitaFurnaceBlockEntity.Wood::new, BlocksInit.MANAITA_FURNACE_WOOD);
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<ManaitaFurnaceBlockEntity.Stone>> MANAITA_FURNACE_STONE = registerFurnace(ManaitaTiers.STONE, ManaitaFurnaceBlockEntity.Stone::new, BlocksInit.MANAITA_FURNACE_STONE);
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<ManaitaFurnaceBlockEntity.Iron>> MANAITA_FURNACE_IRON = registerFurnace(ManaitaTiers.IRON, ManaitaFurnaceBlockEntity.Iron::new, BlocksInit.MANAITA_FURNACE_IRON);
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<ManaitaFurnaceBlockEntity.Gold>> MANAITA_FURNACE_GOLD = registerFurnace(ManaitaTiers.GOLD, ManaitaFurnaceBlockEntity.Gold::new, BlocksInit.MANAITA_FURNACE_GOLD);
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<ManaitaFurnaceBlockEntity.Diamond>> MANAITA_FURNACE_DIAMOND = registerFurnace(ManaitaTiers.DIAMOND, ManaitaFurnaceBlockEntity.Diamond::new, BlocksInit.MANAITA_FURNACE_DIAMOND);
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<ManaitaFurnaceBlockEntity.Mtk>> MANAITA_FURNACE_EMERALD = registerFurnace(ManaitaTiers.MTK, ManaitaFurnaceBlockEntity.Mtk::new, BlocksInit.MANAITA_FURNACE_MTK);

    public static <T extends AbstractManaitaFurnaceBlockEntity, U extends AbstractBlockManaitaFurnace> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> registerFurnace(ManaitaTier manaitaTier, ManaitaBlockEntitySupplier<T> sup, DoubleHolder.BlockHolder<U> block) {
        String name = "manaita_furnace_" + manaitaTier.getName();
        return BLOCK_ENTITIES.register(name, () -> BlockEntityType.Builder.of((p, s) -> sup.create(p, s, manaitaTier), block.getBlock()).build(null));
    }
}
