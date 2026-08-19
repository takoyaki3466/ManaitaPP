package com.takoy3466.manaitapp.util;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ManaitaShapeUtil {

    private static final VoxelShape UP_SHAPE = Block.box(1, 0, 3, 15, 1, 13);
    private static final VoxelShape DOWN_SHAPE = Block.box(1, 15, 3, 15, 16, 13);
    private static final VoxelShape SOUTH_SHAPE = Block.box(1, 3, 0, 15, 13, 1);
    private static final VoxelShape NORTH_SHAPE = Block.box(1, 3, 15, 15, 13, 16);
    private static final VoxelShape EAST_SHAPE = Block.box(0, 3, 1, 1, 13, 15);
    private static final VoxelShape WEST_SHAPE = Block.box(15, 3, 1, 16, 13, 15);

    public static VoxelShape getShape(Direction direction) {
        return switch (direction) {
            case UP -> UP_SHAPE;
            case DOWN -> DOWN_SHAPE;
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
            case EAST -> EAST_SHAPE;
        };
    }
}
