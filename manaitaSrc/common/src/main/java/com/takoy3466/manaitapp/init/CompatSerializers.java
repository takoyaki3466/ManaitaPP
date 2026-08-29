package com.takoy3466.manaitapp.init;

import com.takoy3466.manaitapp.core.registry.holder.CompatHolder;
import com.takoy3466.manaitapp.core.registry.CompatRegistry;
import com.takoy3466.manaitapp.recipe.CrushedManaitaRecipe;
import com.takoy3466.manaitapp.recipe.CrushedManaitaRecipeSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class CompatSerializers {

    public static final CompatHolder<RecipeSerializer<CrushedManaitaRecipe>> MANAITA_RECIPE = CompatRegistry.registerRecipeSerializer("manaita_recipe", CrushedManaitaRecipeSerializer::new);

    public static void init() {
    }
}
