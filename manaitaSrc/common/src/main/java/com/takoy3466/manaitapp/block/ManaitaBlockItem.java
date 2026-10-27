package com.takoy3466.manaitapp.block;

import com.takoy3466.manaitapp.core.interfaces.IHasDisplayComponent;
import com.takoy3466.manaitapp.core.interfaces.IHasMenuProvider;
import com.takoy3466.manaitapp.core.interfaces.IMultiple;
import com.takoy3466.manaitapp.menu.MenuManaitaCrafting;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class ManaitaBlockItem extends BlockItem implements IHasMenuProvider<MenuManaitaCrafting> {
    public ManaitaBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (!(getBlock() instanceof IHasDisplayComponent displayBlock)) {
            return InteractionResultHolder.pass(player.getItemInHand(usedHand));
        }
        return this.openMenu(level, player, displayBlock.getDisplayComponent(), usedHand);
    }

    @Override
    public MenuManaitaCrafting setMenu(int id, Inventory inv, Player player, ItemStack stack) {
        if (!(getBlock() instanceof IMultiple iMultiple)) {
            return null;
        }
        return new MenuManaitaCrafting(id, inv, createAccess(player), iMultiple.getManaitaTier(), getBlock());
    }


}
