package com.randomlucky.mod.common.event;

import com.randomlucky.mod.common.item.HealthBreadItem;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 玩家事件处理器
 * 处理玩家重生、登录等事件，确保血量增强面包的效果持久化
 */
@Mod.EventBusSubscriber(modid = "randomlucky")
public class PlayerEventHandler {
    
    /**
     * 玩家重生事件处理
     * 当玩家死亡重生时，恢复血量增强面包的效果
     * 
     * @param event 玩家重生事件
     */
    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide) {
            // 恢复玩家的血量上限
            HealthBreadItem.restorePlayerHealthLimit(player);
        }
    }
    
    /**
     * 玩家登录事件处理
     * 当玩家登录时，恢复血量增强面包的效果
     * 
     * @param event 玩家登录事件
     */
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide) {
            // 恢复玩家的血量上限
            HealthBreadItem.restorePlayerHealthLimit(player);
        }
    }
    
    /**
     * 玩家切换维度事件处理
     * 当玩家切换维度时，恢复血量增强面包的效果
     * 
     * @param event 玩家切换维度事件
     */
    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide) {
            // 恢复玩家的血量上限
            HealthBreadItem.restorePlayerHealthLimit(player);
        }
    }
}