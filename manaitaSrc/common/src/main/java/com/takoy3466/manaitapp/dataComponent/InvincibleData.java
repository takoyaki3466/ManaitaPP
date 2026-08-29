package com.takoy3466.manaitapp.dataComponent;

import com.mojang.serialization.Codec;
import com.takoy3466.manaitapp.core.interfaces.IDataAttachment;
import com.takoy3466.manaitapp.dataComponent.helper.CodecHelper;
import com.takoy3466.manaitapp.dataComponent.helper.ManaitaDataComponents;
import com.takoy3466.manaitapp.init.CompatData;
import com.takoy3466.manaitapp.keyMapping.ManaitaKey;
import com.takoy3466.manaitapp.util.ArmorUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

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
    public boolean onInvTick(@NotNull Level level, @NotNull Entity tickEntity) {

        if (!(tickEntity instanceof Player player)) {
            return false;
        }
        boolean isInvincible = true;
        boolean hasData = false;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack itemBySlot = player.getItemBySlot(slot);
            InvincibleData data = itemBySlot.get(CompatData.INVINCIBLE_DATA.get());
            if (data != null) {
                isInvincible = data.getMsg();
                hasData = true;
            }
        }

        if (!hasData || !isInvincible) {
            return false;
        }

        return ArmorUtil.invincible(level, tickEntity);
    }

    @Override
    public boolean onKeyDown(@NotNull Level level, @NotNull Entity interactEntity, int key, int scanCode, int action) {
        if (interactEntity instanceof Player player && player.isSteppingCarefully() && ManaitaKey.HelmetKey.consumeClick()) {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                ItemStack itemBySlot = player.getItemBySlot(slot);
                InvincibleData data = itemBySlot.get(CompatData.INVINCIBLE_DATA.get());
                if (data != null) {
                    data.setMsg(!data.getMsg());
                    player.displayClientMessage(Component.literal(ManaitaDataComponents.INVINCIBLE_TEXT.getString() + ": " + ManaitaDataComponents.getCommonTextTF(data.getMsg()).getString()), true);
                    break;
                }
            }
        }
        return true;
    }
}
