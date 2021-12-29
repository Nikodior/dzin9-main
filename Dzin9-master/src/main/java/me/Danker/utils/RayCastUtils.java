package me.Danker.utils; 

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import org.lwjgl.util.vector.Vector3f;

public class RayCastUtils {
    public static boolean isFacingBlock(BlockPos block, float range) {
      float stepSize = 0.15F;
      if (Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().theWorld != null) {
        Vector3f position = new Vector3f((float)Minecraft.getMinecraft().thePlayer.posX, (float)Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight(), (float)Minecraft.getMinecraft().thePlayer.posZ);
        Vec3 look = Minecraft.getMinecraft().thePlayer.getLook(0.0F);
        Vector3f step = new Vector3f((float)look.xCoord, (float)look.yCoord, (float)look.zCoord);
        step.scale(stepSize / step.length());
        for (int i = 0; i < Math.floor((range / stepSize)) - 2.0D; i++) {
          BlockPos blockAtPos = new BlockPos(position.x, position.y, position.z);
          if (blockAtPos.equals(block))
            return true; 
          position.translate(step.x, step.y, step.z);
        } 
      } 
      return false;
    }
    
    public static <T extends net.minecraft.entity.Entity> List<T> getFacedEntityOfType(Class<T> _class, float range) {
      float stepSize = 0.5F;
      if (Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().theWorld != null) {
        Vector3f position = new Vector3f((float)Minecraft.getMinecraft().thePlayer.posX, (float)Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight(), (float)Minecraft.getMinecraft().thePlayer.posZ);
        
        Vec3 look = Minecraft.getMinecraft().thePlayer.getLook(0.0F);

        Vector3f step = new Vector3f((float)look.xCoord, (float)look.yCoord, (float)look.zCoord);
        step.scale(stepSize / step.length());
        for (int i = 0; i < Math.floor((range / stepSize)) - 2.0D; i++) {
          List<T> entities = Minecraft.getMinecraft().theWorld.getEntitiesWithinAABB(_class, new AxisAlignedBB(position.x - 0.5D, position.y - 0.5D, position.z - 0.5D, position.x + 0.5D, position.y + 0.5D, position.z + 0.5D));
          if (!entities.isEmpty())
            return entities; 
          position.translate(step.x, step.y, step.z);
        } 
      } 
      return null;
    }
  }