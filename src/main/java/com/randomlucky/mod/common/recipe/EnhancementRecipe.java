package com.randomlucky.mod.common.recipe;

import com.google.gson.JsonObject;
import com.randomlucky.mod.common.registry.ModRecipes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class EnhancementRecipe implements CraftingRecipe {
    private final ResourceLocation id;

    public EnhancementRecipe(ResourceLocation id) {
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

        // 检查第一行的三个物品
        ItemStack leftItem = input.getItem(0);
        ItemStack centerItem = input.getItem(1);
        ItemStack rightItem = input.getItem(2);

        // 必须所有三个位置都有物品
        if (leftItem.isEmpty() || centerItem.isEmpty() || rightItem.isEmpty()) {
            return false;
        }

        // 检查是否为相同的物品类型
        if (leftItem.getItem() != centerItem.getItem() || 
            leftItem.getItem() != rightItem.getItem()) {
            return false;
        }

        // 检查是否为相同的强化等级
        int leftLevel = com.randomlucky.mod.common.item.EnhancementUtils.getEnhancementLevel(leftItem);
        int centerLevel = com.randomlucky.mod.common.item.EnhancementUtils.getEnhancementLevel(centerItem);
        int rightLevel = com.randomlucky.mod.common.item.EnhancementUtils.getEnhancementLevel(rightItem);
        
        if (leftLevel != centerLevel || leftLevel != rightLevel) {
            return false;
        }

        // 检查是否是可强化的物品且未达到最大等级
        return isEnhanceable(centerItem) && 
               com.randomlucky.mod.common.item.EnhancementUtils.canEnhance(centerItem);
    }

    private boolean isEnhanceable(ItemStack stack) {
        return com.randomlucky.mod.common.item.EnhancementUtils.isEnhanceable(stack);
    }

    @Override
    public ItemStack assemble(CraftingContainer input, RegistryAccess registryAccess) {
        if (!matches(input, null)) {
            return ItemStack.EMPTY;
        }

        ItemStack centerItem = input.getItem(1).copy(); // 第一行中间位置
        
        // 使用强化工具进行强化
        return com.randomlucky.mod.common.item.EnhancementUtils.enhanceItem(centerItem);
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= 3 && height >= 3;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return ItemStack.EMPTY; // 动态结果
    }

    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.ENHANCEMENT_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeType.CRAFTING;
    }

    @Override
    public CraftingBookCategory category() {
        return CraftingBookCategory.EQUIPMENT;
    }

    public static class Serializer implements RecipeSerializer<EnhancementRecipe> {
        @Override
        public EnhancementRecipe fromJson(ResourceLocation id, JsonObject jsonObject) {
            return new EnhancementRecipe(id);
        }

        @Override
        public EnhancementRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            return new EnhancementRecipe(id);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, EnhancementRecipe recipe) {
            // 不需要写入任何数据，因为配方很简单
        }
    }
}