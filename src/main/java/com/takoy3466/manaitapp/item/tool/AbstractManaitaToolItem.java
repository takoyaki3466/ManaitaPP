package com.takoy3466.manaitapp.item.tool;

import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractManaitaToolItem extends Item {
    private final TagKey<Block> blocks;

    public AbstractManaitaToolItem(TagKey<Block> blocks, Properties properties) {
        super(properties);
        this.blocks = blocks;
    }

    @Override
    public float getAttackDamageBonus(Entity target, float damage, DamageSource damageSource) {
        return Float.MAX_VALUE;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return Float.MAX_VALUE;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return state.is(blocks);
    }
}
