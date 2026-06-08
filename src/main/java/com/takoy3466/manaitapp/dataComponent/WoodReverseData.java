package com.takoy3466.manaitapp.dataComponent;

import com.mojang.serialization.Codec;
import com.takoy3466.manaitapp.core.interfaces.IDataAttachment;
import com.takoy3466.manaitapp.util.ToolUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class WoodReverseData extends AbstractManaitaData<Boolean> implements IDataAttachment {
    public static final StreamCodec<ByteBuf, WoodReverseData> STREAM_CODEC = CodecHelper.networkCodec(ByteBufCodecs.BOOL, WoodReverseData::new);
    public static final Codec<WoodReverseData> CODEC = CodecHelper.oneDataCodec(Codec.BOOL, "is_wood_reverse", WoodReverseData::new);

    public WoodReverseData(Boolean tMsg) {
        super(tMsg);
    }

    @Override
    public boolean onRightClickBlock(Level level, BlockPos pos, Entity entity, InteractionHand hand) {
        System.out.println("onRightClick was called!");
        if (level == null || entity == null) {
            return false;
        }
        if (level.isClientSide()|| !(entity instanceof Player player)) {
            return false;
        }
        if (!player.isSteppingCarefully()) {
            return false;
        }
        ToolUtil.woodReverse(level, pos, player, player.getItemInHand(hand), hand);
        return true;
    }

    @Override
    public String toString() {
        return "WoodReverseData{" +
                "tMsg=" + tMsg +
                '}';
    }
}
