package com.takoy3466.manaitapp.item.armor;

import com.takoy3466.manaitapp.dataComponent.InvincibleData;
import com.takoy3466.manaitapp.dataComponent.ManaitaFlyData;
import com.takoy3466.manaitapp.dataComponent.helper.DataHelper;
import com.takoy3466.manaitapp.dataComponent.helper.ManaitaDataComponents;
import com.takoy3466.manaitapp.init.CompatData;
import com.takoy3466.manaitapp.util.ManaitaUnsafeUtil;
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
                        .component(CompatData.FLY_DATA.get(), new ManaitaFlyData(ManaitaFlyData.FlySpeed.DEFAULT))
                        .component(CompatData.INVINCIBLE_DATA.get(), new InvincibleData(true))
        );
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (level.isClientSide()) {
            return;
        }
        if (entity instanceof Player player) {
            ManaitaUnsafeUtil.applyAbsoluteShield(player);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        tooltipComponents.add(DataHelper.hoverDataText(stack, CompatData.FLY_DATA.get(), data -> ": " + data.getMsg().getComponent().getString()));
        tooltipComponents.add(DataHelper.hoverDataText(stack, CompatData.INVINCIBLE_DATA.get(), data -> ": " + (data.getMsg() ? ManaitaDataComponents.TRUE_TEXT : ManaitaDataComponents.FALSE_TEXT).getString()));
    }
}
