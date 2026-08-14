package com.takoy3466.datgen;

import com.takoy3466.manaitapp.block.abstracts.AbstractBlockManaitaFurnace;
import com.takoy3466.manaitapp.core.registry.holder.DoubleHolder;
import com.takoy3466.manaitapp.init.BlocksInit;
import com.takoy3466.manaitapp.init.ItemsInit;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ManaitaRecipe extends RecipeProvider {
    public ManaitaRecipe(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, BlocksInit.MANAITA_WOOD.getItem())
                .requires(ItemsInit.MANAITA_ORIGIN.get())
                .unlockedBy("hasItem", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(recipeOutput);

        addCraftingManaita(recipeOutput, BlocksInit.MANAITA_STONE, Items.COBBLESTONE, BlocksInit.MANAITA_WOOD);
        addCraftingManaita(recipeOutput, BlocksInit.MANAITA_IRON, Items.IRON_INGOT, BlocksInit.MANAITA_STONE);
        addCraftingManaita(recipeOutput, BlocksInit.MANAITA_GOLD, Items.GOLD_INGOT, BlocksInit.MANAITA_IRON);
        addCraftingManaita(recipeOutput, BlocksInit.MANAITA_DIAMOND, Items.DIAMOND, BlocksInit.MANAITA_GOLD);


        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, BlocksInit.MANAITA_MTK.getItem())
                .define('#', ItemsInit.MANAITA_ORIGIN.get())
                .define('C', BlocksInit.MANAITA_DIAMOND.getItem())
                .pattern("#C#")
                .unlockedBy("hasItem", InventoryChangeTrigger.TriggerInstance.hasItems(ItemsInit.MANAITA_ORIGIN))
                .save(recipeOutput);


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, BlocksInit.MANAITA_FURNACE_WOOD.getItem())
                .requires(ItemsInit.MANAITA_ORIGIN.get())
                .requires(Items.FURNACE)
                .unlockedBy("hasItem", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(recipeOutput);

        addFurnaceManaita(recipeOutput, BlocksInit.MANAITA_FURNACE_STONE.getItem(), Items.COBBLESTONE, BlocksInit.MANAITA_FURNACE_WOOD.getItem());
        addFurnaceManaita(recipeOutput, BlocksInit.MANAITA_FURNACE_IRON.getItem(), Items.IRON_INGOT, BlocksInit.MANAITA_FURNACE_STONE.getItem());
        addFurnaceManaita(recipeOutput, BlocksInit.MANAITA_FURNACE_GOLD.getItem(), Items.GOLD_INGOT, BlocksInit.MANAITA_FURNACE_IRON.getItem());
        addFurnaceManaita(recipeOutput, BlocksInit.MANAITA_FURNACE_DIAMOND.getItem(), Items.DIAMOND, BlocksInit.MANAITA_FURNACE_GOLD.getItem());

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, BlocksInit.MANAITA_FURNACE_MTK.getItem())
                .define('#', ItemsInit.MANAITA_ORIGIN.get())
                .define('C', BlocksInit.MANAITA_FURNACE_DIAMOND.getItem())
                .pattern("#C#")
                .unlockedBy("hasItem", InventoryChangeTrigger.TriggerInstance.hasItems(ItemsInit.MANAITA_ORIGIN))
                .save(recipeOutput);
    }

    public <T extends Block> void addCraftingManaita(RecipeOutput output, DoubleHolder.BlockHolder<T> result, ItemLike ingredient, DoubleHolder.BlockHolder<T> previousItem) {
        addCraftingManaita(output, result.getItem(), ingredient, previousItem.getItem());
    }

    public <T extends Block> void addCraftingManaita(RecipeOutput output, ItemLike result, ItemLike ingredient, ItemLike previousItem) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
                .define('#', ingredient)
                .define('C', previousItem)
                .pattern("###" + "CCC" + "###")
                .unlockedBy("hasItem", InventoryChangeTrigger.TriggerInstance.hasItems(ItemsInit.MANAITA_ORIGIN))
                .save(output);
    }

    public <T extends Block> void addFurnaceManaita(RecipeOutput output, ItemLike result, ItemLike ingredient, ItemLike previousItem) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
                .define('#', ingredient)
                .define('C', previousItem)
                .pattern("###" + "CCC" + "###")
                .unlockedBy("hasItem", InventoryChangeTrigger.TriggerInstance.hasItems(ItemsInit.MANAITA_ORIGIN))
                .save(output);
    }
}
