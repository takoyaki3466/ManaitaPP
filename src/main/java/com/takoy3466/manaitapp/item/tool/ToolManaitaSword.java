package com.takoy3466.manaitapp.item.tool;

import com.takoy3466.manaitapp.dataComponent.LightningStrikerData;
import com.takoy3466.manaitapp.dataComponent.ManaitaKillData;
import com.takoy3466.manaitapp.dataComponent.helper.DataHelper;
import com.takoy3466.manaitapp.init.DataInit;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;

import java.util.List;

public class ToolManaitaSword extends SwordItem {
    public static final Component KILL_ALL = Component.translatable("gui.overlay.sword.all_die");
    public static final Component ONLY_ENEMY = Component.translatable("gui.overlay.sword.enemy_die");

    public ToolManaitaSword() {
        super(ManaitaToolTier.MANAITA_TIER, new Item.Properties().fireResistant()
                .component(DataInit.STRIKER_DATA, new LightningStrikerData(false))
                .component(DataInit.KILL_DATA, new ManaitaKillData(true))
                .rarity(Rarity.EPIC)
        );
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        tooltipComponents.add(DataHelper.hoverDataText(stack, DataInit.STRIKER_DATA.get(), data -> ": " + (data.getMsg() ? KILL_ALL : ONLY_ENEMY).getString()));
    }
}
