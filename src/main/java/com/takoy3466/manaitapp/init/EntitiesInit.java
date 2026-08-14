package com.takoy3466.manaitapp.init;

import com.takoy3466.manaitapp.Manaitapp;
import com.takoy3466.manaitapp.entity.EntityManaitaArrow;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class EntitiesInit {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, Manaitapp.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<EntityManaitaArrow>> MANAITA_ARROW = ENTITY_TYPES.register("manaita_arrow",
            () -> EntityType.Builder.of(EntityManaitaArrow::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("textures/entity/manaita_arrow")
    );
}
