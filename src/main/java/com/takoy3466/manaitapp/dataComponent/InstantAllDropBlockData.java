package com.takoy3466.manaitapp.dataComponent;

import com.mojang.serialization.Codec;
import com.takoy3466.manaitapp.dataComponent.helper.CodecHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class InstantAllDropBlockData extends AbstractManaitaData<Boolean> {
    public static final StreamCodec<ByteBuf, InstantAllDropBlockData> STREAM_CODEC = CodecHelper.networkCodec(ByteBufCodecs.BOOL, InstantAllDropBlockData::new);
    public static final Codec<InstantAllDropBlockData> CODEC = CodecHelper.oneDataCodec(Codec.BOOL, "multiple", InstantAllDropBlockData::new);
    
    public InstantAllDropBlockData(Boolean tMsg) {
        super(tMsg);
    }

    @Override
    public String toString() {
        return "InstantAllDropBlockData{" +
                "tMsg=" + tMsg +
                '}';
    }
}
