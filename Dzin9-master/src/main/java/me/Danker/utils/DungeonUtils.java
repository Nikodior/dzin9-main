package me.Danker.utils; 

import me.Danker.DankersSkyblockMod; 
import me.Danker.events.TickEndEvent; 

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DungeonUtils {
    private static final Pattern namePattern = Pattern.compile("([A-Za-z0-9_]{0,16}) \\((Mage|Tank|Berserk|Healer|Archer) ([IVXL0]{1,7})\\)");
    
    private static final Pattern secretsPattern = Pattern.compile(" Secrets Found: (\\d*)");
    
    private static final Pattern cryptsPattern = Pattern.compile(" Crypts: (\\d*)");
    
    private static final Pattern deathsPattern = Pattern.compile("Deaths: \\((\\d*)\\)");
    
    private static final String[] mimicDeathMessages = new String[] { "Mimic Dead!", "$SKYTILS-DUNGEON-SCORE-MIMIC$", "Child Destroyed!", "Mimic Obliterated!", "Mimic Exorcised!", "Mimic Destroyed!", "Mimic Annhilated!" };
    
    public static DungeonRun dungeonRun = null;

    public enum Floor {
        ENTERANCE("(E)"),
        FLOOR_1("(F1)"),
        FLOOR_2("(F2)"),
        FLOOR_3("(F3)"),
        FLOOR_4("(F4)"),
        FLOOR_5("(F5)"),
        FLOOR_6("(F6)"),
        FLOOR_7("(F7)"),
        MASTER_1("(M1)"),
        MASTER_2("(M2)"),
        MASTER_3("(M3)"),
        MASTER_4("(M4)"),
        MASTER_5("(M5)"),
        MASTER_6("(M6)"),
        MASTER_7("(M7)");
        
        public String name;
        
        Floor(String name) {
          this.name = name;
        }
      }

      public static class DungeonRun {
        public int secretsFound = 0;
        
        public int cryptsFound = 0;
        
        public DungeonUtils.Floor floor = null;
        
        public boolean inBoss = false;
        
        public boolean mimicFound = false;
        
        public int deaths;
        
        public HashSet<String> team = new HashSet<>();
        
        public long startTime = System.currentTimeMillis();
        
        public long getTimeMs() {
          return System.currentTimeMillis() - this.startTime;
        }
      }

      @SubscribeEvent
      public void onChat(ClientChatReceivedEvent event) {
        if (Utils.inDungeons && !dungeonRun.mimicFound && onFloorWithMimic())
          for (String mimicMessage : mimicDeathMessages) {
            if (event.message.getFormattedText().contains(mimicMessage))
              dungeonRun.mimicFound = true; 
          }  
      }

      private int counter = 0;

      @SubscribeEvent
      public void onTick(TickEndEvent event) {
        if (this.counter % 20 == 0) {
          if (Utils.inDungeons) {
            if (dungeonRun == null)
              dungeonRun = new DungeonRun(); 
            if (dungeonRun.floor == null) {
              String cataLine = ScoreboardUtils.getLineThatContains("The Catacombs");
              if (cataLine != null)
                for (Floor floor : Floor.values()) {
                  if (cataLine.contains(floor.name))
                    dungeonRun.floor = floor; 
                }  
            } 
            /*for (String name : TabUtils.getTabList()) {
              if (name.contains("Crypts: ")) {
                name = Utils.removeFormatting(name);
                Matcher cryptsMatcher = cryptsPattern.matcher(name);
                if (cryptsMatcher.matches())
                  dungeonRun.cryptsFound = Integer.parseInt(cryptsMatcher.group(1)); 
                continue;
              } 
              if (name.contains("Secrets Found: ")) {
                name = Utils.removeFormatting(name);
                Matcher secretsMatcher = secretsPattern.matcher(name);
                if (secretsMatcher.matches())
                  dungeonRun.secretsFound = Integer.parseInt(secretsMatcher.group(1)); 
                continue;
              } 
              if (name.contains("Deaths: ")) {
                name = Utils.removeFormatting(name);
                Matcher deathsMatcher = deathsPattern.matcher(name);
                if (deathsMatcher.matches())
                  dungeonRun.deaths = Integer.parseInt(deathsMatcher.group(1)); 
                continue;
              } 
              if (dungeonRun.team.size() < 5 && (name.contains("Mage") || name.contains("Berserker") || name.contains("Archer") || name.contains("Tank") || name.contains("Healer"))) {
                name = Utils.removeFormatting(name);
                Matcher nameMatcher = namePattern.matcher(name);
                if (nameMatcher.matches())
                  dungeonRun.team.add(nameMatcher.group(1).trim()); 
              } 
            } */
            if (Minecraft.getMinecraft().theWorld != null && dungeonRun != null && ((
              ScoreboardUtils.scoreboardContains("30,30") && (dungeonRun.floor == Floor.FLOOR_1 || dungeonRun.floor == Floor.MASTER_1)) || (
              ScoreboardUtils.scoreboardContains("30,125") && (dungeonRun.floor == Floor.FLOOR_2 || dungeonRun.floor == Floor.MASTER_2)) || (
              ScoreboardUtils.scoreboardContains("30,225") && (dungeonRun.floor == Floor.FLOOR_3 || dungeonRun.floor == Floor.MASTER_3)) || (
              ScoreboardUtils.scoreboardContains("- Healthy") && (dungeonRun.floor == Floor.FLOOR_3 || dungeonRun.floor == Floor.MASTER_3)) || (
              ScoreboardUtils.scoreboardContains("30,344") && (dungeonRun.floor == Floor.FLOOR_4 || dungeonRun.floor == Floor.MASTER_4)) || (
              ScoreboardUtils.scoreboardContains("livid") && (dungeonRun.floor == Floor.FLOOR_5 || dungeonRun.floor == Floor.MASTER_5)) || (
              ScoreboardUtils.scoreboardContains("sadan") && (dungeonRun.floor == Floor.FLOOR_6 || dungeonRun.floor == Floor.MASTER_6)) || (
              ScoreboardUtils.scoreboardContains("necron") && (dungeonRun.floor == Floor.FLOOR_7 || dungeonRun.floor == Floor.MASTER_7))))
              dungeonRun.inBoss = true; 
          } else {
            dungeonRun = null;
          } 
          this.counter = 0;
        } 
        this.counter++;
      }
      
      /*
      public static void debug() {
        if (Utils.inDungeons && dungeonRun != null) {
          Utils.sendModMessage("Floor: " + dungeonRun.floor.name());
          Utils.sendModMessage("In Boss: " + dungeonRun.inBoss);
          Utils.sendModMessage("Secrets Found: " + dungeonRun.secretsFound);
          Utils.sendModMessage("Crypts Found: " + dungeonRun.cryptsFound);
        } else {
          Utils.sendMessage("You must be in a dungeon to debug a dungeon!");
        } 
      }

      public static int calculateScore() {
        if (dungeonRun == null || DungeonMap.activeDungeonLayout == null)
          return 0; 
        return calculateSkillScore() + calculateExploreScore() + calculateSpeedScore() + calculateBonusScore();
      }

      private static int calculateSkillScore() {
        return 100 - dungeonRun.deaths * 2 + ((Config.assumeSpiritPet && dungeonRun.deaths > 0) ? 1 : 0);
      }

      private static int calculateExploreScore() {
        return 60 + 40 * dungeonRun.secretsFound / DungeonMap.activeDungeonLayout.totalSecrets;
      }
      
      private static int calculateSpeedScore() {
        return 100;
      }
      
      private static int calculateBonusScore() {
        return (MayorAPI.isPaulBonus() ? 10 : 0) + (dungeonRun.mimicFound ? 5 : 0) + MathHelper.clamp(dungeonRun.cryptsFound, 0, 5);
      }
      */
      @SubscribeEvent
      public void onEntityDeath(LivingDeathEvent event) {
        if (onFloorWithMimic() && !dungeonRun.mimicFound && 
          event.entity.getClass() == EntityZombie.class) {
          EntityZombie entity = (EntityZombie)event.entity;
          if (entity.isChild() && 
            entity.getCurrentArmor(0) == null && entity.getCurrentArmor(1) == null && entity.getCurrentArmor(2) == null && entity.getCurrentArmor(3) == null)
            dungeonRun.mimicFound = true; 
        } 
      }

      public static boolean inFloor(Floor floor) {
        return floor.equals(dungeonRun.floor);
      }
      
      public static boolean onFloorWithMimic() {
        return (dungeonRun != null && dungeonRun.floor != null && dungeonRun.floor != Floor.ENTERANCE && dungeonRun.floor != Floor.FLOOR_1 && dungeonRun.floor != Floor.FLOOR_2 && dungeonRun.floor != Floor.FLOOR_3 && dungeonRun.floor != Floor.MASTER_1 && dungeonRun.floor != Floor.MASTER_2 && dungeonRun.floor != Floor.MASTER_3);
      }
    }