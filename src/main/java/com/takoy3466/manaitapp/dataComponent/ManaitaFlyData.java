package com.takoy3466.manaitapp.dataComponent;

import com.mojang.serialization.Codec;
import com.takoy3466.manaitapp.core.interfaces.IDataAttachment;
import com.takoy3466.manaitapp.dataComponent.helper.CodecHelper;
import com.takoy3466.manaitapp.init.DataInit;
import com.takoy3466.manaitapp.keyMapping.ManaitaKey;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ManaitaFlyData extends AbstractManaitaData<ManaitaFlyData.FlySpeed> implements IDataAttachment {
    public static final StreamCodec<ByteBuf, ManaitaFlyData> STREAM_CODEC = CodecHelper.networkCodec(FlySpeed.STREAM_CODEC, ManaitaFlyData::new);
    public static final Codec<ManaitaFlyData> CODEC = CodecHelper.oneDataCodec(FlySpeed.CODEC, "fly_speed", ManaitaFlyData::new);

    public ManaitaFlyData(FlySpeed tMsg) {
        super(tMsg);
    }


    @Override
    public boolean onKeyDown(@NotNull Level level, @NotNull Entity interactEntity, int key, int scanCode, int action) {
        if (!(interactEntity instanceof Player player)) {
            return false;
        }
        if (level.isClientSide()) {
            return false;
        }
        if (ManaitaKey.FlySpeedKey.matches(key, scanCode)) {
            ItemStack stack = player.getItemBySlot(EquipmentSlot.HEAD);
            ManaitaFlyData data = stack.get(DataInit.FLY_DATA);
            if (data != null) {
                data.setMsg(data.getMsg().getNext());
                player.displayClientMessage(Component.literal("MODE: " + data.getMsg().getFlySpeed()), true);
            }
        }
        return true;
    }

    /**
     * mayFlyが非推奨になっているので今後変更する可能性があります。
     */
    @SuppressWarnings("deprecation")
    @Override
    public boolean onLivingEquipmentChange(@NotNull Level level, @NotNull Entity interactEntity, ItemStack currentStack, ItemStack previousStack) {
        return equipmentChangeHelper(level, interactEntity, currentStack, previousStack, DataInit.FLY_DATA.get(),
                player -> {
                    // データのないアイテムからデータのあるアイテムに代わったとき

                    // ↓ 補足
                    // player.getAbilities().flying <- クリエなどで浮いているときの値
                    // player.isFallFlying() <- エリトラなどで滑空しているときの設定
                    player.getAbilities().mayfly = true;
                    player.onUpdateAbilities();
                    },
                player -> {
                    // データのあるアイテムからデータがないアイテムに変わったとき
                    player.getAbilities().mayfly = false;
                    player.getAbilities().flying = false;
                    player.onUpdateAbilities();
                    if (player instanceof ServerPlayer serverPlayer) {
                        serverPlayer.connection.teleport(serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(),
                                serverPlayer.getYRot(), serverPlayer.getXRot());
                    }
        }
        );
    }

    @Override
    public boolean onInvTick(@NotNull Level level, @NotNull Entity tickEntity) {
        if (!(tickEntity instanceof Player player)) {
            return false;
        }
        if (level.isClientSide()) {
            return false;
        }
        ItemStack stack = player.getItemBySlot(EquipmentSlot.HEAD);
        ManaitaFlyData data = stack.get(DataInit.FLY_DATA);
        if (data == null) {
            return false;
        }
        player.getAbilities().setFlyingSpeed(data.getMsg().getFlySpeed());
        return true;
    }

    public enum FlySpeed implements StringRepresentable {
        DEFAULT(0.05f),
        LOW(0.1f),
        MEDIUM(0.2f),
        HIGH(0.4f),
        EXTREME(0.6f),
        OMG(1.0f);

        public static final Codec<FlySpeed> CODEC = StringRepresentable.fromEnum(FlySpeed::values);
        public static final StreamCodec<ByteBuf, FlySpeed> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);

        private final float flySpeed;

        public FlySpeed getNext() {
            return switch (this) {
                case DEFAULT -> LOW;
                case LOW -> MEDIUM;
                case MEDIUM -> HIGH;
                case HIGH -> EXTREME;
                case EXTREME -> OMG;
                case OMG -> DEFAULT;
            };
        }

        FlySpeed(float flySpeed) {
            this.flySpeed = flySpeed;
        }

        public float getFlySpeed() {
            return flySpeed;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name();
        }
    }

    @Override
    public String toString() {
        return "ManaitaFlyData{" +
                "tMsg=" + tMsg +
                '}';
    }
}
