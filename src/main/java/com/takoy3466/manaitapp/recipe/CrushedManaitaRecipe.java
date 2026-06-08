package com.takoy3466.manaitapp.recipe;

import com.takoy3466.manaitapp.dataComponent.CrushedManaitaData;
import com.takoy3466.manaitapp.init.DataInit;
import com.takoy3466.manaitapp.init.SerializersInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class CrushedManaitaRecipe implements CraftingRecipe {

    public CrushedManaitaRecipe() {
    }

    @Override
    public @NotNull CraftingBookCategory category() {
        return CraftingBookCategory.MISC;
    }

    @Override
    public boolean matches(CraftingInput input, @NotNull Level level) {
        boolean source = false;
        boolean item = false;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack stack = input.getItem(i);
            if (!stack.isEmpty()) {
                boolean isContainData = stack.has(DataInit.CRUSHED_DATA);
                if (isContainData) {
                    if (!source) {source = true;}
                    else {
                        if (item) {return false;}
                        item = true;
                    }
                } else {
                    if (item) {return false;}
                    item = true;
                }
            }
        }
        return source && item;
    }

    @Override
    public @NotNull ItemStack assemble(CraftingInput input, HolderLookup.@NotNull Provider provider) {
        ItemStack targetStack = ItemStack.EMPTY;
        int source = 0;
        // CRUSHED_DATAを持ちかつデータがtrueのアイテム (以後データ持ちアイテムと呼ぶ) を記録しておく。
        ItemStack crushedStack = ItemStack.EMPTY;
        for(int i = 0; i < input.size(); ++i) {
            ItemStack stackInSlot = input.getItem(i);
            if (!stackInSlot.isEmpty()) {
                if (stackInSlot.has(DataInit.CRUSHED_DATA)) {
                    ++source;
                    // データ餅アイテムの場合記録しておく。
                    crushedStack = stackInSlot;
                }else {
                    targetStack = stackInSlot;
                }
            }
        }

        DataComponentMap components = crushedStack.getComponents();
        CrushedManaitaData data = components.get(DataInit.CRUSHED_DATA.get());
        int multiple = 1;
        if (data != null) {
            multiple = data.getMsg();
        }

        ItemStack result;
        if (targetStack.isEmpty()) { // 単純に倍化ターゲットがないとき
            result = ItemStack.EMPTY;
        } else {
            switch (source) {
                case 1 -> { // 倍化ターゲットがいてかつデータ持ちアイテムが1つしかないとき
                    result = targetStack.copy();
                    result.setCount(multiple);
                }
                case 2 -> { // 倍化ターゲットアイテムとデータ持ちアイテムが同じ場合の処理
                    result = crushedStack.copy();
                    result.setCount(multiple);
                }
                default -> result = ItemStack.EMPTY;
            }
        }
        return result;
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
