package com.takoy3466.manaitapp.item.armor;

import com.takoy3466.manaitapp.dataComponent.InvincibleData;
import com.takoy3466.manaitapp.dataComponent.ManaitaFlyData;
import com.takoy3466.manaitapp.init.DataInit;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;

public class ManaitaHelmet extends ArmorItem {

    public ManaitaHelmet() {
        super(new Holder.Direct<>(ManaitaArmorMaterial.MANAITA_MATERIAL), Type.HELMET,
                new Properties()
                        .component(DataInit.FLY_DATA, new ManaitaFlyData(ManaitaFlyData.FlySpeed.DEFAULT))
                        .component(DataInit.INVINCIBLE_DATA, new InvincibleData(true))
        );
    }
}
