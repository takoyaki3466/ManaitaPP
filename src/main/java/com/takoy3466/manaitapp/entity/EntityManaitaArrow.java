package com.takoy3466.manaitapp.entity;

import com.takoy3466.manaitapp.util.WeaponUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.neoforge.entity.PartEntity;
import org.jetbrains.annotations.NotNull;

public class EntityManaitaArrow extends AbstractArrow {
    private int tick = 0;

    public EntityManaitaArrow(EntityType<? extends EntityManaitaArrow> entityType, Level level) {
        super(entityType, level);
        this.setBaseDamage(Double.MAX_VALUE);
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        if (level().isClientSide()) {
            return;
        }
        Entity target = result.getEntity();
        if (target instanceof PartEntity<?> part) {
            kill(part.getParent());
        }
        kill(target);
        super.onHitEntity(result);
    }

    public void kill(@NotNull Entity target) {
        WeaponUtil.manaitaKill(target);
    }

    @Override
    protected @NotNull ItemStack getDefaultPickupItem() {
        return null;
    }

    @Override
    public void tick() {
        super.tick();
        this.tick++;
        if (!level().isClientSide && this.tick > 20 * 10) {
            this.discard();
        }
    }
}
