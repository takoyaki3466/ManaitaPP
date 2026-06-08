package com.takoy3466.manaitapp.core.registry.register;

import com.takoy3466.manaitapp.core.registry.holder.DoubleHolder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class DoubleRegister<T, U> {
    private final DeferredRegister<T> front;
    private final DeferredRegister<U> behind;

    private DoubleRegister(ResourceKey<? extends Registry<T>> frontKey, ResourceKey<? extends Registry<U>> behindKey, String modId) {
        this.front = DeferredRegister.create(frontKey, modId);
        this.behind = DeferredRegister.create(behindKey, modId);
    }

    public static <V, W> DoubleRegister<V, W> create(ResourceKey<? extends Registry<V>> frontKey, ResourceKey<? extends Registry<W>> behindKey, String modId) {
        return new DoubleRegister<>(frontKey, behindKey, modId);
    }

    public static <V, W> DoubleRegister<V, W> create(Registry<V> frontRegistry, Registry<W> behindRegistry, String modId) {
        return new DoubleRegister<>(frontRegistry.key(), behindRegistry.key(), modId);
    }

    public static BlockRegister createBlock(String modId) {
        return new BlockRegister(modId);
    }

    public <I extends T, J extends U> DoubleHolder<T, I, U, J> register(String frontName, Supplier<I> frontSup, String behindName, Supplier<J> behindSup) {
        return DoubleHolder.of(registerFront(frontName, frontSup), registerBehind(behindName, behindSup));
    }

    public <I extends T> DeferredHolder<T, I> registerFront(String name, Supplier<I> sup) {
        return front.register(name, sup);
    }

    public <J extends U> DeferredHolder<U, J> registerBehind(String name, Supplier<J> sup) {
        return behind.register(name, sup);
    }

    public void register(IEventBus bus) {
        this.front.register(bus);
        this.behind.register(bus);
    }

    public DeferredRegister<T> getFront() {
            return front;
    }

    public DeferredRegister<U> getBehind() {
        return behind;
    }

    public static class BlockRegister {
        private final DoubleRegister<Block, Item> REGISTER;

        public BlockRegister(String modId) {
            this.REGISTER = DoubleRegister.create(Registries.BLOCK, Registries.ITEM, modId);
        }

        public <BLOCK extends Block> DoubleHolder.BlockHolder<BLOCK> register(String name, Supplier<BLOCK> sup, Item.Properties itemProperties) {
            DeferredHolder<Block, BLOCK> blockHolder = REGISTER.registerFront(name, sup);
            DeferredHolder<Item, BlockItem> itemHolder = REGISTER.registerBehind(name, () -> new BlockItem(blockHolder.get(), itemProperties));

            return DoubleHolder.BlockHolder.of(blockHolder, itemHolder);
        }

        public void register(IEventBus bus) {
            this.REGISTER.register(bus);
        }

        public DoubleRegister<Block, Item> getRegister() {
            return REGISTER;
        }
    }
}
