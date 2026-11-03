package com.takoy3466.manaitapp.menu;

import com.takoy3466.manaitapp.block.blockEntity.abstracts.AbstractManaitaFurnaceBlockEntity;
import com.takoy3466.manaitapp.core.platform.Services;
import com.takoy3466.manaitapp.core.slot.ManaitaFurnaceFuelSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.FuelValues;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MenuManaitaFurnace extends RecipeBookMenu {
    private final ContainerData containerData;
    private final Level level;
    private final RecipeType<? extends AbstractCookingRecipe> recipeType;
    private final RecipeBookType recipeBookType;

    public final AbstractManaitaFurnaceBlockEntity blockEntity;

    public MenuManaitaFurnace(int id, Inventory playerInventory, FriendlyByteBuf buf) {
        this(id, playerInventory, buf.readBlockPos());
    }

    public MenuManaitaFurnace(int id, Inventory playerInventory, BlockPos pos) {
        super(MenuType.FURNACE, id);
        this.recipeType = RecipeType.SMELTING;
        this.recipeBookType = RecipeBookType.FURNACE;
        this.level = playerInventory.player.level();

        BlockEntity be = this.level.getBlockEntity(pos);
        if (be instanceof AbstractManaitaFurnaceBlockEntity furnaceBlockEntity) {
            this.blockEntity = furnaceBlockEntity;
        } else {
            throw new IllegalStateException(Objects.requireNonNull(be).getClass().getCanonicalName() + "と ManaitaFurnaceBlockEntity クラスは違うよ！");
        }

        this.containerData = blockEntity.dataAccess;

        this.addSlot(new Slot(blockEntity, 0, 56, 17));
        this.addSlot(new ManaitaFurnaceFuelSlot(this, blockEntity, 1, 56, 53));
        this.addSlot(new FurnaceResultSlot(playerInventory.player, blockEntity, 2, 116, 35));

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }

        this.addDataSlots(containerData);
    }

    public MenuManaitaFurnace(int id, Inventory playerInventory, AbstractManaitaFurnaceBlockEntity blockEntity) {
        super(MenuType.FURNACE, id);

        this.recipeType = RecipeType.SMELTING;
        this.recipeBookType = RecipeBookType.FURNACE;
        this.level = playerInventory.player.level();

        this.blockEntity = blockEntity;
        this.containerData = blockEntity.dataAccess;

        this.addSlot(new Slot(blockEntity, 0, 56, 17));
        this.addSlot(new ManaitaFurnaceFuelSlot(this, blockEntity, 1, 56, 53));
        this.addSlot(new FurnaceResultSlot(playerInventory.player, blockEntity, 2, 116, 35));

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }

        this.addDataSlots(containerData);
    }

    public boolean isFuel(ItemStack stack) {
        return level.fuelValues().isFuel(stack);
    }

    @Override
    public void fillCraftSlotsStackedContents(@NotNull StackedItemContents contents) {
        if (this.blockEntity != null) {
            this.blockEntity.fillStackedContents(contents);
        }
    }

    @Override
    public @NotNull PostPlaceAction handlePlacement(boolean useMaxItems, boolean allowDroppingItemsToClear, RecipeHolder<?> recipe, ServerLevel serverLevel, Inventory inventory) {
        return PostPlaceAction.NOTHING;
    }

    public void clearCraftingContent() {
        this.getSlot(0).set(ItemStack.EMPTY);
        this.getSlot(2).set(ItemStack.EMPTY);
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
        if (!(level instanceof ServerLevel serverLevel)) {
            return false;
        }
        return serverLevel.getServer().getRecipeManager().getRecipeFor(this.recipeType, new SingleRecipeInput(stack), this.level).isPresent();
    }

    public float getBurnProgress() {
        int i = this.containerData.get(2);
        int j = this.containerData.get(3);
        return j != 0 && i != 0 ? Mth.clamp((float) i / (float) j, 0.0F, 1.0F) : 0.0F;
    }

    public float getLitProgress() {
        int i = this.containerData.get(1);
        if (i == 0) {
            i = 200;
        }

        return Mth.clamp((float) this.containerData.get(0) / (float) i, 0.0F, 1.0F);
    }

    public boolean isLit() {
        return this.containerData.get(0) > 0;
    }

    public @NotNull RecipeBookType getRecipeBookType() {
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
