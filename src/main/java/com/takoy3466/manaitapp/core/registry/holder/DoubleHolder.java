package com.takoy3466.manaitapp.core.registry.holder;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;

public class DoubleHolder<R, T extends R, S, U extends S> {
    private final DeferredHolder<R, T> front;
    private final DeferredHolder<S, U> behind;

    private DoubleHolder(DeferredHolder<R, T> front, DeferredHolder<S, U> behind) {
        this.front = front;
        this.behind = behind;
    }
    
    public static <R1, T1 extends R1, S1, U1 extends S1> DoubleHolder<R1, T1, S1, U1> of(DeferredHolder<R1, T1> front, DeferredHolder<S1, U1> behind) {
        return new DoubleHolder<>(front, behind);
    }

    public DeferredHolder<R, T> getFront() {
        return front;
    }

    public DeferredHolder<S, U> getBehind() {
        return behind;
    }

    public static class BlockHolder<BLOCK extends Block> {
        private final DoubleHolder<Block, BLOCK, Item, BlockItem> HOLDER;

        public BlockHolder(DoubleHolder<Block, BLOCK, Item, BlockItem> holder) {
            this.HOLDER = holder;
        }

        private BlockHolder(DeferredHolder<Block, BLOCK> front, DeferredHolder<Item, BlockItem> behind) {
            this(DoubleHolder.of(front, behind));
        }

        public static <B extends Block> BlockHolder<B> of(DeferredHolder<Block, B> front, DeferredHolder<Item, BlockItem> behind) {
            return new BlockHolder<>(front, behind);
        }

        public DeferredHolder<Block, BLOCK> getBlockHolder() {
            return HOLDER.getFront();
        }

        public DeferredHolder<Item, BlockItem> getItemHolder() {
            return HOLDER.getBehind();
        }

        public BLOCK getBlock() {
            return getBlockHolder().get();
        }

        public BlockItem getItem() {
            return getItemHolder().get();
        }
    }
}
