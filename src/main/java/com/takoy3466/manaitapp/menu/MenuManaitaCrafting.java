package com.takoy3466.manaitapp.menu;

import com.takoy3466.manaitapp.core.interfaces.IMultiple;
import com.takoy3466.manaitapp.core.ManaitaTier;
import com.takoy3466.manaitapp.util.CraftingUtil;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

public class MenuManaitaCrafting extends RecipeBookMenu<CraftingInput, CraftingRecipe> implements IMultiple {
    private final CraftingContainer craftSlots;
    private final ResultContainer resultSlots;
    private final ContainerLevelAccess access;
    private final Player player;
    private final ManaitaTier manaitaTier;
    private final Block block;

    public <T extends Block> MenuManaitaCrafting(int id, Inventory inv, ContainerLevelAccess access, ManaitaTier manaitaTier, T block) {
        super(MenuType.CRAFTING, id);
        this.block = block;
        this.craftSlots = new TransientCraftingContainer(this, 3, 3);
        this.resultSlots = new ResultContainer();
        this.access = access;
        this.player = inv.player;
        this.manaitaTier = manaitaTier;
        this.addSlot(new ResultSlot(player, this.craftSlots, this.resultSlots, 0, 124, 35));

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                this.addSlot(new Slot(this.craftSlots, j + i * 3, 30 + j * 18, 17 + i * 18));
            }
        }

        for(int k = 0; k < 3; ++k) {
            for(int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(inv, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }

        for(int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(inv, l, 8 + l * 18, 142));
        }
    }

    protected void slotChangedCraftingGrid(AbstractContainerMenu menu, Level level, Player player, CraftingInput input, ResultContainer resultContainer) {
        if (level == null || level.isClientSide() || !(player instanceof ServerPlayer serverPlayer)) {
            return;
        }
        ItemStack stack = ItemStack.EMPTY;
        Optional<RecipeHolder<CraftingRecipe>> optionalRecipe = Objects.requireNonNull(level.getServer()).getRecipeManager().getRecipeFor(RecipeType.CRAFTING, input, level);

        if (optionalRecipe.isPresent()) {
            RecipeHolder<CraftingRecipe> recipeHolder = optionalRecipe.get();

            if (resultContainer.setRecipeUsed(level, serverPlayer, recipeHolder)) {
                CraftingRecipe recipe = recipeHolder.value();
                ItemStack result = recipe.assemble(input, level.registryAccess());

                if (result.isItemEnabled(level.enabledFeatures())) {
                    stack = result;
                    multipler(stack);
                }
            }
        }

        resultContainer.setItem(0, stack);
        menu.setRemoteSlot(0, stack);
        serverPlayer.connection.send(new ClientboundContainerSetSlotPacket(menu.containerId, menu.incrementStateId(), 0, stack));
    }

    @Override
    public void slotsChanged(@NotNull Container container) {
        this.access.execute((level, pos) -> {
            if (this.multipleMatch((CraftingContainer) container)){
                setItem(0,1, this.multipleAssemble((CraftingContainer) container, getMultiple()));
                this.resultSlots.setChanged();
            } else {
                slotChangedCraftingGrid(this, level, this.player, this.craftSlots.asCraftInput(), this.resultSlots);
            }
        });
    }

    private boolean multipleMatch(CraftingContainer container) {
        return CraftingUtil.matches(container);
    }

    private ItemStack multipleAssemble(CraftingContainer container, int magnification) {
        return CraftingUtil.multipleAssemble(container.getItems(), magnification);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack empty = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            empty = stack.copy();
            if (index == 0) {
                this.access.execute((level, pos) -> stack.getItem().onCraftedBy(stack, level, player));
                if (!this.moveItemStackTo(stack, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(stack, empty);
            } else if (index >= 10 && index < 46) {
                if (!this.moveItemStackTo(stack, 1, 10, false)) {
                    if (index < 37) {
                        if (!this.moveItemStackTo(stack, 37, 46, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.moveItemStackTo(stack, 10, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(stack, 10, 46, false)) {
                return ItemStack.EMPTY;
            }

            if (stack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack.getCount() == empty.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stack);
            if (index == 0) {
                player.drop(stack, false);
            }
        }

        return empty;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return stillValid(access, player, block);
    }

    @Override
    public void fillCraftSlotsStackedContents(StackedContents itemHelper) {
        this.craftSlots.fillStackedContents(itemHelper);
    }

    @Override
    public void clearCraftingContent() {
        this.craftSlots.clearContent();
        this.resultSlots.clearContent();
    }

    @Override
    public void removed(@NotNull Player player) {
        super.removed(player);
        this.access.execute((level, pos) -> this.clearContainer(player, this.craftSlots));
    }

    @Override
    public boolean recipeMatches(RecipeHolder<CraftingRecipe> recipeHolder) {
        return recipeHolder.value().matches(this.craftSlots.asCraftInput(), this.player.level());
    }

    @Override
    public int getResultSlotIndex() {
        return 0;
    }

    @Override
    public int getGridWidth() {
        return this.craftSlots.getWidth();
    }

    @Override
    public int getGridHeight() {
        return this.craftSlots.getHeight();
    }

    @Override
    public int getSize() {
        return 10;
    }

    @Override
    public @NotNull RecipeBookType getRecipeBookType() {
        return RecipeBookType.CRAFTING;
    }

    public boolean shouldMoveToInventory(int slotIndex) {
        return slotIndex != this.getResultSlotIndex();
    }


    @Override
    public ManaitaTier getManaitaTier() {
        return manaitaTier;
    }

    @Override
    public int getMultiple() {
        if (manaitaTier == null) {
            return 1;
        }else {
            return manaitaTier.getMultiple();
        }

    }
}
