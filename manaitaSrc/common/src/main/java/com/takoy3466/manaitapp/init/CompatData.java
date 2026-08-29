package com.takoy3466.manaitapp.init;

import com.takoy3466.manaitapp.core.registry.holder.CompatHolder;
import com.takoy3466.manaitapp.core.registry.CompatRegistry;
import com.takoy3466.manaitapp.dataComponent.*;
import net.minecraft.core.component.DataComponentType;

public class CompatData {
    
    public static final CompatHolder<DataComponentType<WoodReverseData>> WOOD_REVERSE_DATA = CompatRegistry.registerDataComponentType("wood_reverse", WoodReverseData.CODEC, WoodReverseData.STREAM_CODEC);
    public static final CompatHolder<DataComponentType<RangeBreakData>> RANGE_BREAK_DATA = CompatRegistry.registerDataComponentType("range_break", RangeBreakData.CODEC, RangeBreakData.STREAM_CODEC);
    public static final CompatHolder<DataComponentType<CrushedManaitaData>> CRUSHED_DATA = CompatRegistry.registerDataComponentType("crushed_data", CrushedManaitaData.CODEC, CrushedManaitaData.STREAM_CODEC);
    public static final CompatHolder<DataComponentType<LightningStrikerData>> STRIKER_DATA = CompatRegistry.registerDataComponentType("striker_data", LightningStrikerData.CODEC, LightningStrikerData.STREAM_CODEC);
    public static final CompatHolder<DataComponentType<InvincibleData>> INVINCIBLE_DATA = CompatRegistry.registerDataComponentType("invincible_data", InvincibleData.CODEC, InvincibleData.STREAM_CODEC);
    public static final CompatHolder<DataComponentType<SpreadGrowData>> SPREAD_GROW_DATA = CompatRegistry.registerDataComponentType("spread_grow_data", SpreadGrowData.CODEC, SpreadGrowData.STREAM_CODEC);
    public static final CompatHolder<DataComponentType<ManaitaFlyData>> FLY_DATA = CompatRegistry.registerDataComponentType("manaita_fly_data", ManaitaFlyData.CODEC, ManaitaFlyData.STREAM_CODEC);
    public static final CompatHolder<DataComponentType<ManaitaKillData>> KILL_DATA = CompatRegistry.registerDataComponentType("manaita_kill_data", ManaitaKillData.CODEC, ManaitaKillData.STREAM_CODEC);
    public static final CompatHolder<DataComponentType<InstantAllDropBlockData>> INSTANT_BLOCK_DATA = CompatRegistry.registerDataComponentType("instant_all_drop_block_data", InstantAllDropBlockData.CODEC, InstantAllDropBlockData.STREAM_CODEC);
    public static final CompatHolder<DataComponentType<StripWoodData>> STRIP_WOOD_DATA = CompatRegistry.registerDataComponentType("strip_wood_data", StripWoodData.CODEC, StripWoodData.STREAM_CODEC);
    public static final CompatHolder<DataComponentType<TillSoilData>> TILL_SOIL_DATA = CompatRegistry.registerDataComponentType("till_soil_data", TillSoilData.CODEC, TillSoilData.STREAM_CODEC);

    public static void init() {
    }
}
