package com.takoy3466.manaitapp.recipe;

import com.takoy3466.manaitapp.init.SerializersInit;
import com.takoy3466.manaitapp.util.CraftingUtil;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
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

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return i * i1 >= 2;
    }

    /**
     * @return 今回はレシピ結果が動的に変わるのでEmptyを置いています。
     */
    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider provider) {
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return SerializersInit.MANAITA_RECIPE.get();
    }
}
