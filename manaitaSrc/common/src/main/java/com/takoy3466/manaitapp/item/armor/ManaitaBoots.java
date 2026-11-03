package com.takoy3466.manaitapp.item.armor;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorType;

public class ManaitaBoots extends ArmorItem {

    public ManaitaBoots(Properties properties) {
        super(ManaitaArmorMaterial.MANAITA_MATERIAL, ArmorType.BOOTS, properties);
    }
}
