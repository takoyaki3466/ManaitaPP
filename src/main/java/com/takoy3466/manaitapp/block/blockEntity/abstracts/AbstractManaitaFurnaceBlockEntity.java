package com.takoy3466.manaitapp.block.blockEntity.abstracts;

import com.takoy3466.manaitapp.core.ManaitaTier;
import com.takoy3466.manaitapp.core.interfaces.IMultiple;
import com.takoy3466.manaitapp.core.interfaces.ITickableBlockEntity;
import com.takoy3466.manaitapp.menu.ManaitaFurnaceMenu;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeCraftingHolder;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractManaitaFurnaceBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, RecipeCraftingHolder, StackedContentsCompatible, IMultiple, ITickableBlockEntity {
    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_DOWN = new int[]{2, 1};
    private static final int[] SLOTS_FOR_SIDES = new int[]{1};
    private final RecipeType<? extends AbstractCookingRecipe> recipeType;
    protected NonNullList<ItemStack> items;
    int litTime;
    int litDuration;
    int cookingProgress;
    int cookingTotalTime;
    public final ContainerData dataAccess;
    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed;
    private final RecipeManager.CachedCheck<SingleRecipeInput, ? extends AbstractCookingRecipe> quickCheck;

    private final ManaitaTier manaitaTier;
    public AbstractManaitaFurnaceBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState, ManaitaTier manaitaTier) {
        super(type, pos, blockState);
        this.items = NonNullList.withSize(3, ItemStack.EMPTY);
        this.manaitaTier = manaitaTier;
        this.dataAccess = new ContainerData() {
            public int get(int i) {
                return switch (i) {
                    case 0 -> {
                        if (AbstractManaitaFurnaceBlockEntity.this.litDuration > 32767) {
                            yield Mth.floor((double) AbstractManaitaFurnaceBlockEntity.this.litTime / (double) AbstractManaitaFurnaceBlockEntity.this.litDuration * (double) 32767.0F);
                        }

                        yield AbstractManaitaFurnaceBlockEntity.this.litTime;
                    }
                    case 1 -> Math.min(AbstractManaitaFurnaceBlockEntity.this.litDuration, 32767);
                    case 2 -> AbstractManaitaFurnaceBlockEntity.this.cookingProgress;
                    case 3 -> AbstractManaitaFurnaceBlockEntity.this.cookingTotalTime;
                    default -> 0;
                };
            }

            public void set(int i, int i1) {
                switch (i) {
                    case 0 -> AbstractManaitaFurnaceBlockEntity.this.litTime = i1;
                    case 1 -> AbstractManaitaFurnaceBlockEntity.this.litDuration = i1;
                    case 2 -> AbstractManaitaFurnaceBlockEntity.this.cookingProgress = i1;
                    case 3 -> AbstractManaitaFurnaceBlockEntity.this.cookingTotalTime = i1;
                }

            }

            public int getCount() {
                return 4;
            }
        };

        this.recipesUsed = new Object2IntOpenHashMap<>();
        this.recipeType = RecipeType.SMELTING;
        this.quickCheck = RecipeManager.createCheck(recipeType);

    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.literal("test");
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory) {
        return new ManaitaFurnaceMenu(id, inventory, getBlockPos());
    }

    @Override
    public void serverTick() {
        BlockPos pos = getBlockPos();
        BlockState state = getBlockState();
        Level level = getLevel();
        if (level == null) {
            return;

        }
        boolean flag = isLit();
        boolean flag1 = false;
        if (isLit()) {
            --litTime;
        }

        ItemStack stack = items.get(1);
        ItemStack stack1 = items.get(0);
        boolean flag2 = !stack1.isEmpty();
        boolean flag3 = !stack.isEmpty();
        if (isLit() || flag3 && flag2) {
            RecipeHolder<?> recipeholder;
            if (flag2) {

                // レシピ取得
                recipeholder = quickCheck.getRecipeFor(new SingleRecipeInput(stack1), level).orElse(null);
            } else {
                recipeholder = null;
            }

            int i = getMaxStackSize();
            if (!isLit() && canBurn(level.registryAccess(), recipeholder, items, i)) {
                litTime = getBurnDuration(stack);
                litDuration = litTime;
                if (isLit()) {
                    flag1 = true;
                    if (stack.hasCraftingRemainingItem()) {
                        items.set(1, stack.getCraftingRemainingItem());
                    } else if (flag3) {
                        stack.shrink(1);
                        if (stack.isEmpty()) {
                            items.set(1, stack.getCraftingRemainingItem());
                        }
                    }
                }
            }

            if (isLit() && canBurn(level.registryAccess(), recipeholder, items, i)) {
                ++cookingProgress;
                if (cookingProgress == cookingTotalTime) {
                    cookingProgress = 0;
                    cookingTotalTime = getTotalCookTime();
                    if (burn(level.registryAccess(), recipeholder, items, i)) {
                        setRecipeUsed(recipeholder);
                    }

                    flag1 = true;
                }
            } else {
                cookingProgress = 0;
            }
        } else if (!isLit() && cookingProgress > 0) {
            cookingProgress = Mth.clamp(cookingProgress - 2, 0, cookingTotalTime);
        }

        if (flag != isLit()) {
            flag1 = true;
            state = state.setValue(AbstractFurnaceBlock.LIT, isLit());
            level.setBlock(pos, state, 3);
        }

        if (flag1) {
            setChanged();
        }
    }

    private boolean isLit() {
        return this.litTime > 0;
    }

    private boolean canBurn(RegistryAccess registryAccess, @javax.annotation.Nullable RecipeHolder<?> recipe, NonNullList<ItemStack> inventory, int maxStackSize) {
        if (!inventory.get(0).isEmpty() && recipe != null) {
            ItemStack assemble = ((AbstractCookingRecipe)recipe.value()).assemble(new SingleRecipeInput(getItem(0)), registryAccess);
            if (assemble.isEmpty()) {
                return false;
            } else {
                ItemStack stack = inventory.get(2);
                if (stack.isEmpty()) {
                    return true;
                } else if (!ItemStack.isSameItemSameComponents(stack, assemble)) {
                    return false;
                } else {
                    return stack.getCount() + assemble.getCount() <= maxStackSize && stack.getCount() + assemble.getCount() <= stack.getMaxStackSize() || stack.getCount() + assemble.getCount() <= assemble.getMaxStackSize();
                }
            }
        } else {
            return false;
        }
    }

    private boolean burn(RegistryAccess registryAccess, @javax.annotation.Nullable RecipeHolder<?> recipe, NonNullList<ItemStack> inventory, int maxStackSize) {
        if (recipe != null && canBurn(registryAccess, recipe, inventory, maxStackSize)) {
            ItemStack itemstack = inventory.get(0);
            ItemStack assemble = ((AbstractCookingRecipe)recipe.value()).assemble(new SingleRecipeInput(getItem(0)), registryAccess);

            multipler(assemble);

            ItemStack stack = inventory.get(2);
            if (stack.isEmpty()) {
                inventory.set(2, assemble.copy());
            } else if (ItemStack.isSameItemSameComponents(stack, assemble)) {
                stack.grow(assemble.getCount());
            }

            if (itemstack.is(Blocks.WET_SPONGE.asItem()) && !inventory.get(1).isEmpty() && inventory.get(1).is(Items.BUCKET)) {
                inventory.set(1, new ItemStack(Items.WATER_BUCKET));
            }

            itemstack.shrink(1);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int @NotNull [] getSlotsForFace(@NotNull Direction side) {
        if (side == Direction.DOWN) {
            return SLOTS_FOR_DOWN;
        } else {
            return side == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES;
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, @NotNull ItemStack itemStack, @javax.annotation.Nullable Direction direction) {
        return this.canPlaceItem(index, itemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, @NotNull ItemStack stack, @NotNull Direction direction) {
        return direction != Direction.DOWN || index != 1 || stack.is(Items.WATER_BUCKET) || stack.is(Items.BUCKET);
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    protected @NotNull NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(@NotNull NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        ItemStack itemstack = (ItemStack)this.items.get(index);
        boolean flag = !stack.isEmpty() && ItemStack.isSameItemSameComponents(itemstack, stack);
        this.items.set(index, stack);
        stack.limitSize(this.getMaxStackSize(stack));
        if (index == 0 && !flag) {
            this.cookingTotalTime = getTotalCookTime();
            this.cookingProgress = 0;
            this.setChanged();
        }

    }

    @Override
    public boolean canPlaceItem(int index, @NotNull ItemStack stack) {
        if (index == 2) {
            return false;
        } else if (index != 1) {
            return true;
        } else {
            ItemStack itemstack = this.items.get(1);
            return stack.getBurnTime(this.recipeType) > 0 || stack.is(Items.BUCKET) && !itemstack.is(Items.BUCKET);
        }
    }

    @Override
    public void setRecipeUsed(@javax.annotation.Nullable RecipeHolder<?> recipe) {
        if (recipe != null) {
            ResourceLocation resourcelocation = recipe.id();
            this.recipesUsed.addTo(resourcelocation, 1);
        }

    }

    @Nullable
    @Override
    public RecipeHolder<?> getRecipeUsed() {
        return null;
    }

    protected int getBurnDuration(ItemStack fuel) {
        return fuel.isEmpty() ? 0 : fuel.getBurnTime(this.recipeType);
    }

    private int getTotalCookTime() {
        return 1;
    }

    public static boolean isFuel(ItemStack stack) {
        return stack.getBurnTime(null) > 0;
    }

    @Override
    public void fillStackedContents(@NotNull StackedContents helper) {
        for(ItemStack itemstack : this.items) {
            helper.accountStack(itemstack);
        }

    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("BurnTime", this.litTime);
        tag.putInt("CookTime", this.cookingProgress);
        tag.putInt("CookTimeTotal", this.cookingTotalTime);
        ContainerHelper.saveAllItems(tag, this.items, registries);
        CompoundTag compoundtag = new CompoundTag();
        this.recipesUsed.forEach((resourceLocation, integer) -> compoundtag.putInt(resourceLocation.toString(), integer));
        tag.put("RecipesUsed", compoundtag);
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, this.items, registries);
        this.litTime = tag.getInt("BurnTime");
        this.cookingProgress = tag.getInt("CookTime");
        this.cookingTotalTime = tag.getInt("CookTimeTotal");
        this.litDuration = this.getBurnDuration(this.items.get(1));
        CompoundTag compoundtag = tag.getCompound("RecipesUsed");

        for(String s : compoundtag.getAllKeys()) {
            this.recipesUsed.put(ResourceLocation.parse(s), compoundtag.getInt(s));
        }
    }

    @Override
    public ManaitaTier getManaitaTier() {
        return manaitaTier;
    }

    @Override
    public int getMultiple() {
        return manaitaTier.getMultiple();
    }
}
