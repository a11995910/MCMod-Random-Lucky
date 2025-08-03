package com.randomlucky.mod.common.registry;

import com.randomlucky.mod.RandomLuckyMod;
import com.randomlucky.mod.common.recipe.EnhancementRecipe;
import com.randomlucky.mod.common.recipe.HandCraftedChestRecipe;
import com.randomlucky.mod.common.recipe.RandomItemRecipe;
import com.randomlucky.mod.common.recipe.SuperAppleRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = 
        DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, RandomLuckyMod.MOD_ID);

    // 随机物品配方序列化器
    public static final RegistryObject<RecipeSerializer<RandomItemRecipe>> RANDOM_ITEM_SERIALIZER = 
        RECIPE_SERIALIZERS.register("random_item", RandomItemRecipe.Serializer::new);

    // 装备强化配方序列化器
    public static final RegistryObject<RecipeSerializer<EnhancementRecipe>> ENHANCEMENT_SERIALIZER = 
        RECIPE_SERIALIZERS.register("enhancement", EnhancementRecipe.Serializer::new);

    // 手作箱子配方序列化器
    public static final RegistryObject<RecipeSerializer<HandCraftedChestRecipe>> HAND_CRAFTED_CHEST_SERIALIZER = 
        RECIPE_SERIALIZERS.register("hand_crafted_chest", HandCraftedChestRecipe.Serializer::new);

    // 超级苹果配方序列化器
    public static final RegistryObject<RecipeSerializer<SuperAppleRecipe>> SUPER_APPLE_SERIALIZER = 
        RECIPE_SERIALIZERS.register("super_apple", SuperAppleRecipe.Serializer::new);

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
    }
}