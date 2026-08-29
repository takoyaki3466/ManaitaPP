package com.takoy3466.manaitapp.item;

import com.takoy3466.manaitapp.core.platform.Services;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ManaitaBow extends BowItem {
    public ManaitaBow() {
        super(new Properties().fireResistant().rarity(Rarity.UNCOMMON));
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player player) {
            ItemStack itemstack = new ItemStack(Items.ARROW);
            if (!itemstack.isEmpty()) {
                int i = this.getUseDuration(stack, entityLiving) - timeLeft;
                i = Services.UTIL.onArrowLoose(stack, level, player, i, !itemstack.isEmpty());
                if (i < 0) {
                    return;
                }

                float f = 1.0f;
                List<ItemStack> list = this.drawOriginal(stack, itemstack, player);
                if (level instanceof ServerLevel serverlevel) {
                    if (!list.isEmpty()) {
                        this.shoot(serverlevel, player, player.getUsedItemHand(), stack, list, f * 3.0F, 1.0F, true, null);
                    }
                }

                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                player.awardStat(Stats.ITEM_USED.get(this));
            }
        }

    }

    protected List<ItemStack> drawOriginal(ItemStack weapon, ItemStack ammo, LivingEntity shooter) {
        if (ammo.isEmpty()) {
            return List.of();
        } else {
            Level level = shooter.level();
            int projectileCount;
            if (level instanceof ServerLevel serverlevel) {
                projectileCount = EnchantmentHelper.processProjectileCount(serverlevel, weapon, shooter, 1);
            } else {
                projectileCount = 1;
            }

            int i = projectileCount;
            List<ItemStack> list = new ArrayList<>(i);

            for(int j = 0; j < i; ++j) {
                ItemStack itemstack = ammo.copy();
                if (!itemstack.isEmpty()) {
                    list.add(itemstack);
                }
            }

            return list;
        }
    }

    @Override
    protected @NotNull Projectile createProjectile(Level level, LivingEntity shooter, ItemStack weapon, ItemStack ammo, boolean isCrit) {
        Projectile projectile = super.createProjectile(level, shooter, weapon, ammo, isCrit);
        if (projectile instanceof AbstractArrow abstractArrow) {
            abstractArrow.pickup = AbstractArrow.Pickup.DISALLOWED;
        }

        return projectile;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 720;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public int getDefaultProjectileRange() {
        return 256;
    }
}
