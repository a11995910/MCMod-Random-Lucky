package com.randomlucky.mod.common.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.List;
import java.util.UUID;

public class EnhancementUtils {
    public static final String ENHANCEMENT_TAG = "RandomLuckyEnhancement";
    public static final String ENHANCEMENT_LEVEL_TAG = "EnhancementLevel";
    public static final String ENHANCEMENT_MULTIPLIER_TAG = "EnhancementMultiplier";
    public static final String ORIGINAL_MAX_DAMAGE_TAG = "OriginalMaxDamage";
    public static final int MAX_ENHANCEMENT_LEVEL = 9;
    
    // 用于属性修改器的UUID
    private static final UUID ATTACK_DAMAGE_UUID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
    private static final UUID ATTACK_SPEED_UUID = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");

    /**
     * 获取物品的强化等级
     */
    public static int getEnhancementLevel(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains(ENHANCEMENT_TAG)) {
            CompoundTag enhancementTag = tag.getCompound(ENHANCEMENT_TAG);
            return enhancementTag.getInt(ENHANCEMENT_LEVEL_TAG);
        }
        return 0;
    }

    /**
     * 设置物品的强化等级
     */
    public static void setEnhancementLevel(ItemStack stack, int level) {
        CompoundTag tag = stack.getOrCreateTag();
        CompoundTag enhancementTag = new CompoundTag();
        enhancementTag.putInt(ENHANCEMENT_LEVEL_TAG, level);
        enhancementTag.putDouble(ENHANCEMENT_MULTIPLIER_TAG, Math.pow(2, level));
        tag.put(ENHANCEMENT_TAG, enhancementTag);
    }

    /**
     * 获取强化倍数
     */
    public static double getEnhancementMultiplier(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains(ENHANCEMENT_TAG)) {
            CompoundTag enhancementTag = tag.getCompound(ENHANCEMENT_TAG);
            return enhancementTag.getDouble(ENHANCEMENT_MULTIPLIER_TAG);
        }
        return 1.0;
    }

    /**
     * 检查物品是否可以强化
     */
    public static boolean canEnhance(ItemStack stack) {
        return getEnhancementLevel(stack) < MAX_ENHANCEMENT_LEVEL && isEnhanceable(stack);
    }

    /**
     * 检查物品是否是可强化的类型
     */
    public static boolean isEnhanceable(ItemStack stack) {
        if (stack.isEmpty()) return false;
        
        String itemName = stack.getItem().toString().toLowerCase();
        
        // 检查是否为武器、工具、护甲或熔炉
        return itemName.contains("sword") ||
               itemName.contains("axe") ||
               itemName.contains("pickaxe") ||
               itemName.contains("shovel") ||
               itemName.contains("hoe") ||
               itemName.contains("helmet") ||
               itemName.contains("chestplate") ||
               itemName.contains("leggings") ||
               itemName.contains("boots") ||
               itemName.contains("bow") ||
               itemName.contains("crossbow") ||
               itemName.contains("trident") ||
               itemName.contains("furnace") ||
               stack.isDamageableItem();
    }

    /**
     * 强化物品
     */
    public static ItemStack enhanceItem(ItemStack stack) {
        if (!canEnhance(stack)) {
            return stack;
        }

        ItemStack result = stack.copy();
        int currentLevel = getEnhancementLevel(result);
        int newLevel = currentLevel + 1;
        setEnhancementLevel(result, newLevel);

        // 强化耐久度（如果物品有耐久度）
        if (result.isDamageableItem()) {
            enhanceDurability(result, newLevel);
        }

        // 更新显示名称
        updateDisplayName(result);
        
        String itemName = result.getItem().toString().toLowerCase();
        
        if (itemName.contains("furnace")) {
            // 熔炉特殊处理：添加熔炼速度增强信息
            addFurnaceEnhancement(result, newLevel);
        } else {
            // 普通装备：添加附魔效果和属性修改器
            addEnhancementEffects(result, newLevel);
            addAttributeModifiers(result, newLevel);
        }

        return result;
    }
    
    /**
     * 强化物品耐久度
     * @param stack 物品堆
     * @param level 强化等级
     */
    private static void enhanceDurability(ItemStack stack, int level) {
        if (!stack.isDamageableItem()) {
            return;
        }
        
        CompoundTag tag = stack.getOrCreateTag();
        CompoundTag enhancementTag = tag.getCompound(ENHANCEMENT_TAG);
        
        // 获取或保存原始最大耐久度
        int originalMaxDamage;
        if (enhancementTag.contains(ORIGINAL_MAX_DAMAGE_TAG)) {
            originalMaxDamage = enhancementTag.getInt(ORIGINAL_MAX_DAMAGE_TAG);
        } else {
            originalMaxDamage = stack.getMaxDamage();
            enhancementTag.putInt(ORIGINAL_MAX_DAMAGE_TAG, originalMaxDamage);
            tag.put(ENHANCEMENT_TAG, enhancementTag);
        }
        
        // 计算当前耐久度比例
        double durabilityRatio = 1.0 - ((double) stack.getDamageValue() / stack.getMaxDamage());
        
        // 计算新的最大耐久度（翻倍效果）
        int newMaxDamage = (int) (originalMaxDamage * Math.pow(2, level));
        
        // 设置新的最大耐久度
        CompoundTag itemTag = stack.getOrCreateTag();
        itemTag.putInt("MaxDamage", newMaxDamage);
        
        // 根据比例设置新的当前耐久度
        int newDamage = (int) (newMaxDamage * (1.0 - durabilityRatio));
        stack.setDamageValue(newDamage);
    }
    
    /**
     * 为熔炉添加强化信息
     */
    private static void addFurnaceEnhancement(ItemStack stack, int level) {
        // 为熔炉添加特殊的NBT标记，表示熔炼速度增强
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt("FurnaceSpeedMultiplier", (int) Math.pow(2, level));
    }
    
    /**
     * 添加强化效果（通过属性修改器和附魔）
     */
    private static void addEnhancementEffects(ItemStack stack, int level) {
        String itemName = stack.getItem().toString().toLowerCase();
        
        // 添加附魔效果
        if (itemName.contains("sword") || itemName.contains("axe") || itemName.contains("trident")) {
            // 武器：增加锋利
            stack.enchant(Enchantments.SHARPNESS, Math.min(5 + level, 127));
        }
        
        if (itemName.contains("pickaxe") || itemName.contains("shovel") || itemName.contains("axe")) {
            // 工具：增加效率
            stack.enchant(Enchantments.BLOCK_EFFICIENCY, Math.min(5 + level, 127));
        }
        
        if (itemName.contains("helmet") || itemName.contains("chestplate") || 
            itemName.contains("leggings") || itemName.contains("boots")) {
            // 护甲：增加保护
            stack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, Math.min(4 + level, 127));
        }
        
        if (itemName.contains("bow")) {
            // 弓：增加力量
            stack.enchant(Enchantments.POWER_ARROWS, Math.min(5 + level, 127));
        }
        
        // 所有装备都增加耐久
        if (stack.isDamageableItem()) {
            stack.enchant(Enchantments.UNBREAKING, Math.min(3 + level, 127));
        }
        
        // 添加属性修改器以显示实际属性变化
        addAttributeModifiers(stack, level);
    }
    
    /**
     * 添加属性修改器
     */
    private static void addAttributeModifiers(ItemStack stack, int level) {
        double multiplier = Math.pow(2, level); // 2^level 倍率
        
        CompoundTag tag = stack.getOrCreateTag();
        
        // 创建一个临时的原版物品来获取其默认属性
        ItemStack originalStack = new ItemStack(stack.getItem());
        
        // 获取现有的属性修改器（如果有的话）
        net.minecraft.nbt.ListTag existingModifiers = new net.minecraft.nbt.ListTag();
        if (tag.contains("AttributeModifiers", 9)) {
            existingModifiers = tag.getList("AttributeModifiers", 10);
        }
        
        // 复制现有的修改器，但排除我们之前添加的增强修改器
        net.minecraft.nbt.ListTag modifiersList = new net.minecraft.nbt.ListTag();
        for (int i = 0; i < existingModifiers.size(); i++) {
            CompoundTag modifier = existingModifiers.getCompound(i);
            String name = modifier.getString("Name");
            if (!name.startsWith("randomlucky_enhancement")) {
                modifiersList.add(modifier);
            }
        }
        
        // 检查所有可能的槽位
        net.minecraft.world.entity.EquipmentSlot[] slots = {
            net.minecraft.world.entity.EquipmentSlot.MAINHAND,
            net.minecraft.world.entity.EquipmentSlot.OFFHAND,
            net.minecraft.world.entity.EquipmentSlot.HEAD,
            net.minecraft.world.entity.EquipmentSlot.CHEST,
            net.minecraft.world.entity.EquipmentSlot.LEGS,
            net.minecraft.world.entity.EquipmentSlot.FEET
        };
        
        for (net.minecraft.world.entity.EquipmentSlot slot : slots) {
            var attributes = originalStack.getItem().getDefaultAttributeModifiers(slot);
            
            for (var entry : attributes.entries()) {
                var attribute = entry.getKey();
                var modifier = entry.getValue();
                
                // 计算最终属性值
                double originalAmount = modifier.getAmount();
                double enhancement = 0;
                
                String attributeKey = net.minecraftforge.registries.ForgeRegistries.ATTRIBUTES.getKey(attribute).toString();
                
                if (originalAmount > 0) {
                    // 正数属性：直接按倍率增强
                    double finalValue = originalAmount * multiplier;
                    enhancement = finalValue - originalAmount;
                } else if (originalAmount < 0) {
                    // 负数属性：需要特殊处理让它变得更好
                    if (attributeKey.contains("attack_speed")) {
                        // 攻击速度：负数表示慢，我们要让它变快（减少负数）
                        // 例如：-2.8 -> -1.4（更快）-> -0.7（更快）
                        double targetValue = originalAmount / multiplier; // 除以倍率让负数变小
                        enhancement = targetValue - originalAmount; // 这会是正数
                    } else if (attributeKey.contains("attack_damage")) {
                        // 攻击伤害：如果是负数，我们要补偿并增强
                        double targetValue = originalAmount * multiplier;
                        enhancement = targetValue - originalAmount;
                    } else {
                        // 其他负数属性：保持不变
                        continue;
                    }
                }
                
                if (Math.abs(enhancement) > 0.001) { // 只有当增强值有意义时才添加
                    CompoundTag modifierTag = new CompoundTag();
                    
                    // 使用Resource Location格式的属性名
                    modifierTag.putString("AttributeName", attributeKey);
                    modifierTag.putString("Name", "randomlucky_enhancement_" + attributeKey.replace(":", "_"));
                    modifierTag.putDouble("Amount", enhancement);
                    modifierTag.putInt("Operation", 0); // 使用加法操作
                    modifierTag.putString("Slot", slot.getName());
                    
                    // 生成唯一UUID
                    java.util.UUID uuid = java.util.UUID.nameUUIDFromBytes(
                        ("randomlucky_" + attributeKey + "_" + slot.getName() + "_" + level).getBytes()
                    );
                    modifierTag.putIntArray("UUID", new int[]{
                        (int) (uuid.getMostSignificantBits() >> 32),
                        (int) uuid.getMostSignificantBits(),
                        (int) (uuid.getLeastSignificantBits() >> 32),
                        (int) uuid.getLeastSignificantBits()
                    });
                    
                    modifiersList.add(modifierTag);
                }
            }
        }
        
        if (!modifiersList.isEmpty()) {
            tag.put("AttributeModifiers", modifiersList);
        }
    }
    
    /**
     * 更新物品显示名称
     */
    private static void updateDisplayName(ItemStack stack) {
        int level = getEnhancementLevel(stack);
        if (level > 0) {
            Component originalName = Component.translatable(stack.getDescriptionId());
            Component enhancedName = Component.literal("§6[+" + level + "] §r").append(originalName);
            stack.setHoverName(enhancedName);
        }
    }

    /**
     * 添加工具提示
     */
    public static void addEnhancementTooltip(ItemStack stack, List<Component> tooltip) {
        int level = getEnhancementLevel(stack);
        if (level > 0) {
            String itemName = stack.getItem().toString().toLowerCase();
            
            if (itemName.contains("furnace")) {
                // 熔炉特殊提示
                int speedMultiplier = (int) Math.pow(2, level);
                tooltip.add(Component.literal("§6强化等级: +" + level));
                tooltip.add(Component.literal("§a熔炼速度: " + speedMultiplier + "x"));
                
                if (level < MAX_ENHANCEMENT_LEVEL) {
                    tooltip.add(Component.literal("§7可继续强化 " + (MAX_ENHANCEMENT_LEVEL - level) + " 次"));
                } else {
                    tooltip.add(Component.literal("§c已达到最大强化等级"));
                }
            } else {
                // 普通装备提示
                double multiplier = getEnhancementMultiplier(stack);
                tooltip.add(Component.literal("§6强化等级: +" + level));
                tooltip.add(Component.literal("§a属性倍数: " + String.format("%.1fx", multiplier)));
                
                // 显示耐久度强化信息
                if (stack.isDamageableItem()) {
                    CompoundTag tag = stack.getTag();
                    if (tag != null && tag.contains(ENHANCEMENT_TAG)) {
                        CompoundTag enhancementTag = tag.getCompound(ENHANCEMENT_TAG);
                        if (enhancementTag.contains(ORIGINAL_MAX_DAMAGE_TAG)) {
                            int originalMaxDamage = enhancementTag.getInt(ORIGINAL_MAX_DAMAGE_TAG);
                            // 从NBT标签中读取当前最大耐久度，如果没有则使用默认值
                            int currentMaxDamage = tag.contains("MaxDamage") ? tag.getInt("MaxDamage") : stack.getMaxDamage();
                            double durabilityMultiplier = (double) currentMaxDamage / originalMaxDamage;
                            tooltip.add(Component.literal("§b耐久度倍数: " + String.format("%.1fx", durabilityMultiplier)));
                        }
                    }
                }
                
                if (level < MAX_ENHANCEMENT_LEVEL) {
                    tooltip.add(Component.literal("§7可继续强化 " + (MAX_ENHANCEMENT_LEVEL - level) + " 次"));
                } else {
                    tooltip.add(Component.literal("§c已达到最大强化等级"));
                }
            }
        }
    }
}