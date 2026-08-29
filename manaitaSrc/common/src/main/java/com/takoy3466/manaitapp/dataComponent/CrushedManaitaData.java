package com.takoy3466.manaitapp.dataComponent;

import com.mojang.serialization.Codec;
import com.takoy3466.manaitapp.dataComponent.helper.CodecHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class CrushedManaitaData extends AbstractManaitaData<Integer> {

    public static final StreamCodec<ByteBuf, CrushedManaitaData> STREAM_CODEC = CodecHelper.networkCodec(ByteBufCodecs.INT, CrushedManaitaData::new);
    public static final Codec<CrushedManaitaData> CODEC = CodecHelper.oneDataCodec(Codec.INT, "multiple", CrushedManaitaData::new);

    public CrushedManaitaData(int tMsg) {
        super(tMsg);
    }

    @Override
    public String toString() {
        return "CrushedManaitaData{" +
                "tMsg=" + tMsg +
                '}';
    }
}