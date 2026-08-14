package com.takoy3466.manaitapp.dataComponent;

import com.mojang.serialization.Codec;
import com.takoy3466.manaitapp.core.interfaces.IDataAttachment;
import com.takoy3466.manaitapp.init.AttachmentsInit;
import com.takoy3466.manaitapp.init.DataInit;
import com.takoy3466.manaitapp.util.ArmorUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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

    @Override
    public boolean onLivingEquipmentChange(Level level, Entity entity, ItemStack currentStack, ItemStack previousStack) {
        return equipmentChangeHelper(level, entity, currentStack, previousStack, DataInit.INVINCIBLE_DATA.get(),
                player -> changeData(player, true),
                player -> changeData(player, false)
        );
    }

    private void changeData(Player player, boolean bool) {
        player.setData(AttachmentsInit.INVINCIBLE_ATTACHMENT, new InvincibleData(bool));
    }
}
