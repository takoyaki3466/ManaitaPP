package com.takoy3466.manaitapp.item.armor;

import com.takoy3466.manaitapp.ManaitaPPCommon;
import com.takoy3466.manaitapp.core.Identifier;
import com.takoy3466.manaitapp.util.ArmorUtil;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.equipment.ArmorMaterial;

public class ManaitaArmorMaterial {
    public static final Identifier identifier = new Identifier(ManaitaPPCommon.MOD_ID, "armor_manaita");

    public static ArmorMaterial MANAITA_MATERIAL = new ArmorMaterial(
            2147483647,
            ArmorUtil.getSimpleDefence(10),
            30,
            SoundEvents.ARMOR_EQUIP_GENERIC,
            Float.MAX_VALUE,
            2147473647,
            ItemTags.REPAIRS_IRON_ARMOR,
            identifier.get()
    );
}
