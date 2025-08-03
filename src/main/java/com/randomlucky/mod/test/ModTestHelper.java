package com.randomlucky.mod.test;

import com.randomlucky.mod.common.item.EnhancementUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ModTestHelper {
    
    /**
     * 测试强化系统
     */
    public static void testEnhancementSystem() {
        // 创建测试物品
        ItemStack sword = new ItemStack(Items.DIAMOND_SWORD);
        
        // 测试是否可以强化
        boolean canEnhance = EnhancementUtils.canEnhance(sword);
        System.out.println("钻石剑可以强化: " + canEnhance);
        
        // 强化物品
        if (canEnhance) {
            ItemStack enhancedSword = EnhancementUtils.enhanceItem(sword);
            int level = EnhancementUtils.getEnhancementLevel(enhancedSword);
            double multiplier = EnhancementUtils.getEnhancementMultiplier(enhancedSword);
            
            System.out.println("强化等级: " + level);
            System.out.println("强化倍数: " + multiplier);
        }
    }
    
    /**
     * 测试血量面包功能
     */
    public static void testHealthBread() {
        ItemStack healthBread = new ItemStack(com.randomlucky.mod.common.registry.ModItems.HEALTH_BREAD.get());
        System.out.println("血量面包创建成功: " + !healthBread.isEmpty());
    }
}