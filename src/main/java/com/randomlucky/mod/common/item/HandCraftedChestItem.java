package com.randomlucky.mod.common.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

import java.util.List;

public class HandCraftedChestItem extends Item {
    
    public HandCraftedChestItem(Properties properties) {
        super(properties);
    }
    
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            // 打开手作箱子界面
            openHandCraftedChest(serverPlayer, stack);
        }
        
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }
    
    private void openHandCraftedChest(ServerPlayer player, ItemStack stack) {
        // 创建一个虚拟的箱子容器
        HandCraftedChestContainer container = new HandCraftedChestContainer(player, stack);
        NetworkHooks.openScreen(player, container, buffer -> {
            buffer.writeInt(player.getInventory().selected); // 发送手持物品槽位
        });
    }
    
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.literal("§6右键在手中打开"));
        tooltip.add(Component.literal("§7可存储27个物品"));
        
        // 显示存储的物品数量
        int itemCount = getStoredItemCount(stack);
        if (itemCount > 0) {
            tooltip.add(Component.literal("§a已存储: " + itemCount + "/27 个物品"));
        }
    }
    
    /**
     * 获取箱子中存储的物品数量
     */
    public static int getStoredItemCount(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains("Items")) {
            ListTag items = tag.getList("Items", 10);
            return items.size();
        }
        return 0;
    }
    
    /**
     * 获取箱子的存储容器
     */
    public static ListTag getItems(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if (!tag.contains("Items")) {
            tag.put("Items", new ListTag());
        }
        return tag.getList("Items", 10);
    }
    
    /**
     * 保存物品到箱子
     */
    public static void setItems(ItemStack stack, ListTag items) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.put("Items", items);
    }
}