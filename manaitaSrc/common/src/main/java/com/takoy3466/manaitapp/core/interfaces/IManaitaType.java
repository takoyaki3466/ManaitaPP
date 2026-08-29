package com.takoy3466.manaitapp.core.interfaces;

import net.minecraft.world.entity.LivingEntity;

public interface IManaitaType {

    void manaitaPP$setManaitaType(boolean isManaita);

    boolean manaitaPP$isManaitaType();

    void manaitaPP$manaitaKill(LivingEntity attacker);
}
