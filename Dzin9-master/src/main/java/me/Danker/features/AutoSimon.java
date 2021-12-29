package me.Danker.features;

import me.Danker.commands.ToggleCommand;
import me.Danker.utils.Utils;
import me.Danker.utils.KeybindUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraft.init.Blocks;

public class AutoSimon {
    private boolean clicking = false;
    @SubscribeEvent
    public void onInteract(PlayerInteractEvent event) {
        if (ToggleCommand.autosimon && !this.clicking && Utils.inDungeons && event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK &&
        Minecraft.getMinecraft().theWorld.getBlockState(event.pos).getBlock() == Blocks.stone_button)
        {
            int x = event.pos.getX();
            int y = event.pos.getY();
            int z = event.pos.getZ();
            if (x == 309 && y >= 120 && y <= 123 && z >= 291 && z <= 294) {
                this.clicking = true;
                for (int i = 0; i < 4; i++) {
                    KeybindUtils.rightClick();
                }
                this.clicking = false;
            } 
        }
    }
}
