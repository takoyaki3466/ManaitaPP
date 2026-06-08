package com.takoy3466.manaitapp.dataComponent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.takoy3466.manaitapp.core.interfaces.IMsg;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.function.Function;

public class CodecHelper {

    public static <C, T extends IMsg<C>> StreamCodec<ByteBuf, T> networkCodec(StreamCodec<ByteBuf, C> codec, Function<C, T> instance) {
        return StreamCodec.composite(codec, IMsg::getMsg, instance);
    }

    public static <T extends IMsg<D>, D> Codec<T> oneDataCodec(PrimitiveCodec<D> primitive, String fieldName, Function<D, T> func) {
        return RecordCodecBuilder.create(instance ->
                instance.group(primitive.fieldOf(fieldName).forGetter(IMsg::getMsg)).apply(instance, func)
        );
    }

    public static <T extends IMsg<D>, D> Codec<T> oneDataCodec(Codec<D> codec, String fieldName, Function<D, T> func) {
        return RecordCodecBuilder.create(instance ->
                instance.group(codec.fieldOf(fieldName).forGetter(IMsg::getMsg)).apply(instance, func)
        );
    }

}
