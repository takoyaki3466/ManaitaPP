package com.takoy3466.manaitapp.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.Collections;
import java.util.HashMap;

public class ToolUtil {

    // 逆マップ（Strippedブロックネーム → 原木ブロック）
    private static final HashMap<String, Block> REVERSE_STRIPPABLES = new HashMap<>();

    public static void init() {
        System.out.println("init was called!");
        mapMaker(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD);
        mapMaker(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG);
        mapMaker(Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD);
        mapMaker(Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG);
        mapMaker(Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD);
        mapMaker(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG);
        mapMaker(Blocks.CHERRY_WOOD, Blocks.STRIPPED_CHERRY_WOOD);
        mapMaker(Blocks.CHERRY_LOG, Blocks.STRIPPED_CHERRY_LOG);
        mapMaker(Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD);
        mapMaker(Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG);
        mapMaker(Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD);
        mapMaker(Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG);
        mapMaker(Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD);
        mapMaker(Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG);
        mapMaker(Blocks.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM);
        mapMaker(Blocks.WARPED_HYPHAE, Blocks.STRIPPED_WARPED_HYPHAE);
        mapMaker(Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM);
        mapMaker(Blocks.CRIMSON_HYPHAE, Blocks.STRIPPED_CRIMSON_HYPHAE);
        mapMaker(Blocks.MANGROVE_WOOD, Blocks.STRIPPED_MANGROVE_WOOD);
        mapMaker(Blocks.MANGROVE_LOG, Blocks.STRIPPED_MANGROVE_LOG);
        mapMaker(Blocks.BAMBOO_BLOCK, Blocks.STRIPPED_BAMBOO_BLOCK);
    }

    public static void mapMaker(Block originalBlock, Block strippedBlock) {
        REVERSE_STRIPPABLES.put(strippedBlock.getDescriptionId(), originalBlock);
    }

    public static void woodReverse(Level level, BlockPos pos, Player player, ItemStack stack, InteractionHand hand) {
        if (REVERSE_STRIPPABLES.isEmpty()) {
            init();
        }

        BlockState state = level.getBlockState(pos);
        Block strippedBlock = state.getBlock();

        Block originalBlock = REVERSE_STRIPPABLES.get(strippedBlock.getDescriptionId());
        if (originalBlock == null) {
            return;
        }

        BlockState strippedState = originalBlock.defaultBlockState();

        if (strippedState.hasProperty(RotatedPillarBlock.AXIS)) {
            strippedState = strippedState.setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS));
        }
        level.setBlock(pos, strippedState, 1 + 2 + 8);
        level.playSound(player, pos, SoundType.WOOD.getPlaceSound(), SoundSource.BLOCKS, 1, 1);
        EquipmentSlot slot = switch (hand) {
            case MAIN_HAND -> EquipmentSlot.MAINHAND;
            case OFF_HAND -> EquipmentSlot.OFFHAND;
        };
        stack.hurtAndBreak(1, player, slot);
    }

    public static InteractionResult woodReverse(UseOnContext context) {
        if (context == null) {
            return InteractionResult.PASS;
        }
        woodReverse(context.getLevel(), context.getClickedPos(), context.getPlayer(), context.getItemInHand(), context.getHand());
        return InteractionResult.SUCCESS;
    }

    public static void spreadGrow(Level level, BlockPos pos, int radius) {
        if (!level.isClientSide()) {
            for (int x = -1* radius; x <= radius; x++) {
                for (int y = -1* radius; y <= radius; y++) {
                    for (int z = -1* radius; z <= radius; z++) {
                        BlockPos targetPos = pos.offset(x, y, z);
                        BlockState state = level.getBlockState(targetPos);

                        for (Property<?> property : state.getProperties()) {
                            if (property.getName().equals("age") && property instanceof IntegerProperty ageProperty) {
                                int currentAge = state.getValue(ageProperty);
                                int maxAge = Collections.max(ageProperty.getPossibleValues());

                                if (currentAge < maxAge) {
                                    level.setBlock(targetPos, state.setValue(ageProperty, maxAge), 2);
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public static void rangeBreak(LevelAccessor levelAccessor, BlockPos pos, LivingEntity entity, int range) {
        rangeBreak(levelAccessor, pos.getX(), pos.getY(), pos.getZ(), entity, range);
    }

    public static void rangeBreak(LevelAccessor levelAccessor, int x, int y, int z, LivingEntity entity, int range) {
        if (entity == null) {
            return;
        }

        int numRange = -1 * (range - 1) / 2;
        int whileRange = (range + 1) / 2;
        int X, Y, Z;

        if ((entity.getXRot() >= 40 && entity.getXRot() <= 90) || (entity.getXRot() <= -40 && entity.getXRot() >= -90)) {
            X = numRange;
            Z = numRange;
            while (Z < whileRange) {
                while (X < whileRange) {
                    levelAccessor.destroyBlock(BlockPos.containing(x + X, y, z + Z), true, entity);
                    X++;
                }
                Z++;
                X = numRange;
            }
        } else if ((entity.getDirection()) == Direction.NORTH || (entity.getDirection()) == Direction.SOUTH) {
            X = numRange;
            Y = numRange;
            while (Y < whileRange) {
                while (X < whileRange) {
                    levelAccessor.destroyBlock(BlockPos.containing(x + X, y + Y, z), true, entity);
                    X++;
                }
                Y++;
                X = numRange;
            }
        } else if ((entity.getDirection()) == Direction.EAST || (entity.getDirection()) == Direction.WEST) {
            Y = numRange;
            Z = numRange;
            while (Y < whileRange) {
                while (Z < whileRange) {
                    levelAccessor.destroyBlock(BlockPos.containing(x, y + Y, z + Z), true, entity);
                    Z++;
                }
                Y++;
                Z = numRange;
            }
        }
    }
}
