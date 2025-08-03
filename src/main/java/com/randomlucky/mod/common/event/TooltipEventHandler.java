package com.randomlucky.mod.common.event;

import com.randomlucky.mod.common.item.EnhancementUtils;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "randomlucky")
public class TooltipEventHandler {
    
    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        if (EnhancementUtils.getEnhancementLevel(event.getItemStack()) > 0) {
            EnhancementUtils.addEnhancementTooltip(event.getItemStack(), event.getToolTip());
        }
    }
}