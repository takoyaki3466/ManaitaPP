package com.takoy3466.manaitapp.event;

import com.takoy3466.manaitapp.Manaitapp;
import com.takoy3466.manaitapp.init.DataInit;
import com.takoy3466.manaitapp.init.ItemsInit;
import com.takoy3466.manaitapp.util.ArmorUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockDropsEvent;
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
    public static void onPlayerTickEvent(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        ManaitaEventHelper.attachmentEquipSlotExecute(player, dataAttachment -> dataAttachment.onInvTick(player.level(), player));
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
        event.setCanceled(ArmorUtil.playerEquipDataItem(event.getEntity(), DataInit.INVINCIBLE_DATA.get()));
    }

    @SubscribeEvent
    public static void onLivingIncomingDamageEvent(LivingIncomingDamageEvent event) {
        event.setCanceled(ArmorUtil.playerEquipDataItem(event.getEntity(), DataInit.INVINCIBLE_DATA.get()));
    }

    @SubscribeEvent
    public static void onLivingKnockBackEvent(LivingKnockBackEvent event) {
        event.setCanceled(ArmorUtil.playerEquipDataItem(event.getEntity(), DataInit.INVINCIBLE_DATA.get()));
    }

    @SubscribeEvent
    public static void onBlockDropEvent(BlockDropsEvent event) {
        blockDrop(event, Blocks.BEDROCK, Items.BEDROCK);
    }

    public static void blockDrop(BlockDropsEvent event, Block block, Item item) {
        if (event.getState().is(block) && event.getBreaker() instanceof LivingEntity livingEntity) {
            if (livingEntity.getMainHandItem().get(DataInit.INSTANT_BLOCK_DATA) != null) {
                event.getDrops().add(new ItemEntity(event.getLevel(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), new ItemStack(item)));
            }
        }
    }








    @SubscribeEvent
    public static void onManaitaOriginDrop(BlockDropsEvent event) {
        if (event.getState().is(BlockTags.LOGS) && event.getBreaker() instanceof LivingEntity livingEntity && livingEntity.getMainHandItem().isEmpty()) {
            if (event.getLevel().getRandom().nextFloat() >= 0.8F) {
                return;
            }
            event.getDrops().add(new ItemEntity(event.getLevel(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), new ItemStack(ItemsInit.MANAITA_ORIGIN.get())));
        }
    }

}
