package com.takoy3466.manaitapp.item.armor;

import com.takoy3466.manaitapp.dataComponent.InvincibleData;
import com.takoy3466.manaitapp.dataComponent.ManaitaFlyData;
import com.takoy3466.manaitapp.init.DataInit;
import com.takoy3466.manaitapp.util.ManaitaUnsafeDefence;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ManaitaHelmet extends ArmorItem {

    public ManaitaHelmet() {
        super(new Holder.Direct<>(ManaitaArmorMaterial.MANAITA_MATERIAL), Type.HELMET,
                new Properties()
                        .component(DataInit.FLY_DATA, new ManaitaFlyData(ManaitaFlyData.FlySpeed.DEFAULT))
                        .component(DataInit.INVINCIBLE_DATA, new InvincibleData(true))
        );
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (level.isClientSide()) {
            return;
        }
        if (entity instanceof Player player) {
            ManaitaUnsafeDefence.applyAbsoluteShield(player);
        }
    }
}
