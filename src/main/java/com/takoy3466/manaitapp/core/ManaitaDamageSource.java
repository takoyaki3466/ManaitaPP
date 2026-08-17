package com.takoy3466.manaitapp.core;

import com.takoy3466.manaitapp.init.DamageTypesInit;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class ManaitaDamageSource extends DamageSource {

    public ManaitaDamageSource(LivingEntity livingEntity) {
        super(livingEntity.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypesInit.MANAITA), livingEntity);
    }
}
