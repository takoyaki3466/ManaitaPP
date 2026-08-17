package com.takoy3466.manaitapp.init;

import com.takoy3466.manaitapp.Manaitapp;
import com.takoy3466.manaitapp.core.Identifier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class DamageTypesInit {

    public static final ResourceKey<DamageType> MANAITA = ResourceKey.create(Registries.DAMAGE_TYPE, new Identifier(Manaitapp.MOD_ID, "manaita").get());
}
