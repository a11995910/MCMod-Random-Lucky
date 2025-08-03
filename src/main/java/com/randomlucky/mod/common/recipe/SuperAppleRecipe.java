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

/**
 * 超级苹果合成配方
 * 合成方式：钻石为中心，周围8个苹果
 */
public class SuperAppleRecipe implements CraftingRecipe {
    private final ResourceLocation id;

    /**
     * 构造函数
     * @param id 配方ID
     */
    public SuperAppleRecipe(ResourceLocation id) {
        this.id = id;
    }

    /**
     * 检查配方是否匹配
     * @param input 合成输入容器
     * @param level 世界
     * @return 是否匹配
     */
    @Override
    public boolean matches(CraftingContainer input, Level level) {
        // 检查是否为3x3网格
        if (input.getWidth() != 3 || input.getHeight() != 3) {
            return false;
        }

        // 检查中心位置(1,1)是否为钻石
        ItemStack centerItem = input.getItem(4); // 3x3网格中心位置索引为4
        if (!centerItem.is(Items.DIAMOND)) {
            return false;
        }

        // 检查周围8个位置是否都是苹果
        int[] surroundingSlots = {0, 1, 2, 3, 5, 6, 7, 8}; // 除了中心位置4的其他位置
        
        for (int slot : surroundingSlots) {
            ItemStack item = input.getItem(slot);
            if (!item.is(Items.APPLE)) {
                return false;
            }
        }

        return true;
    }

    /**
     * 组装配方结果
     * @param input 合成输入容器
     * @param registryAccess 注册表访问器
     * @return 合成结果
     */
    @Override
    public ItemStack assemble(CraftingContainer input, RegistryAccess registryAccess) {
        if (!matches(input, null)) {
            return ItemStack.EMPTY;
        }

        // 返回一个超级苹果
        return new ItemStack(ModItems.SUPER_APPLE.get(), 1);
    }

    /**
     * 检查是否可以在指定尺寸的网格中合成
     * @param width 宽度
     * @param height 高度
     * @return 是否可以合成
     */
    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= 3 && height >= 3;
    }

    /**
     * 获取配方结果物品（用于显示）
     * @param registryAccess 注册表访问器
     * @return 结果物品
     */
    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return new ItemStack(ModItems.SUPER_APPLE.get(), 1);
    }

    /**
     * 获取配方ID
     * @return 配方ID
     */
    public ResourceLocation getId() {
        return id;
    }

    /**
     * 获取配方序列化器
     * @return 序列化器
     */
    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.SUPER_APPLE_SERIALIZER.get();
    }

    /**
     * 获取配方类型
     * @return 配方类型
     */
    @Override
    public RecipeType<?> getType() {
        return RecipeType.CRAFTING;
    }

    /**
     * 获取合成书分类
     * @return 分类
     */
    @Override
    public CraftingBookCategory category() {
        return CraftingBookCategory.MISC;
    }

    /**
     * 配方序列化器
     */
    public static class Serializer implements RecipeSerializer<SuperAppleRecipe> {
        
        /**
         * 从JSON反序列化配方
         * @param id 配方ID
         * @param jsonObject JSON对象
         * @return 配方实例
         */
        @Override
        public SuperAppleRecipe fromJson(ResourceLocation id, JsonObject jsonObject) {
            return new SuperAppleRecipe(id);
        }

        /**
         * 从网络数据反序列化配方
         * @param id 配方ID
         * @param buffer 数据缓冲区
         * @return 配方实例
         */
        @Override
        public SuperAppleRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            return new SuperAppleRecipe(id);
        }

        /**
         * 将配方序列化到网络数据
         * @param buffer 数据缓冲区
         * @param recipe 配方实例
         */
        @Override
        public void toNetwork(FriendlyByteBuf buffer, SuperAppleRecipe recipe) {
            // 超级苹果配方不需要额外的网络数据
        }
    }
}