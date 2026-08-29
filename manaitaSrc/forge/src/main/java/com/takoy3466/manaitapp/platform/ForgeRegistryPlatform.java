package com.takoy3466.manaitapp.platform;

import com.mojang.serialization.Codec;
import com.takoy3466.manaitapp.ManaitaPPCommon;
import com.takoy3466.manaitapp.core.ManaitaTier;
import com.takoy3466.manaitapp.core.interfaces.ManaitaBlockEntitySupplier;
import com.takoy3466.manaitapp.core.platform.IRegistryPlatform;
import com.takoy3466.manaitapp.core.registry.holder.CompatDoubleHolder;
import com.takoy3466.manaitapp.core.registry.holder.CompatHolder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ForgeRegistryPlatform implements IRegistryPlatform {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ManaitaPPCommon.MOD_ID);
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ManaitaPPCommon.MOD_ID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ManaitaPPCommon.MOD_ID);
    private static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, ManaitaPPCommon.MOD_ID);
    private static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, ManaitaPPCommon.MOD_ID);
    private static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ManaitaPPCommon.MOD_ID);


    public static void register(IEventBus bus) {
        DATA_COMPONENTS.register(bus);
        BLOCKS.register(bus);
        ITEMS.register(bus);
        BLOCK_ENTITIES.register(bus);
        SERIALIZERS.register(bus);
        TABS.register(bus);
    }

    @Override
    public <T extends Item> void registerItem(CompatHolder<T> compatHolder, Supplier<T> supplier) {
        RegistryObject<T> deferredItem = ITEMS.register(compatHolder.getId(), supplier);
        compatHolder.set(deferredItem);
    }

    @Override
    public <T extends Block> void registerBlock(CompatDoubleHolder.BlockHolder<T> doubleHolder, Supplier<T> supplier, Item.Properties properties) {
        RegistryObject<T> deferredBlock = BLOCKS.register(doubleHolder.getBlockHolder().getId(), supplier);
        RegistryObject<BlockItem> deferredItem = ITEMS.register(doubleHolder.getItemHolder().getId(), () -> new BlockItem(deferredBlock.get(), properties));
        doubleHolder.getBlockHolder().set(deferredBlock);
        doubleHolder.getItemHolder().set(deferredItem);
    }

    @Override
    public <T extends BlockEntity> void registerBlockEntityType(CompatHolder<BlockEntityType<T>> compatHolder, ManaitaBlockEntitySupplier<T> supplier, ManaitaTier tier, CompatDoubleHolder.BlockHolder<? extends Block> blockHolder) {
        RegistryObject<BlockEntityType<T>> deferredHolder = BLOCK_ENTITIES.register(compatHolder.getId(), () -> BlockEntityType.Builder.of((pos, state) -> supplier.create(pos, state, tier), blockHolder.getBlock()).build(null));
        compatHolder.set(deferredHolder);
    }

    @Override
    public <T> void registerDataComponentType(CompatHolder<DataComponentType<T>> compatHolder, Codec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        RegistryObject<DataComponentType<T>> deferredHolder = DATA_COMPONENTS.register(compatHolder.getId(), () -> DataComponentType.<T>builder().persistent(codec).networkSynchronized(streamCodec).build());
        compatHolder.set(deferredHolder);
    }

    @Override
    public <T extends Recipe<?>> void registerRecipeSerializer(CompatHolder<RecipeSerializer<T>> compatHolder, Supplier<RecipeSerializer<T>> supplier) {
        RegistryObject<RecipeSerializer<T>> deferredHolder = SERIALIZERS.register(compatHolder.getId(), supplier);
        compatHolder.set(deferredHolder);
    }

    @Override
    public <T extends CreativeModeTab> void registerCreativeTab(CompatHolder<T> compatHolder, Supplier<T> supplier) {
        RegistryObject<T> deferredHolder = TABS.register(compatHolder.getId(), supplier);
        compatHolder.set(deferredHolder);
    }
}
