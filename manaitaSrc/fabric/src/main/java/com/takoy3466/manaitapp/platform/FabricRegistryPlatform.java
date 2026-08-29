package com.takoy3466.manaitapp.platform;

import com.mojang.serialization.Codec;
import com.takoy3466.manaitapp.ManaitaPPCommon;
import com.takoy3466.manaitapp.core.Identifier;
import com.takoy3466.manaitapp.core.ManaitaTier;
import com.takoy3466.manaitapp.core.interfaces.ManaitaBlockEntitySupplier;
import com.takoy3466.manaitapp.core.platform.IRegistryPlatform;
import com.takoy3466.manaitapp.core.registry.holder.CompatDoubleHolder;
import com.takoy3466.manaitapp.core.registry.holder.CompatHolder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
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

import java.util.function.Supplier;

public class FabricRegistryPlatform implements IRegistryPlatform {
    @Override
    public <T extends Item> void registerItem(CompatHolder<T> compatHolder, Supplier<T> supplier) {
        Identifier identifier = new Identifier(ManaitaPPCommon.MOD_ID, compatHolder.getId());
        T registered = Registry.register(BuiltInRegistries.ITEM, identifier.get(), supplier.get());
        compatHolder.set(() -> registered);
    }

    @Override
    public <T extends Block> void registerBlock(CompatDoubleHolder.BlockHolder<T> doubleHolder, Supplier<T> supplier, Item.Properties properties) {
        Identifier identifier = new Identifier(ManaitaPPCommon.MOD_ID, doubleHolder.getBlockHolder().getId());
        T registeredBlock = Registry.register(BuiltInRegistries.BLOCK, identifier.get(), supplier.get());
        BlockItem registeredItem = Registry.register(BuiltInRegistries.ITEM, identifier.get(), new BlockItem(registeredBlock, properties));
        doubleHolder.getBlockHolder().set(() -> registeredBlock);
        doubleHolder.getItemHolder().set(() -> registeredItem);
    }

    @Override
    public <T extends BlockEntity> void registerBlockEntityType(CompatHolder<BlockEntityType<T>> compatHolder, ManaitaBlockEntitySupplier<T> supplier, ManaitaTier tier, CompatDoubleHolder.BlockHolder<? extends Block> blockHolder) {
        Identifier identifier = new Identifier(ManaitaPPCommon.MOD_ID, compatHolder.getId());
        BlockEntityType<T> registered = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, identifier.get(), BlockEntityType.Builder.of((pos, state) -> supplier.create(pos, state, tier), blockHolder.getBlock()).build(null));
        compatHolder.set(() -> registered);
    }

    @Override
    public <T> void registerDataComponentType(CompatHolder<DataComponentType<T>> compatHolder, Codec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        Identifier identifier = new Identifier(ManaitaPPCommon.MOD_ID, compatHolder.getId());
        DataComponentType<T> registered = Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, identifier.get(), DataComponentType.<T>builder().persistent(codec).networkSynchronized(streamCodec).build());
        compatHolder.set(() -> registered);
    }

    @Override
    public <T extends Recipe<?>> void registerRecipeSerializer(CompatHolder<RecipeSerializer<T>> compatHolder, Supplier<RecipeSerializer<T>> supplier) {
        Identifier identifier = new Identifier(ManaitaPPCommon.MOD_ID, compatHolder.getId());
        RecipeSerializer<T> registered = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, identifier.get(), supplier.get());
        compatHolder.set(() -> registered);
    }

    @Override
    public <T extends CreativeModeTab> void registerCreativeTab(CompatHolder<T> compatHolder, Supplier<T> supplier) {
        Identifier identifier = new Identifier(ManaitaPPCommon.MOD_ID, compatHolder.getId());
        T registered = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, identifier.get(), supplier.get());
        compatHolder.set(() -> registered);
    }
}
