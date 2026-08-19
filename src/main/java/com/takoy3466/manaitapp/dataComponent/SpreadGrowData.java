package com.takoy3466.manaitapp.dataComponent;

import com.mojang.serialization.Codec;
import com.takoy3466.manaitapp.core.interfaces.IDataAttachment;
import com.takoy3466.manaitapp.dataComponent.helper.CodecHelper;
import com.takoy3466.manaitapp.init.DataInit;
import com.takoy3466.manaitapp.util.ToolUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class SpreadGrowData extends AbstractManaitaData<Integer> implements IDataAttachment {
    public static final StreamCodec<ByteBuf, SpreadGrowData> STREAM_CODEC = CodecHelper.networkCodec(ByteBufCodecs.INT, SpreadGrowData::new);
    public static final Codec<SpreadGrowData> CODEC = CodecHelper.oneDataCodec(Codec.INT, "range", SpreadGrowData::new);

    public SpreadGrowData(Integer tMsg) {
        super(tMsg);
    }

    @Override
    public boolean onRightClickBlock(@NotNull Level level, @NotNull BlockPos pos, @NotNull Entity interactEntity, InteractionHand hand) {
        if (!(interactEntity instanceof Player player)) {
            return false;
        }
        if (level.isClientSide()) {
            return false;
        }
        ItemStack stack = player.getItemInHand(hand);
        SpreadGrowData data = stack.get(DataInit.SPREAD_GROW_DATA);
        if (data == null) {
            return false;
        }
        if (player.isSteppingCarefully()) {
            ToolUtil.spreadGrow(level, pos, data.getMsg());
        }
        return false;

    }

    @Override
    public String toString() {
        return "SpreadGrowData{" +
                "tMsg=" + tMsg +
                '}';
    }
}
