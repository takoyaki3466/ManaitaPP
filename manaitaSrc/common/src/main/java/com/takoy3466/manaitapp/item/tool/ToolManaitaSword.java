package com.takoy3466.manaitapp.item.tool;

import com.takoy3466.manaitapp.dataComponent.LightningStrikerData;
import com.takoy3466.manaitapp.dataComponent.helper.DataHelper;
import com.takoy3466.manaitapp.init.CompatData;
import com.takoy3466.manaitapp.util.WeaponUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;

import java.util.List;

public class ToolManaitaSword extends AbstractManaitaToolItem {
    public static final Component KILL_ALL = Component.translatable("gui.overlay.sword.all_die");
    public static final Component ONLY_ENEMY = Component.translatable("gui.overlay.sword.enemy_die");

    public ToolManaitaSword() {
        super(BlockTags.SWORD_EFFICIENT, new Item.Properties().fireResistant()
                .component(CompatData.STRIKER_DATA.get(), new LightningStrikerData(false))
                .rarity(Rarity.EPIC)
        );
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!(attacker instanceof Player player)) {
            return super.hurtEnemy(stack, target, attacker);
        }
        if (player.level().isClientSide()) {
            return false;
        }
        if (target instanceof LivingEntity livingEntity) {
            WeaponUtil.kill(livingEntity, player);
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        tooltipComponents.add(DataHelper.hoverDataText(stack, CompatData.STRIKER_DATA.get(), data -> ": " + (data.getMsg() ? KILL_ALL : ONLY_ENEMY).getString()));
    }
}
