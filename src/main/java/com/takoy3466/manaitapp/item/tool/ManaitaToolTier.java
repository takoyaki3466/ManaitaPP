package com.takoy3466.manaitapp.item.tool;

import com.takoy3466.manaitapp.Manaitapp;
import com.takoy3466.manaitapp.core.Identifier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.SimpleTier;

public class ManaitaToolTier {
    public static final TagKey<Block> EMPTY_INCORRECT_TOOL = TagKey.create(BuiltInRegistries.BLOCK.key(), new Identifier(Manaitapp.MOD_ID, "empty_incorrect_tool").get());

    public static Tier MANAITA_TIER = new SimpleTier(
            EMPTY_INCORRECT_TOOL,
            0,
            Float.MAX_VALUE,
            Float.MAX_VALUE,
            10,
            () -> Ingredient.EMPTY
            );
}
