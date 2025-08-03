package com.randomlucky.mod.common.item;

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

public class HealthBreadItem extends Item {
    public HealthBreadItem(Properties properties) {
        super(properties.food(new FoodProperties.Builder()
                .nutrition(4)
                .saturationMod(0.6f)
                .build()));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide && entity instanceof Player player) {
            // 增加1颗心（2点血量）
            var healthAttribute = player.getAttribute(Attributes.MAX_HEALTH);
            if (healthAttribute != null) {
                double currentMaxHealth = healthAttribute.getBaseValue();
                double maxPossibleHealth = 20.0 + (400.0 * 2.0); // 基础20血 + 400颗心
                
                if (currentMaxHealth < maxPossibleHealth) {
                    healthAttribute.setBaseValue(Math.min(currentMaxHealth + 2.0, maxPossibleHealth));
                    player.sendSystemMessage(Component.literal("最大血量增加了1颗心！"));
                } else {
                    player.sendSystemMessage(Component.literal("血量已达到最大值！"));
                }
            }
        }
        return super.finishUsingItem(stack, level, entity);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("§6使用后永久增加1颗心"));
        tooltip.add(Component.literal("§7最多可增加400颗心"));
        super.appendHoverText(stack, level, tooltip, flag);
    }
}