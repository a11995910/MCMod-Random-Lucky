package com.randomlucky.mod.common.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

public class HandCraftedChestContainer implements MenuProvider {
    private final ServerPlayer player;
    private final ItemStack chestStack;
    private final int slot;
    private SimpleContainer container;
    
    public HandCraftedChestContainer(ServerPlayer player, ItemStack chestStack) {
        this.player = player;
        this.chestStack = chestStack;
        this.slot = player.getInventory().selected;
        this.container = new SimpleContainer(27);
        loadContainerFromItem();
    }
    
    @Override
    public Component getDisplayName() {
        return Component.literal("手作箱子");
    }
    
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        // 创建一个27槽的容器（标准箱子大小）
        SimpleContainer container = new SimpleContainer(27) {
            @Override
            public void setChanged() {
                super.setChanged();
                // 当容器内容改变时，保存到物品NBT
                saveContainerToItem(this);
            }
            
            @Override
            public void stopOpen(Player player) {
                super.stopOpen(player);
                // 当容器关闭时也保存
                saveContainerToItem(this);
            }
        };
        
        // 从物品NBT加载内容到新容器
        loadContainerFromItem(container);
        
        // 创建箱子菜单
        return new ChestMenu(MenuType.GENERIC_9x3, containerId, playerInventory, container, 3) {
            @Override
            public void removed(Player player) {
                super.removed(player);
                // 菜单关闭时保存数据
                saveContainerToItem(container);
            }
        };
    }
    
    private void loadContainerFromItem() {
        loadContainerFromItem(this.container);
    }
    
    private void loadContainerFromItem(SimpleContainer container) {
        ListTag items = HandCraftedChestItem.getItems(chestStack);
        
        // 清空容器
        container.clearContent();
        
        for (int i = 0; i < items.size() && i < 27; i++) {
            CompoundTag itemTag = items.getCompound(i);
            if (!itemTag.isEmpty()) {
                int slot = itemTag.getByte("Slot") & 255;
                if (slot >= 0 && slot < 27) {
                    ItemStack stack = ItemStack.of(itemTag);
                    container.setItem(slot, stack);
                }
            }
        }
    }
    
    private void saveContainerToItem(SimpleContainer container) {
        // 确保我们仍然持有正确的物品
        ItemStack currentStack = player.getInventory().getItem(slot);
        if (currentStack.getItem() instanceof HandCraftedChestItem) {
            ListTag items = new ListTag();
            
            for (int i = 0; i < container.getContainerSize(); i++) {
                ItemStack stack = container.getItem(i);
                if (!stack.isEmpty()) {
                    CompoundTag itemTag = new CompoundTag();
                    itemTag.putByte("Slot", (byte) i);
                    stack.save(itemTag);
                    items.add(itemTag);
                }
            }
            
            HandCraftedChestItem.setItems(currentStack, items);
        }
    }
}