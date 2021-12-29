package me.Danker.features;

import me.Danker.commands.ToggleCommand;
import me.Danker.events.ClickEvent;
import me.Danker.utils.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MovingObjectPosition; 
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class GhostBlock {
    @SubscribeEvent
    public void onInteract(PlayerInteractEvent event) {
        if (!ToggleCommand.ghostblock)
            return;
        String itemId = Utils.getSkyBlockID(Minecraft.getMinecraft().thePlayer.getHeldItem());
        
        if ((itemId.equals("STONK_PICKAXE") || itemId.equals("GOLD_PICKAXE")) && event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
            MovingObjectPosition object = player.rayTrace(Minecraft.getMinecraft().playerController.getBlockReachDistance(), 1.0f);
            Minecraft.getMinecraft().theWorld.setBlockToAir(object.getBlockPos());
            event.setCanceled(true);
        }
    }
}
