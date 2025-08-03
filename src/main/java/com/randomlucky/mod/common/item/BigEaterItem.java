package com.randomlucky.mod.common.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.TooltipFlag;
import java.util.List;

/**
 * 大胃王物品类
 * 食用后可以永久增加1格饱食度上限
 * 每个玩家可以多次食用，没有上限限制
 */
public class BigEaterItem extends Item {
    
    // 每次食用增加的饱食度上限
    private static final int HUNGER_INCREASE = 1;
    // NBT标签键名，用于存储玩家额外的饱食度上限
    private static final String EXTRA_HUNGER_TAG = "ExtraHungerLimit";
    
    /**
     * 构造函数
     * 设置物品属性，包括食物属性
     */
    public BigEaterItem() {
        super(new Item.Properties()
                .food(new FoodProperties.Builder()
                        .nutrition(4) // 恢复4点饱食度
                        .saturationMod(0.6f) // 饱和度修饰符
                        .build())
                .stacksTo(64)); // 最大堆叠数量
    }
    
    /**
     * 当物品被食用完成时调用
     * 增加玩家的饱食度上限
     * 
     * @param stack 物品堆叠
     * @param level 世界
     * @param livingEntity 食用者
     * @return 食用后的物品堆叠
     */
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        // 调用父类方法处理基本的食用逻辑
        ItemStack result = super.finishUsingItem(stack, level, livingEntity);
        
        // 只在服务端处理，且确保是玩家
        if (!level.isClientSide && livingEntity instanceof Player player) {
            // 增加玩家的饱食度上限
            increasePlayerHungerLimit(player);
        }
        
        return result;
    }
    
    /**
     * 增加玩家的饱食度上限
     * 通过修改玩家的NBT数据来实现
     * 
     * @param player 玩家
     */
    private void increasePlayerHungerLimit(Player player) {
        // 获取玩家的持久化数据
        CompoundTag playerData = player.getPersistentData();
        
        // 获取当前额外的饱食度上限
        int currentExtraHunger = playerData.getInt(EXTRA_HUNGER_TAG);
        
        // 增加饱食度上限
        int newExtraHunger = currentExtraHunger + HUNGER_INCREASE;
        playerData.putInt(EXTRA_HUNGER_TAG, newExtraHunger);
        
        // 发送消息给玩家
        Component message = Component.literal("饱食度上限增加了 " + HUNGER_INCREASE + " 格！")
                .withStyle(ChatFormatting.GREEN);
        player.sendSystemMessage(message);
        
        Component totalMessage = Component.literal("当前额外饱食度上限：" + newExtraHunger + " 格")
                .withStyle(ChatFormatting.YELLOW);
        player.sendSystemMessage(totalMessage);
    }
    
    /**
     * 获取玩家的额外饱食度上限
     * 静态方法，供其他类调用
     * 
     * @param player 玩家
     * @return 额外的饱食度上限
     */
    public static int getExtraHungerLimit(Player player) {
        return player.getPersistentData().getInt(EXTRA_HUNGER_TAG);
    }
    
    /**
     * 添加物品提示信息
     * 显示物品的功能说明
     * 
     * @param stack 物品堆叠
     * @param level 世界
     * @param tooltip 提示信息列表
     * @param flag 提示标志
     */
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        
        // 添加功能说明
        tooltip.add(Component.literal("食用后永久增加1格饱食度上限")
                .withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.literal("可以多次食用，没有上限限制")
                .withStyle(ChatFormatting.AQUA));
        tooltip.add(Component.literal("合成：腐肉 + 小麦种子 + 木棍")
                .withStyle(ChatFormatting.GRAY));
    }
}