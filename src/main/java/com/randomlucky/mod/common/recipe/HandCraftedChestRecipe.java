package com.randomlucky.mod.common.recipe;

import com.google.gson.JsonObject;
import com.randomlucky.mod.common.registry.ModItems;
import com.randomlucky.mod.common.registry.ModRecipes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class HandCraftedChestRecipe implements CraftingRecipe {
    private final ResourceLocation id;

    public HandCraftedChestRecipe(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public boolean matches(CraftingContainer input, Level level) {
        // 检查是否为3x3网格中的水平一行（第一行：位置0,1,2）
        if (input.getWidth() != 3 || input.getHeight() != 3) {
            return false;
        }

        // 检查第二行和第三行是否为空
        for (int i = 3; i < 9; i++) {
            if (!input.getItem(i).isEmpty()) return false; // 第二行和第三行
        }

        // 检查第一行的三个物品是否都是箱子
        for (int i = 0; i < 3; i++) {
            ItemStack stack = input.getItem(i);
            if (stack.isEmpty() || stack.getItem() != Items.CHEST) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack assemble(CraftingContainer input, RegistryAccess registryAccess) {
        if (!matches(input, null)) {
            return ItemStack.EMPTY;
        }

        return new ItemStack(ModItems.HAND_CRAFTED_CHEST.get(), 1);
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= 3 && height >= 3;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return new ItemStack(ModItems.HAND_CRAFTED_CHEST.get());
    }

    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.HAND_CRAFTED_CHEST_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeType.CRAFTING;
    }

    @Override
    public CraftingBookCategory category() {
        return CraftingBookCategory.MISC;
    }

    public static class Serializer implements RecipeSerializer<HandCraftedChestRecipe> {
        @Override
        public HandCraftedChestRecipe fromJson(ResourceLocation id, JsonObject jsonObject) {
            return new HandCraftedChestRecipe(id);
        }

        @Override
        public HandCraftedChestRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            return new HandCraftedChestRecipe(id);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, HandCraftedChestRecipe recipe) {
            // 不需要写入任何数据，因为配方很简单
        }
    }
}