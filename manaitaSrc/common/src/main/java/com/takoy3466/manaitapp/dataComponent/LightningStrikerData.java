package com.takoy3466.manaitapp.dataComponent;

import com.mojang.serialization.Codec;
import com.takoy3466.manaitapp.core.interfaces.IDataAttachment;
import com.takoy3466.manaitapp.dataComponent.helper.CodecHelper;
import com.takoy3466.manaitapp.dataComponent.helper.ManaitaDataComponents;
import com.takoy3466.manaitapp.init.CompatData;
import com.takoy3466.manaitapp.keyMapping.ManaitaKey;
import com.takoy3466.manaitapp.util.WeaponUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LightningStrikerData extends AbstractManaitaData<Boolean> implements IDataAttachment {
    public static final StreamCodec<ByteBuf, LightningStrikerData> STREAM_CODEC = CodecHelper.networkCodec(ByteBufCodecs.BOOL, LightningStrikerData::new);
    public static final Codec<LightningStrikerData> CODEC = CodecHelper.oneDataCodec(Codec.BOOL, "is_kill_all", LightningStrikerData::new);

    public LightningStrikerData(Boolean tMsg) {
        super(tMsg);
    }

    @Override
    public String toString() {
        return "LightningStrikerData{" +
                "tMsg=" + tMsg +
                '}';
    }
    public static double RADIUS = 50;
    @Override
    public boolean onRightClickItem(@NotNull Level level, @NotNull BlockPos pos, @NotNull Entity interactEntity, InteractionHand hand) {
        if (level.isClientSide() || !(interactEntity instanceof Player player)) {
            return false;
        }
        LightningStrikerData data = player.getItemInHand(hand).get(CompatData.STRIKER_DATA.get());
        if (data == null) {
            return false;
        }

        List<LivingEntity> targets = WeaponUtil.selectTargets(LivingEntity.class, level, player, RADIUS, data.getMsg() ? WeaponUtil.ALL_LIVING : WeaponUtil.ONLY_ENEMY);
        WeaponUtil.lightningStriker(targets, level, player);
        return true;

    }

    @Override
    public boolean onKeyDown(@NotNull Level level, @NotNull Entity interactEntity, int key, int scanCode, int action) {
        if (!(interactEntity instanceof Player player)) {
            return false;
        }
        if (player.isSteppingCarefully() && ManaitaKey.SwitchExterminationKey.consumeClick()) {
            ItemStack stack = player.getMainHandItem();
            LightningStrikerData data = stack.get(CompatData.STRIKER_DATA.get());
            if (data != null) {
                data.setMsg(!data.getMsg());
                player.displayClientMessage(Component.literal(ManaitaDataComponents.STRIKER_TEXT.getString() + ": " + (data.getMsg() ? ManaitaDataComponents.IS_KILL_ALL.getString() : ManaitaDataComponents.ONLY_ENEMY.getString())), true);
            }
        }
        return true;
    }
}
