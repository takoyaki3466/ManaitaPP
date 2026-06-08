package com.takoy3466.manaitapp.dataComponent;

import com.mojang.serialization.Codec;
import com.takoy3466.manaitapp.core.interfaces.IDataAttachment;
import com.takoy3466.manaitapp.util.ArmorUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class InvincibleData extends AbstractManaitaData<Boolean> implements IDataAttachment {

    public static final StreamCodec<ByteBuf, InvincibleData> STREAM_CODEC = CodecHelper.networkCodec(ByteBufCodecs.BOOL, InvincibleData::new);
    public static final Codec<InvincibleData> CODEC = CodecHelper.oneDataCodec(Codec.BOOL, "is_invincible", InvincibleData::new);

    public InvincibleData(boolean tMsg) {
        super(tMsg);
    }

    @Override
    public String toString() {
        return "InvincibleData{" +
                "tMsg=" + tMsg +
                '}';
    }

    @Override
    public boolean onInvTick(Level level, Entity entity) {
        return ArmorUtil.invincible(level, entity);
    }
}
