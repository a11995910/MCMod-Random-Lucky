package com.randomlucky.mod.common.recipe;

import com.google.gson.JsonObject;
import com.randomlucky.mod.common.registry.ModRecipes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class RandomItemRecipe implements CraftingRecipe {
    private final ResourceLocation id;
    private static final List<Item> POSSIBLE_ITEMS = createPossibleItems();

    public RandomItemRecipe(ResourceLocation id) {
        this.id = id;
    }

    private static List<Item> createPossibleItems() {
        List<Item> items = new ArrayList<>();
        // 添加各种可能的物品
        items.add(Items.DIAMOND);
        items.add(Items.EMERALD);
        items.add(Items.GOLD_INGOT);
        items.add(Items.IRON_INGOT);
        items.add(Items.REDSTONE);
        items.add(Items.LAPIS_LAZULI);
        items.add(Items.COAL);
        items.add(Items.COPPER_INGOT);
        items.add(Items.ENDER_PEARL);
        items.add(Items.BLAZE_ROD);
        items.add(Items.GLOWSTONE_DUST);
        items.add(Items.GUNPOWDER);
        items.add(Items.SLIME_BALL);
        items.add(Items.STRING);
        items.add(Items.BONE);
        items.add(Items.LEATHER);
        items.add(Items.FEATHER);
        items.add(Items.SPIDER_EYE);
        items.add(Items.ROTTEN_FLESH);
        items.add(Items.BREAD);
        items.add(Items.APPLE);
        items.add(Items.WHEAT);
        items.add(Items.WHEAT_SEEDS);
        items.add(Items.CARROT);
        items.add(Items.POTATO);
        items.add(Items.BEETROOT);
        items.add(Items.SUGAR);
        items.add(Items.EGG);
        items.add(Items.MILK_BUCKET);
        items.add(Items.WATER_BUCKET);
        items.add(Items.LAVA_BUCKET);
        return items;
    }

    @Override
    public boolean matches(CraftingContainer input, Level level) {
        // 检查是否为3x3网格，并且所有9个位置都是木棍
        if (input.getWidth() != 3 || input.getHeight() != 3) {
            return false;
        }
        
        for (int i = 0; i < 9; i++) {
            ItemStack stack = input.getItem(i);
            if (stack.isEmpty() || stack.getItem() != Items.STICK) {
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
        
        // 随机选择一个物品
        RandomSource random = RandomSource.create();
        Item randomItem = POSSIBLE_ITEMS.get(random.nextInt(POSSIBLE_ITEMS.size()));
        return new ItemStack(randomItem, 1);
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= 3 && height >= 3;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return new ItemStack(Items.DIAMOND); // 显示钻石作为示例结果
    }

    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.RANDOM_ITEM_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeType.CRAFTING;
    }

    @Override
    public CraftingBookCategory category() {
        return CraftingBookCategory.MISC;
    }

    public static class Serializer implements RecipeSerializer<RandomItemRecipe> {
        @Override
        public RandomItemRecipe fromJson(ResourceLocation id, JsonObject jsonObject) {
            return new RandomItemRecipe(id);
        }

        @Override
        public RandomItemRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            return new RandomItemRecipe(id);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, RandomItemRecipe recipe) {
            // 不需要写入任何数据，因为配方很简单
        }
    }
}