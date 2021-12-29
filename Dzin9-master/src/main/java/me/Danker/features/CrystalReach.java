package me.Danker.features; 

import me.Danker.DankersSkyblockMod; 
import me.Danker.events.ClickEvent; 
import me.Danker.events.TickEndEvent; 
import me.Danker.utils.DungeonUtils; 
import me.Danker.utils.RayCastUtils; 
import me.Danker.utils.Utils;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.util.vector.Vector3f;
import net.minecraft.client.Minecraft;
import me.Danker.commands.ToggleCommand;

public class CrystalReach {
    public static EntityEnderCrystal crystal = null;
    
    public static boolean isEnabled() {
      return (ToggleCommand.crystalreach && Utils.inDungeons && Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().thePlayer.getPosition().getY() > 215 
      && DungeonUtils.dungeonRun != null && DungeonUtils.dungeonRun.inBoss && DungeonUtils.inFloor(DungeonUtils.Floor.FLOOR_7));
    }
   
    @SubscribeEvent
    public void onTick(TickEndEvent event) {
      if (isEnabled() && Minecraft.getMinecraft().thePlayer.isSneaking()) {
        List<EntityEnderCrystal> crystals = RayCastUtils.getFacedEntityOfType(EntityEnderCrystal.class, 32.0F);
        if (crystals != null) {
          crystal = crystals.get(0);
        } else {
          crystal = null;
        } 
      } else {
        crystal = null;
      } 
    }
    
    @SubscribeEvent
    public void onRightClick(ClickEvent.Right event) {
      if (isEnabled() && Minecraft.getMinecraft().thePlayer.isSneaking() && crystal != null) {
        List<Entity> armorStand = Minecraft.getMinecraft().theWorld.getEntitiesInAABBexcluding((Entity)crystal, crystal.getEntityBoundingBox(), entity -> (entity instanceof net.minecraft.entity.item.EntityArmorStand && entity.getCustomNameTag().contains("CLICK HERE")));
        if (!armorStand.isEmpty() && armorStand.get(0) != null) {
          Minecraft.getMinecraft().playerController.interactWithEntitySendPacket((EntityPlayer)Minecraft.getMinecraft().thePlayer, armorStand.get(0));
          event.setCanceled(true);
        } 
      } 
    }
    
    private static EntityEnderCrystal lookingAtCrystal() {
      float range = 32.0F;
      float stepSize = 0.5F;
      if (Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().theWorld != null) {
        Vector3f position = new Vector3f((float)Minecraft.getMinecraft().thePlayer.posX, (float)Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight(), (float)Minecraft.getMinecraft().thePlayer.posZ);
        Vec3 look = Minecraft.getMinecraft().thePlayer.getLook(0.0F);
        Vector3f step = new Vector3f((float)look.xCoord, (float)look.yCoord, (float)look.zCoord);
        step.scale(stepSize / step.length());
        for (int i = 0; i < Math.floor((range / stepSize)) - 2.0D; i++) {
          List<EntityEnderCrystal> entities = Minecraft.getMinecraft().theWorld.getEntitiesWithinAABB(EntityEnderCrystal.class, new AxisAlignedBB(position.x - 0.5D, position.y - 0.5D, position.z - 0.5D, position.x + 0.5D, position.y + 0.5D, position.z + 0.5D));
          if (!entities.isEmpty())
            return entities.get(0); 
          position.translate(step.x, step.y, step.z);
        } 
      } 
      return null;
    }
  }