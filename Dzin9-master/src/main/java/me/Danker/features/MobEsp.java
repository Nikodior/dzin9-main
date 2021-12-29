package me.Danker.features;

import me.Danker.utils.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;
import java.awt.Color;
 
import me.Danker.events.RenderEntityModelEvent;
import me.Danker.utils.LocationUtils;
import me.Danker.utils.OutlineUtils;

public class MobEsp
 {
   private static HashMap<Entity, Color> highlightedEntities = new HashMap<>();
   private static HashSet<Entity> checkedStarNameTags = new HashSet<>();
   
   private static void highlightEntity(Entity entity, Color color) {
     highlightedEntities.put(entity, color);
   }
   
   @SubscribeEvent
   public void onEntityJoinWorld(EntityJoinWorldEvent event) {
     if (Utils.inDungeons) {
       if (/*Config.minibossEsp &&*/ event.entity instanceof net.minecraft.entity.player.EntityPlayer) {
         String name = event.entity.getName();
         switch (name) {
           case "Shadow Assassin":
             event.entity.setInvisible(false);
             highlightEntity(event.entity, Color.MAGENTA);
             break;
           
           case "Lost Adventurer":
             highlightEntity(event.entity, Color.BLUE);
             break;
           
           case "Diamond Guy":
             highlightEntity(event.entity, Color.CYAN);
             break;
         } 
       
       } 
       if (/*Config.secretBatEsp &&*/ event.entity instanceof net.minecraft.entity.passive.EntityBat) {
         highlightEntity(event.entity, Color.RED);
       }
     } 
     
     if (Utils.inSkyblock && LocationUtils.onIsland(LocationUtils.Island.CRYSTAL_HOLLOWS)) {
       if (/*Config.sludgeEsp &&*/ 
         event.entity instanceof net.minecraft.entity.monster.EntitySlime && !(event.entity instanceof net.minecraft.entity.monster.EntityMagmaCube)) {
         highlightEntity(event.entity, Color.GREEN);
       }
 
       
       if (/*Config.yogEsp &&*/
         event.entity instanceof net.minecraft.entity.monster.EntityMagmaCube) {
         highlightEntity(event.entity, Color.RED);
       }
 
       
       if (/*Config.corleoneEsp && */
         event.entity instanceof EntityOtherPlayerMP && event.entity.getName().equals("Team Treasurite")) {
         float health = ((EntityOtherPlayerMP)event.entity).getMaxHealth();
         if (health == 1000000.0F || health == 2000000.0F) {
           highlightEntity(event.entity, Color.PINK);
         }
       } 
     } 
   }
 
   
   @SubscribeEvent
   public void onRenderEntityModel(RenderEntityModelEvent event) {
     if (Utils.inDungeons && !checkedStarNameTags.contains(event.entity) && /*Config.starredMobEsp && */
       event.entity instanceof net.minecraft.entity.item.EntityArmorStand && 
       event.entity.hasCustomName() && event.entity.getCustomNameTag().contains("✯")) {
       List<Entity> possibleEntities = event.entity.getEntityWorld().getEntitiesInAABBexcluding((Entity)event.entity, event.entity.getEntityBoundingBox().expand(0.0D, 3.0D, 0.0D), entity -> !(entity instanceof net.minecraft.entity.item.EntityArmorStand));
       if (!possibleEntities.isEmpty()) {
         highlightEntity(possibleEntities.get(0), Color.ORANGE);
       }
       checkedStarNameTags.add(event.entity);
     } 
 
 
     
     if (/*FolderSetting.isEnabled("Mob ESP") &&*/ !highlightedEntities.isEmpty() && highlightedEntities.containsKey(event.entity)) {
       OutlineUtils.outlineEntity(event, highlightedEntities.get(event.entity));
     }
   }
   
   @SubscribeEvent
   public void onWorldLoad(WorldEvent.Load event) {
     highlightedEntities.clear();
     checkedStarNameTags.clear();
   }
 }
