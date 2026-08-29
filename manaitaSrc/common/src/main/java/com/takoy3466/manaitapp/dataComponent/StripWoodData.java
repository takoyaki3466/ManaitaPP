package com.takoy3466.manaitapp.dataComponent;

import com.mojang.serialization.Codec;
import com.takoy3466.manaitapp.core.interfaces.IDataAttachment;
import com.takoy3466.manaitapp.dataComponent.helper.CodecHelper;
import com.takoy3466.manaitapp.util.ToolUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class StripWoodData extends AbstractManaitaData<Boolean> implements IDataAttachment {
    public static final StreamCodec<ByteBuf, StripWoodData> STREAM_CODEC = CodecHelper.networkCodec(ByteBufCodecs.BOOL, StripWoodData::new);
    public static final Codec<StripWoodData> CODEC = CodecHelper.oneDataCodec(Codec.BOOL, "not_use", StripWoodData::new);
    
    public StripWoodData(Boolean tMsg) {
        super(tMsg);
    }

    @Override
    public boolean onRightClickBlock(@NotNull Level level, @NotNull BlockPos pos, @NotNull Direction face, @NotNull Entity interactEntity, InteractionHand hand) {
        if (!(interactEntity instanceof Player player)) {
            return false;
        }
        if (player.isSteppingCarefully()) {
            return false;
        }
        ToolUtil.stripWood(level, pos, player, hand);
        return true;
    }

    @Override
    public String toString() {
        return "StripWoodData{" +
                "tMsg=" + tMsg +
                '}';
    }
}
