package com.randomlucky.mod;

import com.randomlucky.mod.common.registry.ModItems;
import com.randomlucky.mod.common.registry.ModRecipes;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(RandomLuckyMod.MOD_ID)
public class RandomLuckyMod {
    public static final String MOD_ID = "randomlucky";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public RandomLuckyMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        // 注册物品和配方
        ModItems.register(modEventBus);
        ModRecipes.register(modEventBus);
        
        modEventBus.addListener(this::onCommonSetup);
        modEventBus.addListener(this::addCreative);
        
        // 注册到Forge事件总线
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void onCommonSetup(final FMLCommonSetupEvent event) {
        // 通用设置逻辑
    }

    @SubscribeEvent
    public void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(ModItems.HEALTH_BREAD);
            event.accept(ModItems.SUPER_APPLE);
        }
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(ModItems.HAND_CRAFTED_CHEST);
        }
    }
}