package com.takoy3466.manaitapp.dataComponent;

import com.mojang.serialization.Codec;
import com.takoy3466.manaitapp.core.interfaces.IDataAttachment;
import com.takoy3466.manaitapp.dataComponent.helper.CodecHelper;
import com.takoy3466.manaitapp.util.WeaponUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ManaitaKillData extends AbstractManaitaData<Boolean> implements IDataAttachment {
    public static final StreamCodec<ByteBuf, ManaitaKillData> STREAM_CODEC = CodecHelper.networkCodec(ByteBufCodecs.BOOL, ManaitaKillData::new);
    public static final Codec<ManaitaKillData> CODEC = CodecHelper.oneDataCodec(Codec.BOOL, "is_manaita_kill", ManaitaKillData::new);
    
    public ManaitaKillData(Boolean tMsg) {
        super(tMsg);
    }

    @Override
    public boolean onAttackEntity(@NotNull Level level, @NotNull Player player, @NotNull Entity target) {
        if (level.isClientSide()) {
            return false;
        }
        if (target instanceof LivingEntity livingEntity) {
            WeaponUtil.kill(livingEntity, player);
        }
        return false;
    }

    @Override
    public String toString() {
        return "ManaitaKillData{" +
                "tMsg=" + tMsg +
                '}';
    }
}
