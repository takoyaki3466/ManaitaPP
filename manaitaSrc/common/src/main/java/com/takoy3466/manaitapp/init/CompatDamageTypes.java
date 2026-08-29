package com.takoy3466.manaitapp.init;

import com.takoy3466.manaitapp.ManaitaPPCommon;
import com.takoy3466.manaitapp.core.Identifier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class CompatDamageTypes {

    public static final ResourceKey<DamageType> MANAITA = ResourceKey.create(Registries.DAMAGE_TYPE, new Identifier(ManaitaPPCommon.MOD_ID, "manaita").get());
}
