package com.takoy3466.manaitapp.init;

import com.mojang.serialization.Codec;
import com.takoy3466.manaitapp.Manaitapp;
import com.takoy3466.manaitapp.dataComponent.*;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DataInit {

    public static DeferredRegister.DataComponents DATA_COMPONENT = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Manaitapp.MOD_ID);

    public static DeferredHolder<DataComponentType<?>, DataComponentType<WoodReverseData>> WOOD_REVERSE = registerData("wood_reverse", WoodReverseData.CODEC, WoodReverseData.STREAM_CODEC);
    public static DeferredHolder<DataComponentType<?>, DataComponentType<RangeBreakData>> RANGE_BREAK = registerData("range_break", RangeBreakData.CODEC, RangeBreakData.STREAM_CODEC);
    public static DeferredHolder<DataComponentType<?>, DataComponentType<CrushedManaitaData>> CRUSHED_DATA = registerData("crushed_data", CrushedManaitaData.CODEC, CrushedManaitaData.STREAM_CODEC);
    public static DeferredHolder<DataComponentType<?>, DataComponentType<LightningStrikerData>> STRIKER_DATA = registerData("striker_data", LightningStrikerData.CODEC, LightningStrikerData.STREAM_CODEC);
    public static DeferredHolder<DataComponentType<?>, DataComponentType<InvincibleData>> INVINCIBLE_DATA = registerData("invincible_data", InvincibleData.CODEC, InvincibleData.STREAM_CODEC);
    public static DeferredHolder<DataComponentType<?>, DataComponentType<SpreadGrowData>> SPREAD_GROW_DATA = registerData("spread_grow_data", SpreadGrowData.CODEC, SpreadGrowData.STREAM_CODEC);
    public static DeferredHolder<DataComponentType<?>, DataComponentType<ManaitaFlyData>> FLY_DATA = registerData("manaita_fly_data", ManaitaFlyData.CODEC, ManaitaFlyData.STREAM_CODEC);

    public static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> registerData(String name, Codec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        return DATA_COMPONENT.register(name,
                () -> DataComponentType.<T>builder()
                        .persistent(codec).networkSynchronized(streamCodec).build()
        );
    }
}
