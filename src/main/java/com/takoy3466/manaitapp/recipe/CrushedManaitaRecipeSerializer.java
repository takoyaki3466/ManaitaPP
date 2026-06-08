package com.takoy3466.manaitapp.recipe;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public class CrushedManaitaRecipeSerializer implements RecipeSerializer<CrushedManaitaRecipe> {
    public static final MapCodec<CrushedManaitaRecipe> CODEC = MapCodec.unit(CrushedManaitaRecipe::new);
    public static final StreamCodec<RegistryFriendlyByteBuf, CrushedManaitaRecipe> STREAM_CODEC = StreamCodec.unit(new CrushedManaitaRecipe());
    @Override
    public @NotNull MapCodec<CrushedManaitaRecipe> codec() {
        return CODEC;
    }

    @Override
    public @NotNull StreamCodec<RegistryFriendlyByteBuf, CrushedManaitaRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
