package com.randomlucky.mod.common.registry;

import com.randomlucky.mod.RandomLuckyMod;
import com.randomlucky.mod.common.item.BigEaterItem;
import com.randomlucky.mod.common.item.HandCraftedChestItem;
import com.randomlucky.mod.common.item.HealthBreadItem;
import com.randomlucky.mod.common.item.SuperAppleItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = 
        DeferredRegister.create(ForgeRegistries.ITEMS, RandomLuckyMod.MOD_ID);

    // 血量增强面包
    public static final RegistryObject<Item> HEALTH_BREAD = ITEMS.register("health_bread",
        () -> new HealthBreadItem(new Item.Properties().stacksTo(64)));

    // 手作箱子
    public static final RegistryObject<Item> HAND_CRAFTED_CHEST = ITEMS.register("hand_crafted_chest",
        () -> new HandCraftedChestItem(new Item.Properties().stacksTo(1)));

    // 超级苹果
    public static final RegistryObject<Item> SUPER_APPLE = ITEMS.register("super_apple",
        () -> new SuperAppleItem(new Item.Properties().stacksTo(16)));

    // 大胃王
    public static final RegistryObject<Item> BIG_EATER = ITEMS.register("big_eater",
        () -> new BigEaterItem());

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}