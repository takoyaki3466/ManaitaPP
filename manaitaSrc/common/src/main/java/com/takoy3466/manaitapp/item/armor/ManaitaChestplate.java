package com.takoy3466.manaitapp.item.armor;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;

public class ManaitaChestplate extends ArmorItem {

    public ManaitaChestplate() {
        super(new Holder.Direct<>(ManaitaArmorMaterial.MANAITA_MATERIAL), Type.CHESTPLATE, new Item.Properties());
    }
}
