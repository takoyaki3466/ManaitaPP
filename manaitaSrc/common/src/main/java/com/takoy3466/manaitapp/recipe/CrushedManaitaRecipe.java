package com.takoy3466.manaitapp.recipe;

import com.takoy3466.manaitapp.init.CompatSerializers;
import com.takoy3466.manaitapp.util.CraftingUtil;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class CrushedManaitaRecipe implements CraftingRecipe {
    public static CrushedManaitaRecipe INSTANCE = new CrushedManaitaRecipe();

    public CrushedManaitaRecipe() {
    }

    @Override
    public @NotNull CraftingBookCategory category() {
        return CraftingBookCategory.MISC;
    }

    @Override
    public boolean matches(@NotNull CraftingInput input, @NotNull Level level) {
        return CraftingUtil.matches(input);
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull CraftingInput input, HolderLookup.@NotNull Provider provider) {
        return CraftingUtil.assemble(input);
    }

    /**
     * レシピに含まれるIngredientの情報と、それらをクラフト画面のどのスロットに配置すべきかを定義・管理するオブジェクト
     */
    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public @NotNull RecipeSerializer<? extends CraftingRecipe> getSerializer() {
        return CompatSerializers.MANAITA_RECIPE.get();
    }
}
