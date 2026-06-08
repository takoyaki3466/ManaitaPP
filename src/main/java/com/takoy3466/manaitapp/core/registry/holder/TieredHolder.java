package com.takoy3466.manaitapp.core.registry.holder;

import net.neoforged.neoforge.registries.DeferredHolder;

public class TieredHolder<TIER, BASE, EXTEND extends BASE> {
    private final DeferredHolder<BASE, EXTEND> holder;
    private final TIER tier;

    public TieredHolder(DeferredHolder<BASE, EXTEND> holder, TIER tier) {
        this.holder = holder;
        this.tier = tier;
    }

    public DeferredHolder<BASE, EXTEND> getHolder() {
        return holder;
    }

    public TIER getTier() {
        return tier;
    }

    public static class Single<TIER, T> extends TieredHolder<TIER, T, T> {
        public Single(DeferredHolder<T, T> holder, TIER tier) {
            super(holder, tier);
        }

        private Single(TieredHolder<TIER, T, T> holder) {
            super(holder.getHolder(), holder.getTier());
        }

        public static <TIER, T> TieredHolder.Single<TIER, T> of(TieredHolder<TIER, T, T> holder) {
            return new Single<>(holder);
        }
    }
}
