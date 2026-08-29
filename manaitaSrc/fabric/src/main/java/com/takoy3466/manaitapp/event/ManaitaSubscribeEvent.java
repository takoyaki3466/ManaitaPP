package com.takoy3466.manaitapp.event;

import com.takoy3466.manaitapp.init.CompatItems;
import com.takoy3466.manaitapp.init.CompatData;
import com.takoy3466.manaitapp.util.ArmorUtil;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.player.*;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ManaitaSubscribeEvent {

    public static void registerEvent() {
        onRightClickBlockEvent();
        onRightClickEvent();
        onLeftClickBlockEvent();
        onAttackEntityEvent();
        onLivingEquipmentChangeEvent();
        onLivingIncomingDamageEvent();
        onBlockDropEvent();
        onManaitaOriginDrop();
    }


    public static void onRightClickBlockEvent() {
        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            if (level.isClientSide()) {
                return InteractionResult.PASS;
            }
            return ManaitaEventHelper.fabricInteractionExecute(player.getItemInHand(hand), dataAttachment -> dataAttachment.onRightClickBlock(level, hitResult.getBlockPos(), hitResult.getDirection(), player, hand));
        });
    }

    public static void onRightClickEvent() {
        UseItemCallback.EVENT.register((player, level, hand) -> {
            if (level.isClientSide()) {
                return InteractionResultHolder.pass(player.getItemInHand(hand));
            }
            return ManaitaEventHelper.fabricHolderExecute(player.getItemInHand(hand), dataAttachment -> dataAttachment.onRightClickItem(level, player.blockPosition(), player, hand));
        });
    }


    public static void onLeftClickBlockEvent() {
        AttackBlockCallback.EVENT.register((player, level, hand, pos, direction) -> {
            if (level.isClientSide()) {
                return InteractionResult.PASS;
            }
            return ManaitaEventHelper.fabricInteractionExecute(player.getItemInHand(hand), dataAttachment -> dataAttachment.onLeftClickBlock(level, pos, player, hand));
        });
    }

    /*
    public static void onPlayerTickEvent() {
        // Mixinに移動
    }
    */
    
    public static void onAttackEntityEvent() {
        AttackEntityCallback.EVENT.register((player, level, hand, target, hitResult) -> {
            if (level.isClientSide()) {
                return InteractionResult.PASS;
            }
            return ManaitaEventHelper.fabricInteractionExecute(player.getMainHandItem(), dataAttachment -> dataAttachment.onAttackEntity(player.level(), player, target));

        });
    }

    
    public static void onLivingEquipmentChangeEvent() {
        ServerEntityEvents.EQUIPMENT_CHANGE.register((livingEntity, slot, previous, current) -> {
            Level level = livingEntity.level();
            ManaitaEventHelper.attachmentExecute(livingEntity.getItemBySlot(slot), dataAttachment -> dataAttachment.onLivingEquipmentChange(level, livingEntity, previous, current));
        });
    }

    /*
    public static void onLivingDeathEvent() {
        // onLivingIncomingDamageEventと同じなため省略
    }
    */
    
    public static void onLivingIncomingDamageEvent() {
        ServerLivingEntityEvents.ALLOW_DAMAGE.register((livingEntity, damageSource, v) ->
                ArmorUtil.playerEquipDataItem(livingEntity, CompatData.INVINCIBLE_DATA.get()));
    }

    /*
    public static void onLivingKnockBackEvent() {
        // Mixinに移動
    }
    */
    
    public static void onBlockDropEvent() {
        PlayerBlockBreakEvents.AFTER.register((level, player, pos, state, blockEntity) ->
                blockDrop(state, pos, level, player, Blocks.BEDROCK, Items.BEDROCK));
        
    }

    public static void blockDrop(BlockState state, BlockPos pos, Level level, Player player, Block block, Item item) {
        if (state.is(block)) {
            if (player.getMainHandItem().get(CompatData.INSTANT_BLOCK_DATA.get()) != null) {
                new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(item));
            }
        }
    }
    
    
    public static void onManaitaOriginDrop() {
        PlayerBlockBreakEvents.AFTER.register((level, player, pos, state, blockEntity) -> {
            if (state.is(BlockTags.LOGS) && player.getMainHandItem().isEmpty()) {
                if (level.getRandom().nextFloat() >= 0.8F) {
                    return;
                }
                new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(CompatItems.MANAITA_ORIGIN.get()));
            }
        });
        
    }

}
