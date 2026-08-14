package com.takoy3466.manaitapp.event;

import com.takoy3466.manaitapp.Manaitapp;
import com.takoy3466.manaitapp.init.AttachmentsInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = Manaitapp.MOD_ID)
public class ManaitaSubscribeEvent {

    @SubscribeEvent
    public static void onRightClickBlockEvent(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        InteractionHand hand = event.getHand();
        BlockPos pos = event.getPos();
        if (level.isClientSide()) {
            return;

        }

        boolean isCancelEvent = ManaitaEventHelper.attachmentExecute(player.getItemInHand(hand), dataAttachment -> dataAttachment.onRightClickBlock(level, pos, player, hand));
        event.setCanceled(isCancelEvent);
    }

    @SubscribeEvent
    public static void onRightClickEvent(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        InteractionHand hand = event.getHand();
        BlockPos pos = event.getPos();
        if (level.isClientSide()) {
            return;
        }
        boolean isCancelEvent = ManaitaEventHelper.attachmentExecute(player.getItemInHand(hand), dataAttachment -> dataAttachment.onRightClickItem(level, pos, player, hand));
        event.setCanceled(isCancelEvent);
    }

    @SubscribeEvent
    public static void onLeftClickBlockEvent(PlayerInteractEvent.LeftClickBlock event) {
        ItemStack stack = event.getItemStack();
        if (stack.isEmpty()) {
            return;
        }

        Player player = event.getEntity();
        Level level = event.getLevel();
        InteractionHand hand = event.getHand();
        BlockPos pos = event.getPos();
        if (level.isClientSide()) {
            return;
        }
        boolean isCancelEvent = ManaitaEventHelper.attachmentExecute(player.getItemInHand(hand), dataAttachment -> dataAttachment.onLeftClickBlock(level, pos, player, hand));
        event.setCanceled(isCancelEvent);
    }

    @SubscribeEvent
    public static void onEntityTickEvent(EntityTickEvent.Pre event) {
        Entity entity = event.getEntity();
        ManaitaEventHelper.attachmentExecute(entity, dataAttachment -> dataAttachment.onInvTick(entity.level(), entity));
    }

    @SubscribeEvent
    public static void onAttackEntityEvent(AttackEntityEvent event) {
        Player player = event.getEntity();
        Entity target = event.getTarget();
        ManaitaEventHelper.attachmentExecute(player.getMainHandItem(), dataAttachment -> dataAttachment.onAttackEntity(player.level(), player, target));
    }

    @SubscribeEvent
    public static void onLivingEquipmentChangeEvent(LivingEquipmentChangeEvent event) {
        LivingEntity livingEntity = event.getEntity();
        Level level = livingEntity.level();
        EquipmentSlot slot = event.getSlot();
        ManaitaEventHelper.attachmentExecute(livingEntity.getItemBySlot(slot), dataAttachment -> dataAttachment.onLivingEquipmentChange(level, livingEntity, event.getTo(), event.getFrom()));
    }

    @SubscribeEvent
    public static void onLivingDeathEvent(LivingDeathEvent event) {
        event.setCanceled(ManaitaEventHelper.isCancelFromLiving(event.getEntity()));
    }

    @SubscribeEvent
    public static void onLivingIncomingDamageEvent(LivingIncomingDamageEvent event) {
        event.setCanceled(ManaitaEventHelper.isCancelFromLiving(event.getEntity()));
    }

    @SubscribeEvent
    public static void onLivingKnockBackEvent(LivingKnockBackEvent event) {
        event.setCanceled(ManaitaEventHelper.isCancelFromLiving(event.getEntity()));
    }

}
