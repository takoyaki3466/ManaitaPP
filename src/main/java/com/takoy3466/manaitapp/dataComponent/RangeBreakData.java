package com.takoy3466.manaitapp.dataComponent;

import com.mojang.serialization.Codec;

import com.takoy3466.manaitapp.core.interfaces.IDataAttachment;
import com.takoy3466.manaitapp.dataComponent.helper.CodecHelper;
import com.takoy3466.manaitapp.init.DataInit;
import com.takoy3466.manaitapp.keyMapping.ManaitaKey;
import com.takoy3466.manaitapp.screen.MTKSwitcherScreen;
import com.takoy3466.manaitapp.util.ToolUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

public class RangeBreakData extends AbstractManaitaData<Integer> implements IDataAttachment {
    public static final StreamCodec<ByteBuf, RangeBreakData> STREAM_CODEC = CodecHelper.networkCodec(ByteBufCodecs.INT, RangeBreakData::new);
    public static final Codec<RangeBreakData> CODEC = CodecHelper.oneDataCodec(Codec.INT, "range", RangeBreakData::new);

    public RangeBreakData(Integer tMsg) {
        super(tMsg);
    }

    @Override
    public boolean onRightClickBlock(@NotNull Level level, @NotNull BlockPos pos, @NotNull Entity interactEntity, InteractionHand hand) {
        if (!(interactEntity instanceof Player player)) {
            return false;
        }
        ItemStack stack = player.getItemInHand(hand);
        RangeBreakData data = stack.get(DataInit.RANGE_BREAK_DATA);
        if (data == null) {
            return false;
        }
        ToolUtil.rangeBreak(level, pos, player, data.getMsg());
        return true;
    }

    @Override
    public boolean onKeyDown(@NotNull Level level, @NotNull Entity interactEntity, int key, int scanCode, int action) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null || minecraft.player == null) {
            return false;
        }
        if (ManaitaKey.MTKSwitcherOpenKey.matches(key, scanCode)) {
            if (action == GLFW.GLFW_PRESS) {
                minecraft.setScreen(new MTKSwitcherScreen());
            } else if (action == GLFW.GLFW_RELEASE) {
                if (minecraft.screen instanceof MTKSwitcherScreen) {
                    minecraft.setScreen(null);
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "RangeBreakData{" +
                "tMsg=" + tMsg +
                '}';
    }
}
