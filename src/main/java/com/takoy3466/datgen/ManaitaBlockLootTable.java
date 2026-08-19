package com.takoy3466.datgen;

import com.takoy3466.manaitapp.init.BlocksInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class ManaitaBlockLootTable extends BlockLootSubProvider {


    protected ManaitaBlockLootTable(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, registries);
    }

    @Override
    protected void generate() {
        BlocksInit.BLOCKS.getRegister().getFront().getEntries().forEach(block -> dropSelf(block.get()));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BlocksInit.BLOCKS.getRegister().getFront().getEntries().stream().map(e -> (Block) e.get()).toList();
    }
}
