package com.randomlucky.mod.common.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * 超级苹果物品类
 * 提供永久的夜视、抗火、急速效果
 * 防止多次食用叠加效果
 */
public class SuperAppleItem extends Item {
    // NBT标记，用于记录玩家是否已经食用过超级苹果
    private static final String SUPER_APPLE_TAG = "RandomLuckySuperApple";
    private static final String HAS_EFFECTS_TAG = "HasSuperAppleEffects";
    
    /**
     * 构造函数
     * @param properties 物品属性
     */
    public SuperAppleItem(Properties properties) {
        super(properties.food(new FoodProperties.Builder()
                .nutrition(6)  // 营养值比普通苹果高
                .saturationMod(1.2f)  // 饱和度
                .alwaysEat()  // 即使饱腹也能食用
                .build()));
    }

    /**
     * 物品使用完成时的处理
     * @param stack 物品堆
     * @param level 世界
     * @param entity 使用实体
     * @return 使用后的物品堆
     */
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide && entity instanceof Player player) {
            // 检查玩家是否已经有超级苹果效果
            if (hasPlayerEatenSuperApple(player)) {
                player.sendSystemMessage(Component.literal("§c你已经拥有超级苹果的力量了！"));
                return stack; // 不消耗物品
            }
            
            // 给予永久效果
            givePlayerPermanentEffects(player);
            
            // 标记玩家已经食用过超级苹果
            markPlayerAsEaten(player);
            
            player.sendSystemMessage(Component.literal("§6你获得了超级苹果的永久力量！"));
            player.sendSystemMessage(Component.literal("§a夜视、抗火、急速效果已永久激活！"));
        }
        
        return super.finishUsingItem(stack, level, entity);
    }

    /**
     * 检查玩家是否已经食用过超级苹果
     * @param player 玩家
     * @return 是否已经食用过
     */
    private boolean hasPlayerEatenSuperApple(Player player) {
        CompoundTag persistentData = player.getPersistentData();
        CompoundTag modData = persistentData.getCompound(SUPER_APPLE_TAG);
        return modData.getBoolean(HAS_EFFECTS_TAG);
    }

    /**
     * 标记玩家已经食用过超级苹果
     * @param player 玩家
     */
    private void markPlayerAsEaten(Player player) {
        CompoundTag persistentData = player.getPersistentData();
        CompoundTag modData = new CompoundTag();
        modData.putBoolean(HAS_EFFECTS_TAG, true);
        persistentData.put(SUPER_APPLE_TAG, modData);
    }

    /**
     * 给予玩家永久效果
     * @param player 玩家
     */
    private void givePlayerPermanentEffects(Player player) {
        // 夜视效果 (无限时长)
        MobEffectInstance nightVision = new MobEffectInstance(
            MobEffects.NIGHT_VISION, 
            Integer.MAX_VALUE,  // 无限时长
            0,  // 等级0 (实际是1级)
            false,  // 不显示粒子
            false,  // 不显示图标
            true   // 环境效果
        );
        
        // 抗火效果 (无限时长)
        MobEffectInstance fireResistance = new MobEffectInstance(
            MobEffects.FIRE_RESISTANCE,
            Integer.MAX_VALUE,
            0,
            false,
            false,
            true
        );
        
        // 急速效果 (无限时长)
        MobEffectInstance speed = new MobEffectInstance(
            MobEffects.MOVEMENT_SPEED,
            Integer.MAX_VALUE,
            0,  // 1级急速
            false,
            false,
            true
        );
        
        // 添加效果到玩家
        player.addEffect(nightVision);
        player.addEffect(fireResistance);
        player.addEffect(speed);
    }

    /**
     * 添加物品提示信息
     * @param stack 物品堆
     * @param level 世界
     * @param tooltip 提示列表
     * @param flag 提示标志
     */
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("§6食用后获得永久效果："));
        tooltip.add(Component.literal("§b• 夜视"));
        tooltip.add(Component.literal("§c• 抗火"));
        tooltip.add(Component.literal("§a• 急速"));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.literal("§7每个玩家只能食用一次"));
        tooltip.add(Component.literal("§7多次食用不会叠加效果"));
        super.appendHoverText(stack, level, tooltip, flag);
    }

    /**
     * 检查物品是否有光泽效果
     * @param stack 物品堆
     * @return 是否有光泽
     */
    @Override
    public boolean isFoil(ItemStack stack) {
        return true; // 超级苹果始终有附魔光泽
    }
}