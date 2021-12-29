package me.Danker.features; 

import me.Danker.utils.Utils; 
import me.Danker.DankersSkyblockMod; 
import me.Danker.events.TickEndEvent; 

import me.Danker.commands.ToggleCommand;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoTerminals {        
    private static final ArrayList<Slot> clickQueue = new ArrayList<>(28);
    
    private static final int[] mazeDirection = new int[] { -9, -1, 1, 9 };
    
    private static TerminalType currentTerminal = TerminalType.NONE;
    
    private static long lastClickTime = 0L;
    
    private static int windowId = 0;
    
    private static int windowClicks = 0;
    
    private static boolean recalculate = false;
    
    public static boolean testing = false;

    private int delay = 120;
    
    private enum TerminalType {
      MAZE, NUMBERS, CORRECT_ALL, LETTER, COLOR, NONE;
    }

    @SubscribeEvent
    public void onGuiDraw(GuiScreenEvent.BackgroundDrawnEvent event) {
      if (!ToggleCommand.autoterminals)
        return; 

      if (!Utils.inDungeons)
        return; 
      if (event.gui instanceof GuiChest) {
        Container container = ((GuiChest)event.gui).inventorySlots;
        if (container instanceof ContainerChest) {
          List<Slot> invSlots = container.inventorySlots;
          if (currentTerminal == TerminalType.NONE) {
            String chestName = ((ContainerChest)container).getLowerChestInventory().getDisplayName().getUnformattedText();
            if (chestName.equals("Navigate the maze!")) {
              currentTerminal = TerminalType.MAZE;
            } else if (chestName.equals("Click in order!")) {
              currentTerminal = TerminalType.NUMBERS;
            } else if (chestName.equals("Correct all the panes!")) {
              currentTerminal = TerminalType.CORRECT_ALL;
            } else if (chestName.startsWith("What starts with: '")) {
              currentTerminal = TerminalType.LETTER;
            } else if (chestName.startsWith("Select all the")) {
              currentTerminal = TerminalType.COLOR;
            } 
          } 
          if (currentTerminal != TerminalType.NONE) {
            if (clickQueue.isEmpty() || recalculate) {
              recalculate = getClicks((ContainerChest)container);
            } else {
              switch (currentTerminal) {
                case MAZE:
                case NUMBERS:
                case CORRECT_ALL:
                  clickQueue.removeIf(slot -> (((Slot)invSlots.get(slot.slotNumber)).getHasStack() && ((Slot)invSlots.get(slot.slotNumber)).getStack().getItemDamage() == 5));
                  break;
                case LETTER:
                case COLOR:
                  clickQueue.removeIf(slot -> (((Slot)invSlots.get(slot.slotNumber)).getHasStack() && ((Slot)invSlots.get(slot.slotNumber)).getStack().isItemEnchanted()));
                  break;
              } 
            } 
            if (!clickQueue.isEmpty() && 
              System.currentTimeMillis() - lastClickTime > delay)
              clickSlot(clickQueue.get(0)); 
          } 
        } 
      } 
    }

    @SubscribeEvent
    public void onTick(TickEndEvent event) {
      if (!ToggleCommand.autoterminals)
        return;

      if (!Utils.inDungeons)
        return; 
      if (Minecraft.getMinecraft().thePlayer == null || Minecraft.getMinecraft().theWorld == null)
        return; 
      if (!(Minecraft.getMinecraft().currentScreen instanceof GuiChest)) {
        currentTerminal = TerminalType.NONE;
        clickQueue.clear();
        windowClicks = 0;
      }  
    }    

    private boolean getClicks(ContainerChest container) {
        int[] mazeDirection;
        boolean[] isStartSlot;
        int endSlot, slot, min;
        Slot[] temp;
        int i;
        String colorNeeded;
        List<Slot> invSlots = container.inventorySlots;
        String chestName = container.getLowerChestInventory().getDisplayName().getUnformattedText();
        clickQueue.clear();
        switch (currentTerminal) {
          case MAZE:
            mazeDirection = new int[] { -9, -1, 1, 9 };
            isStartSlot = new boolean[54];
            endSlot = -1;
            for (Slot slot1 : invSlots) {
              if (slot1.inventory == Minecraft.getMinecraft().thePlayer.inventory)
                continue; 
              ItemStack itemStack = slot1.getStack();
              if (itemStack != null && 
                itemStack.getItem() == Item.getItemFromBlock((Block)Blocks.stained_glass_pane)) {
                if (itemStack.getItemDamage() == 5) {
                  isStartSlot[slot1.slotNumber] = true;
                  continue;
                } 
                if (itemStack.getItemDamage() == 14)
                  endSlot = slot1.slotNumber; 
              } 
            } 
            for (slot = 0; slot < 54; slot++) {
              if (isStartSlot[slot]) {
                boolean[] mazeVisited = new boolean[54];
                int startSlot = slot;
                while (startSlot != endSlot) {
                  boolean newSlotChosen = false;
                  for (int j : mazeDirection) {
                    int nextSlot = startSlot + j;
                    if (nextSlot >= 0 && nextSlot <= 53 && (j != -1 || startSlot % 9 != 0) && (j != 1 || startSlot % 9 != 8)) {
                      if (nextSlot == endSlot)
                        return false; 
                      if (!mazeVisited[nextSlot]) {
                        ItemStack itemStack = ((Slot)invSlots.get(nextSlot)).getStack();
                        if (itemStack != null && 
                          itemStack.getItem() == Item.getItemFromBlock((Block)Blocks.stained_glass_pane) && itemStack.getItemDamage() == 0) {
                          clickQueue.add(invSlots.get(nextSlot));
                          startSlot = nextSlot;
                          mazeVisited[nextSlot] = true;
                          newSlotChosen = true;
                          break;
                        } 
                      } 
                    } 
                  } 
                  if (!newSlotChosen) {
                    System.out.println("Maze calculation aborted");
                    return true;
                  } 
                } 
              } 
            } 
            return true;
          case NUMBERS:
            min = 0;
            temp = new Slot[14];
            for (i = 10; i <= 25; i++) {
              if (i != 17 && i != 18) {
                ItemStack itemStack = ((Slot)invSlots.get(i)).getStack();
                if (itemStack != null && 
                  itemStack.getItem() == Item.getItemFromBlock((Block)Blocks.stained_glass_pane) && itemStack.stackSize < 15)
                  if (itemStack.getItemDamage() == 14) {
                    temp[itemStack.stackSize - 1] = invSlots.get(i);
                  } else if (itemStack.getItemDamage() == 5 && 
                    min < itemStack.stackSize) {
                    min = itemStack.stackSize;
                  }  
              } 
            } 
            clickQueue.addAll((Collection<? extends Slot>)Arrays.<Slot>stream(temp).filter(Objects::nonNull).collect(Collectors.toList()));
            if (clickQueue.size() != 14 - min)
              return true; 
            break;
          case CORRECT_ALL:
            for (Slot slot1 : invSlots) {
              if (slot1.inventory == Minecraft.getMinecraft().thePlayer.inventory || 
                slot1.slotNumber < 9 || slot1.slotNumber > 35 || slot1.slotNumber % 9 <= 1 || slot1.slotNumber % 9 >= 7)
                continue; 
              ItemStack itemStack = slot1.getStack();
              if (itemStack == null)
                return true; 
              if (itemStack.getItem() == Item.getItemFromBlock((Block)Blocks.stained_glass_pane) && itemStack.getItemDamage() == 14)
                clickQueue.add(slot1); 
            } 
            break;
          case LETTER:
            if (chestName.length() > chestName.indexOf("'") + 1) {
              char letterNeeded = chestName.charAt(chestName.indexOf("'") + 1);
              for (Slot slot1 : invSlots) {
                if (slot1.inventory == Minecraft.getMinecraft().thePlayer.inventory || 
                  slot1.slotNumber < 9 || slot1.slotNumber > 44 || slot1.slotNumber % 9 == 0 || slot1.slotNumber % 9 == 8)
                  continue; 
                ItemStack itemStack = slot1.getStack();
                if (itemStack == null)
                  return true; 
                if (!itemStack.isItemEnchanted() && 
                  StringUtils.stripControlCodes(itemStack.getDisplayName()).charAt(0) == letterNeeded)
                  clickQueue.add(slot1); 
              } 
            } 
            break;
          case COLOR:
            colorNeeded = null;
            for (EnumDyeColor color : EnumDyeColor.values()) {
              String colorName = color.getName().replaceAll("_", " ").toUpperCase();
              if (chestName.contains(colorName)) {
                colorNeeded = color.getUnlocalizedName();
                break;
              } 
            } 
            if (colorNeeded != null)
              for (Slot slot1 : invSlots) {
                if (slot1.inventory == Minecraft.getMinecraft().thePlayer.inventory || 
                  slot1.slotNumber < 9 || slot1.slotNumber > 44 || slot1.slotNumber % 9 == 0 || slot1.slotNumber % 9 == 8)
                  continue; 
                ItemStack itemStack = slot1.getStack();
                if (itemStack == null)
                  return true; 
                if (!itemStack.isItemEnchanted() && 
                  itemStack.getUnlocalizedName().contains(colorNeeded)) // Moze sie szybko spierdolic 
                  clickQueue.add(slot1); 
              }  
            break;
        } 
        return false;
      } 
      private void clickSlot(Slot slot) { // kompletnie nie wiem co to jest 
        if (!ToggleCommand.autoterminals)
          return;
       
        if (windowClicks == 0)
          windowId = Minecraft.getMinecraft().thePlayer.openContainer.windowId; 
        if (testing) {
          Minecraft.getMinecraft().playerController.windowClick(windowId + windowClicks, slot.slotNumber, 0, 1, (EntityPlayer)Minecraft.getMinecraft().thePlayer);
        } else {
          Minecraft.getMinecraft().playerController.windowClick(windowId + windowClicks, slot.slotNumber, 2, 0, (EntityPlayer)Minecraft.getMinecraft().thePlayer);
        } 
        lastClickTime = System.currentTimeMillis();
        // if (Config.terminalPingless) {
        //  windowClicks++;
        //  clickQueue.remove(slot); 
        // } 
        if (ToggleCommand.autoterminals_pingless) {
            windowClicks++;
            clickQueue.remove(slot); 
        }
      }
    }
    