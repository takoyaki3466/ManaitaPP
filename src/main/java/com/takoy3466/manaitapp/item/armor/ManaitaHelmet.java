package com.takoy3466.manaitapp.item.armor;

import com.takoy3466.manaitapp.dataComponent.InvincibleData;
import com.takoy3466.manaitapp.dataComponent.ManaitaFlyData;
import com.takoy3466.manaitapp.dataComponent.helper.DataHoverHelper;
import com.takoy3466.manaitapp.init.DataInit;
import com.takoy3466.manaitapp.util.ManaitaUnsafe;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

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
            ManaitaUnsafe.applyAbsoluteShield(player);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        tooltipComponents.add(DataHoverHelper.hoverDataText(stack, DataInit.FLY_DATA.get(), data -> ": " + data.getMsg()));
        tooltipComponents.add(DataHoverHelper.hoverDataText(stack, DataInit.INVINCIBLE_DATA.get(), data -> ": " + data.getMsg()));
    }
}
