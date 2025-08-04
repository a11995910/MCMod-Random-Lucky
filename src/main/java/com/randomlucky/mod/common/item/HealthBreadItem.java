package com.randomlucky.mod.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * 血量增强面包物品类
 * 食用后可以永久增加1颗心的血量上限
 * 使用NBT数据持久化存储，确保死亡后不会丢失
 */
public class HealthBreadItem extends Item {
    // NBT标签键名，用于存储玩家额外的血量上限
    private static final String EXTRA_HEALTH_TAG = "ExtraHealthLimit";
    // 每次食用增加的血量（1颗心 = 2点血量）
    private static final double HEALTH_INCREASE = 2.0;
    // 最大额外血量上限（400颗心 = 800点血量）
    private static final double MAX_EXTRA_HEALTH = 400.0 * 2.0;
    
    public HealthBreadItem(Properties properties) {
        super(properties.food(new FoodProperties.Builder()
                .nutrition(4)
                .saturationMod(0.6f)
                .build()));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        // 调用父类方法处理基本的食用逻辑
        ItemStack result = super.finishUsingItem(stack, level, entity);
        
        // 只在服务端处理，且确保是玩家
        if (!level.isClientSide && entity instanceof Player player) {
            // 增加玩家的血量上限
            increasePlayerHealthLimit(player);
        }
        
        return result;
    }

    /**
     * 增加玩家的血量上限
     * 通过修改玩家的NBT数据和MAX_HEALTH属性来实现
     * 
     * @param player 玩家
     */
    private void increasePlayerHealthLimit(Player player) {
        // 获取玩家的持久化数据
        CompoundTag playerData = player.getPersistentData();
        
        // 获取当前额外的血量上限
        double currentExtraHealth = playerData.getDouble(EXTRA_HEALTH_TAG);
        
        // 检查是否已达到最大值
        if (currentExtraHealth >= MAX_EXTRA_HEALTH) {
            player.sendSystemMessage(Component.literal("血量已达到最大值！")
                    .withStyle(ChatFormatting.RED));
            return;
        }
        
        // 增加血量上限
        double newExtraHealth = Math.min(currentExtraHealth + HEALTH_INCREASE, MAX_EXTRA_HEALTH);
        playerData.putDouble(EXTRA_HEALTH_TAG, newExtraHealth);
        
        // 同时更新玩家的MAX_HEALTH属性
        var healthAttribute = player.getAttribute(Attributes.MAX_HEALTH);
        if (healthAttribute != null) {
            double baseHealth = 20.0; // 基础血量
            healthAttribute.setBaseValue(baseHealth + newExtraHealth);
        }
        
        // 发送消息给玩家
        Component message = Component.literal("最大血量增加了1颗心！")
                .withStyle(ChatFormatting.GREEN);
        player.sendSystemMessage(message);
        
        int totalExtraHearts = (int) (newExtraHealth / 2.0);
        Component totalMessage = Component.literal("当前额外血量：" + totalExtraHearts + " 颗心")
                .withStyle(ChatFormatting.YELLOW);
        player.sendSystemMessage(totalMessage);
    }
    
    /**
     * 获取玩家的额外血量上限
     * 静态方法，供其他类调用
     * 
     * @param player 玩家
     * @return 额外的血量上限
     */
    public static double getExtraHealthLimit(Player player) {
        return player.getPersistentData().getDouble(EXTRA_HEALTH_TAG);
    }
    
    /**
     * 恢复玩家的血量上限
     * 用于玩家重生时恢复血量上限
     * 
     * @param player 玩家
     */
    public static void restorePlayerHealthLimit(Player player) {
        double extraHealth = getExtraHealthLimit(player);
        if (extraHealth > 0) {
            var healthAttribute = player.getAttribute(Attributes.MAX_HEALTH);
            if (healthAttribute != null) {
                double baseHealth = 20.0; // 基础血量
                healthAttribute.setBaseValue(baseHealth + extraHealth);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        
        // 添加功能说明
        tooltip.add(Component.literal("食用后永久增加1颗心的血量上限")
                .withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.literal("最多可增加400颗心")
                .withStyle(ChatFormatting.AQUA));
        tooltip.add(Component.literal("合成：钻石 + 8个面包")
                .withStyle(ChatFormatting.GRAY));
    }
}