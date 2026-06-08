package com.takoy3466.manaitapp.init;

import com.takoy3466.manaitapp.Manaitapp;
import com.takoy3466.manaitapp.recipe.CrushedManaitaRecipe;
import com.takoy3466.manaitapp.recipe.CrushedManaitaRecipeSerializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SerializersInit {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, Manaitapp.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<CrushedManaitaRecipe>> MANAITA_RECIPE = SERIALIZERS.register("manaita_recipe", CrushedManaitaRecipeSerializer::new);
}
