package com.takoy3466.manaitapp.event;

import com.takoy3466.manaitapp.ManaitaPPCommon;
import com.takoy3466.manaitapp.init.CompatItems;
import com.takoy3466.manaitapp.init.CompatData;
import com.takoy3466.manaitapp.util.ArmorUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ManaitaPPCommon.MOD_ID)
public class ManaitaSubscribeEvent {

    @SubscribeEvent
    public static void onRightClickBlockEvent(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        InteractionHand hand = event.getHand();
        BlockPos pos = event.getPos();
        Direction face = event.getFace();
        if (level.isClientSide() || face == null) {
            return;

        }

        boolean isCancelEvent = ManaitaEventHelper.attachmentExecute(player.getItemInHand(hand), dataAttachment -> dataAttachment.onRightClickBlock(level, pos, face, player, hand));
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
    public static void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        ManaitaEventHelper.attachmentEquipSlotExecute(player, dataAttachment -> dataAttachment.onInvTick(player.level(), player));
    }

    @SubscribeEvent
    public static void onAttackEntityEvent(LivingAttackEvent event) {
        Entity attacker = event.getSource().getEntity();
        if (!(attacker instanceof Player player)) {
            return;
        }
        LivingEntity target = event.getEntity();
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
        event.setCanceled(ArmorUtil.playerEquipDataItem(event.getEntity(), CompatData.INVINCIBLE_DATA.get()));
    }

    @SubscribeEvent
    public static void onLivingIncomingDamageEvent(LivingDamageEvent event) {
        event.setCanceled(ArmorUtil.playerEquipDataItem(event.getEntity(), CompatData.INVINCIBLE_DATA.get()));
    }

    @SubscribeEvent
    public static void onLivingKnockBackEvent(net.minecraftforge.event.entity.living.LivingKnockBackEvent event) {
        event.setCanceled(ArmorUtil.playerEquipDataItem(event.getEntity(), CompatData.INVINCIBLE_DATA.get()));
    }

    @SubscribeEvent
    public static void onBlockDropEvent(BlockEvent.BreakEvent event) {
        blockDrop(event.getState(), event.getPos(), event.getPlayer().level(), event.getPlayer(), Blocks.BEDROCK, Items.BEDROCK);
    }

    public static void blockDrop(BlockState state, BlockPos pos, Level level, Player player, Block block, Item item) {
        if (state.is(block)) {
            if (player.getMainHandItem().get(CompatData.INSTANT_BLOCK_DATA.get()) != null) {
                new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(item));
            }
        }
    }








    @SubscribeEvent
    public static void onManaitaOriginDrop(BlockEvent.BreakEvent event) {
        if (event.getState().is(BlockTags.LOGS) && event.getPlayer().getMainHandItem().isEmpty()) {
            if (event.getLevel().getRandom().nextFloat() >= 0.8F) {
                return;
            }
            new ItemEntity(event.getPlayer().level(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), new ItemStack(CompatItems.MANAITA_ORIGIN.get()));
        }
    }

}
