package com.takoy3466.manaitapp.menu;

import com.takoy3466.manaitapp.block.blockEntity.abstracts.AbstractManaitaFurnaceBlockEntity;
import com.takoy3466.manaitapp.core.platform.Services;
import com.takoy3466.manaitapp.core.slot.ManaitaFurnaceFuelSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ManaitaFurnaceMenu extends RecipeBookMenu<SingleRecipeInput, AbstractCookingRecipe> {
    private final ContainerData containerData;
    private final Level level;
    private final RecipeType<? extends AbstractCookingRecipe> recipeType;
    private final RecipeBookType recipeBookType;

    public final AbstractManaitaFurnaceBlockEntity blockEntity;

    public ManaitaFurnaceMenu(int id, Inventory playerInventory, FriendlyByteBuf buf) {
        this(id, playerInventory, buf.readBlockPos());
    }

    public ManaitaFurnaceMenu(int id, Inventory playerInventory, BlockPos pos) {
        super(MenuType.FURNACE, id);
        this.recipeType = RecipeType.SMELTING;
        this.recipeBookType = RecipeBookType.FURNACE;
        this.level = playerInventory.player.level();

        BlockEntity be = this.level.getBlockEntity(pos);
        if (be instanceof AbstractManaitaFurnaceBlockEntity furnaceBlockEntity){
            this.blockEntity = furnaceBlockEntity;
        } else {
            throw new IllegalStateException(Objects.requireNonNull(be).getClass().getCanonicalName() + "と ManaitaFurnaceBlockEntity クラスは違うよ！");
        }

        this.containerData = blockEntity.dataAccess;

        this.addSlot(new Slot(blockEntity, 0, 56, 17));
        this.addSlot(new ManaitaFurnaceFuelSlot(this, blockEntity, 1, 56, 53));
        this.addSlot(new FurnaceResultSlot(playerInventory.player, blockEntity, 2, 116, 35));

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }

        this.addDataSlots(containerData);
    }

    public boolean isFuel(ItemStack stack) {
        return Services.UTIL.getBurnTime(stack, this.recipeType) > 0;
    }

    @Override
    public void fillCraftSlotsStackedContents(@NotNull StackedContents stackedContents) {
        if (this.blockEntity != null) {
            this.blockEntity.fillStackedContents(stackedContents);
        }
    }

    public void clearCraftingContent() {
        this.getSlot(0).set(ItemStack.EMPTY);
        this.getSlot(2).set(ItemStack.EMPTY);
    }

    @Override
    public boolean recipeMatches(@NotNull RecipeHolder<AbstractCookingRecipe> recipeHolder) {
        return recipeHolder.value().matches(new SingleRecipeInput(this.blockEntity.getItem(0)), this.level);
    }

    public int getResultSlotIndex() {
        return 2;
    }

    public int getGridWidth() {
        return 1;
    }

    public int getGridHeight() {
        return 1;
    }

    public int getSize() {
        return 3;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemstack = stack.copy();
            if (index == 2) {
                if (!this.moveItemStackTo(stack, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(stack, itemstack);
            } else if (index != 1 && index != 0) {
                if (this.canSmelt(stack)) {
                    if (!this.moveItemStackTo(stack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (this.isFuel(stack)) {
                    if (!this.moveItemStackTo(stack, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 3 && index < 30) {
                    if (!this.moveItemStackTo(stack, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 30 && index < 39 && !this.moveItemStackTo(stack, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(stack, 3, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (stack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stack);
        }

        return itemstack;
    }

    protected boolean canSmelt(ItemStack stack) {
        return this.level.getRecipeManager().getRecipeFor(this.recipeType, new SingleRecipeInput(stack), this.level).isPresent();
    }

    public float getBurnProgress() {
        int i = this.containerData.get(2);
        int j = this.containerData.get(3);
        return j != 0 && i != 0 ? Mth.clamp((float)i / (float)j, 0.0F, 1.0F) : 0.0F;
    }

    public float getLitProgress() {
        int i = this.containerData.get(1);
        if (i == 0) {
            i = 200;
        }

        return Mth.clamp((float)this.containerData.get(0) / (float)i, 0.0F, 1.0F);
    }

    public boolean isLit() {
        return this.containerData.get(0) > 0;
    }

    public RecipeBookType getRecipeBookType() {
        return this.recipeBookType;
    }

    public boolean shouldMoveToInventory(int slotIndex) {
        return slotIndex != 1;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return this.blockEntity.stillValid(player);
    }
}
