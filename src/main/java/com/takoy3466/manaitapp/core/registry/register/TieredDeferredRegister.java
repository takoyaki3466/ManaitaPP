package com.takoy3466.manaitapp.core.registry.register;

import com.takoy3466.manaitapp.core.registry.holder.TieredHolder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public class TieredDeferredRegister<TIER, BASE> {
    private final DeferredRegister<BASE> register;

    private TieredDeferredRegister(ResourceKey<Registry<BASE>> resourceKey, String modId) {
        this.register = DeferredRegister.create(resourceKey, modId);
    }

    public static <U, V> TieredDeferredRegister<U, V> create(ResourceKey<Registry<V>> resourceKey, String modId) {
        return new TieredDeferredRegister<>(resourceKey, modId);
    }

    public <EXTEND extends BASE> TieredHolder<TIER, BASE, EXTEND> register(String name, TIER tier, Supplier<? extends EXTEND> sup) {
        return new TieredHolder<>(register.register(name, sup), tier);
    }

    public <EXTEND extends BASE> TieredHolder<TIER, BASE, EXTEND> register(String name, TIER tier, Function<TIER, EXTEND> func) {
        return register(name, tier, () -> func.apply(tier));
    }

    public static <TIER> Blocks<TIER> createBlocks(String modId) {
        return new Blocks<>(modId);
    }

    public static <TIER> Items<TIER> createItems(String modId) {
        return new Items<>(modId);
    }

    public static class Blocks<TIER> extends TieredDeferredRegister<TIER, Block> {
        private Blocks(String modId) {
            super(Registries.BLOCK, modId);
        }
    }

    public static class Items<TIER> extends TieredDeferredRegister<TIER, Item> {
        private Items(String modId) {
            super(Registries.ITEM, modId);
        }
    }
}
