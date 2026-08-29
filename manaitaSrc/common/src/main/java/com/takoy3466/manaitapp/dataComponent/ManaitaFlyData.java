package com.takoy3466.manaitapp.dataComponent;

import com.mojang.serialization.Codec;
import com.takoy3466.manaitapp.core.interfaces.IDataAttachment;
import com.takoy3466.manaitapp.dataComponent.helper.CodecHelper;
import com.takoy3466.manaitapp.dataComponent.helper.ManaitaDataComponents;
import com.takoy3466.manaitapp.init.CompatData;
import com.takoy3466.manaitapp.keyMapping.ManaitaKey;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
        if (ManaitaKey.FlySpeedKey.consumeClick()) {
            ItemStack stack = player.getItemBySlot(EquipmentSlot.HEAD);
            ManaitaFlyData data = stack.get(CompatData.FLY_DATA.get());
            if (data != null) {
                data.setMsg(data.getMsg().getNext());
                player.displayClientMessage(Component.literal(ManaitaDataComponents.FLY_TEXT.getString() + ": " + data.getMsg().getComponent().getString()), true);
            }
        }
        return true;
    }

    @Override
    public boolean onLivingEquipmentChange(@NotNull Level level, @NotNull Entity interactEntity, ItemStack current, ItemStack previous) {
        if (interactEntity instanceof ServerPlayer player) {
            if (player.isCreative() || player.isSpectator()) {
                return false;
            }
            ManaitaFlyData previousData = previous.get(CompatData.FLY_DATA.get());
            ManaitaFlyData currentData = current.get(CompatData.FLY_DATA.get());

            if (previousData != null) {
                if (currentData == null) {
                    player.getAbilities().mayfly = false;
                    player.getAbilities().flying = false;
                    player.onUpdateAbilities();

                    if (player instanceof ServerPlayer serverPlayer) {
                        serverPlayer.connection.teleport(serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), serverPlayer.getYRot(), serverPlayer.getXRot());
                    }
                }
            }else if (currentData != null) {
                player.getAbilities().mayfly = true;
                player.getAbilities().flying = true;
                player.onUpdateAbilities();
            }
        }

        return false;
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
        ManaitaFlyData data = stack.get(CompatData.FLY_DATA.get());
        if (data == null) {
            return false;
        }
        player.getAbilities().setFlyingSpeed(data.getMsg().getFlySpeed());
        player.onUpdateAbilities();
        return false;
    }

    public enum FlySpeed implements StringRepresentable {
        DEFAULT(0.05f, Component.translatable("misc.manaitapp.fly_speed.default")),
        LOW(0.1f, Component.translatable("misc.manaitapp.fly_speed.low")),
        MEDIUM(0.2f, Component.translatable("misc.manaitapp.fly_speed.medium")),
        HIGH(0.4f, Component.translatable("misc.manaitapp.fly_speed.high")),
        EXTREME(0.6f, Component.translatable("misc.manaitapp.fly_speed.extream")),
        OMG(1.0f, Component.translatable("misc.manaitapp.fly_speed.omg"));

        public static final Codec<FlySpeed> CODEC = StringRepresentable.fromEnum(FlySpeed::values);
        public static final StreamCodec<ByteBuf, FlySpeed> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);

        private final float flySpeed;
        private final Component component;

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

        FlySpeed(float flySpeed, Component component) {
            this.flySpeed = flySpeed;
            this.component = component;
        }

        public float getFlySpeed() {
            return flySpeed;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name();
        }

        public Component getComponent() {
            return component;
        }
    }

    @Override
    public String toString() {
        return "ManaitaFlyData{" +
                "tMsg=" + tMsg +
                '}';
    }
}
