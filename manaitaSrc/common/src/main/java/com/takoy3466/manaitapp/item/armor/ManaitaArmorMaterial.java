package com.takoy3466.manaitapp.item.armor;

import com.takoy3466.manaitapp.ManaitaPPCommon;
import com.takoy3466.manaitapp.core.Identifier;
import net.minecraft.Util;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;

public class ManaitaArmorMaterial {
    public static final Identifier identifier = new Identifier(ManaitaPPCommon.MOD_ID, "armor_manaita");

    public static ArmorMaterial MANAITA_MATERIAL = new ArmorMaterial(
            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.HELMET, 10);
                map.put(ArmorItem.Type.CHESTPLATE, 10);
                map.put(ArmorItem.Type.LEGGINGS, 10);
                map.put(ArmorItem.Type.BOOTS, 10);
                map.put(ArmorItem.Type.BODY, 10);
            }),
            30,
            SoundEvents.ARMOR_EQUIP_GENERIC,
            () -> Ingredient.EMPTY,
            List.of(
                    new ArmorMaterial.Layer(identifier.get())
            ),
            2147473647,
            2
    );
}
