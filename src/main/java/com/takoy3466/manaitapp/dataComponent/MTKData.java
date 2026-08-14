package com.takoy3466.manaitapp.dataComponent;

import com.mojang.serialization.Codec;
import com.takoy3466.manaitapp.core.interfaces.IDataAttachment;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.Collections;
import java.util.List;

/**
 * Stringはcomponentの文字列として使用しています。
 */
public class MTKData extends AbstractManaitaData<String> implements IDataAttachment {
    public static final StreamCodec<ByteBuf, MTKData> STREAM_CODEC = CodecHelper.networkCodec(ByteBufCodecs.STRING_UTF8, MTKData::new);
    public static final Codec<MTKData> CODEC = CodecHelper.oneDataCodec(Codec.STRING, "hover_text", MTKData::new);

    public MTKData(String tMsg) {
        super(tMsg);
    }

    @Override
    public boolean onRightClickItem(Level level, BlockPos pos, Entity entity, InteractionHand hand) {
        return IDataAttachment.super.onRightClickItem(level, pos, entity, hand);
    }

    @Override
    public boolean onKeyDown(Level level, Entity entity, int key, int scanCode, int action) {
        return IDataAttachment.super.onKeyDown(level, entity, key, scanCode, action);
    }

    @Override
    public boolean onAttackEntity(Level level, Player player, Entity target) {
        if (level == null || player == null || target == null || level.isClientSide()) {
            return false;
        }
        List<ItemStack> stackList = List.of(ItemStack.EMPTY);
        if (target instanceof LivingEntity livingEntity) {
            stackList = getEntityDrops(livingEntity, level.damageSources().generic());
        }
        stackList.forEach(player::addItem);
        return false;
    }

    /**
     * 指定されたエンティティが倒されたときにドロップするアイテムのリストを取得する。
     */
    protected List<ItemStack> getEntityDrops(LivingEntity entity, DamageSource damageSource) {
        if (!(entity.level() instanceof ServerLevel serverLevel)) {
            return Collections.emptyList();
        }

        ResourceKey<LootTable> lootTableKey = entity.getLootTable();
        LootTable lootTable = serverLevel.getServer().reloadableRegistries().getLootTable(lootTableKey);

        LootParams lootParams = new LootParams.Builder(serverLevel)
                .withParameter(LootContextParams.THIS_ENTITY, entity)
                .withParameter(LootContextParams.ORIGIN, entity.position())
                .withParameter(LootContextParams.DAMAGE_SOURCE, damageSource)
                .withOptionalParameter(LootContextParams.ATTACKING_ENTITY, damageSource.getEntity())
                .create(LootContextParamSets.ENTITY);

        return lootTable.getRandomItems(lootParams);
    }
}
