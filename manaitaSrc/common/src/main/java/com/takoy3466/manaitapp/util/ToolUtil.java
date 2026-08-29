package com.takoy3466.manaitapp.util;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ToolUtil {

    // 逆マップ（Strippedブロックネーム → 原木ブロック）
    private static final HashMap<String, Block> REVERSE_STRIPPABLES = new HashMap<>();
    private static final Map<Block, Block> STRIPPABLES;

    protected static final Map<Block, Pair<UniqueUtil.TriPredicate<Direction, Level, BlockPos>, UniqueUtil.FourConsumer<Player, Level, BlockPos, Direction>>> TILLABLES;

    //Init
    static {
        STRIPPABLES = (new ImmutableMap.Builder<Block, Block>())
                .put(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD)
                .put(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG)
                .put(Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD)
                .put(Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG)
                .put(Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD)
                .put(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG)
                .put(Blocks.CHERRY_WOOD, Blocks.STRIPPED_CHERRY_WOOD)
                .put(Blocks.CHERRY_LOG, Blocks.STRIPPED_CHERRY_LOG)
                .put(Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD)
                .put(Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG)
                .put(Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD)
                .put(Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG)
                .put(Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD)
                .put(Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG)
                .put(Blocks.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM)
                .put(Blocks.WARPED_HYPHAE, Blocks.STRIPPED_WARPED_HYPHAE)
                .put(Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM)
                .put(Blocks.CRIMSON_HYPHAE, Blocks.STRIPPED_CRIMSON_HYPHAE)
                .put(Blocks.MANGROVE_WOOD, Blocks.STRIPPED_MANGROVE_WOOD)
                .put(Blocks.MANGROVE_LOG, Blocks.STRIPPED_MANGROVE_LOG)
                .put(Blocks.BAMBOO_BLOCK, Blocks.STRIPPED_BAMBOO_BLOCK)
                .build();

        Map<Block, Pair<UniqueUtil.TriPredicate<Direction, Level, BlockPos>, UniqueUtil.FourConsumer<Player, Level, BlockPos, Direction>>> map = new HashMap<>();
        map.put(Blocks.GRASS_BLOCK, Pair.of(ToolUtil::onlyIfAirAbove, changeIntoState(Blocks.FARMLAND.defaultBlockState())));
        map.put(Blocks.DIRT_PATH, Pair.of(ToolUtil::onlyIfAirAbove, changeIntoState(Blocks.FARMLAND.defaultBlockState())));
        map.put(Blocks.DIRT, Pair.of(ToolUtil::onlyIfAirAbove, changeIntoState(Blocks.FARMLAND.defaultBlockState())));
        map.put(Blocks.COARSE_DIRT, Pair.of(ToolUtil::onlyIfAirAbove, changeIntoState(Blocks.DIRT.defaultBlockState())));
        map.put(Blocks.ROOTED_DIRT, Pair.of((dir, lev, pos) -> true, changeIntoStateAndDropItem(Blocks.DIRT.defaultBlockState(), Items.HANGING_ROOTS)));
        TILLABLES = Maps.newHashMap(map);

    }

    public static void init() {
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



    public static InteractionResult stripWood(Level level, BlockPos pos, Player player, InteractionHand hand) {
        if (playerHasShieldUseIntent(player, hand)) {
            return InteractionResult.PASS;
        } else {
            Optional<BlockState> optional = evaluateNewBlockState(level, pos, player, level.getBlockState(pos));
            if (optional.isEmpty()) {
                return InteractionResult.PASS;
            } else {
                ItemStack itemstack = player.getItemInHand(hand);
                if (player instanceof ServerPlayer serverPlayer) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, itemstack);
                }

                level.setBlock(pos, optional.get(), 11);
                level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, optional.get()));
                itemstack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));

                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }
    }

    private static boolean playerHasShieldUseIntent(Player player, InteractionHand hand) {
        return hand.equals(InteractionHand.MAIN_HAND) && player.getOffhandItem().is(Items.SHIELD) && !player.isSecondaryUseActive();
    }

    private static Optional<BlockState> evaluateNewBlockState(Level level, BlockPos pos, @Nullable Player player, BlockState state) {
        Optional<BlockState> optional = getStripped(state);
        if (optional.isPresent()) {
            level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
            return optional;
        } else {
            Optional<BlockState> optional1 = WeatheringCopper.getPrevious(state);
            if (optional1.isPresent()) {
                level.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.levelEvent(player, 3005, pos, 0);
                return optional1;
            } else {
                Optional<BlockState> optional2 = Optional.ofNullable((Block)((BiMap<?, ?>) HoneycombItem.WAX_OFF_BY_BLOCK.get()).get(state.getBlock())).map((block) -> block.withPropertiesOf(state));
                if (optional2.isPresent()) {
                    level.playSound(player, pos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
                    level.levelEvent(player, 3004, pos, 0);
                    return optional2;
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    private static Optional<BlockState> getStripped(BlockState unstrippedState) {
        return Optional.ofNullable(STRIPPABLES.get(unstrippedState.getBlock())).map((block) -> block.defaultBlockState().setValue(RotatedPillarBlock.AXIS, unstrippedState.getValue(RotatedPillarBlock.AXIS)));
    }



    public static InteractionResult tillSoil(Level level, BlockPos pos, Direction face, Player player, InteractionHand hand) {
        Pair<UniqueUtil.TriPredicate<Direction, Level, BlockPos>, UniqueUtil.FourConsumer<Player, Level, BlockPos, Direction>> pair = TILLABLES.get(level.getBlockState(pos).getBlock());
        if (pair == null) {
            return InteractionResult.PASS;
        } else {
            UniqueUtil.TriPredicate<Direction, Level, BlockPos> predicate = pair.getFirst();
            UniqueUtil.FourConsumer<Player, Level, BlockPos, Direction> consumer = pair.getSecond();
            if (predicate.test(face, level, pos)) {
                level.playSound(player, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (!level.isClientSide()) {
                    consumer.accept(player, level, pos, face);
                    if (player != null) {
                        player.getItemInHand(hand).hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
                    }
                }

                return InteractionResult.sidedSuccess(level.isClientSide());
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    private static UniqueUtil.FourConsumer<Player, Level, BlockPos, Direction> changeIntoState(BlockState state) {
        return (player, level, pos, face) -> {
            level.setBlock(pos, state, 11);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, state));
        };
    }

    private static UniqueUtil.FourConsumer<Player, Level, BlockPos, Direction> changeIntoStateAndDropItem(BlockState state, ItemLike itemToDrop) {
        return (player, level, pos, face) -> {
            level.setBlock(pos, state, 11);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, state));
            Block.popResourceFromFace(level, pos, face, new ItemStack(itemToDrop));
        };
    }

    private static boolean onlyIfAirAbove(Direction face, Level level, BlockPos pos) {
        return face != Direction.DOWN && level.getBlockState(pos.above()).isAir();
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
