package com.takoy3466.manaitapp.item.armor;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;

public class ManaitaLeggings extends ArmorItem {

    public ManaitaLeggings() {
        super(new Holder.Direct<>(ManaitaArmorMaterial.MANAITA_MATERIAL), Type.LEGGINGS, new Item.Properties());
    }
}
