package me.Danker.features;

import me.Danker.commands.ToggleCommand;
import me.Danker.utils.Utils;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import me.Danker.DankersSkyblockMod;
import net.minecraft.util.EnumChatFormatting;

public class Test {

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());
        if (ToggleCommand.test) return; //anti rasizm
        //if (!Utils.inSkyblock) return;
        //if (!message.contains("!")) return;

        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (message.contains("Mimic Killed")) {
            player.addChatMessage(new ChatComponentText(EnumChatFormatting.LIGHT_PURPLE + "From " + //From
            EnumChatFormatting.RED + "[OWNER] " //Ranga
            + EnumChatFormatting.RED + "hypixel" //Nazwa
            + EnumChatFormatting.GRAY + ": Gruby NIGGER jest kurwa DEAD" )); //nazwa/Tekst
            Utils.createTitle(EnumChatFormatting.RED + "KUBIK IS FUCKING DEAD", 3);
            return;
        }
        else if (message.contains("Mimic")) {
            player.addChatMessage(new ChatComponentText(EnumChatFormatting.LIGHT_PURPLE + "From " + //From
            EnumChatFormatting.GOLD + "[MVP"+EnumChatFormatting.DARK_GREEN+"++"+EnumChatFormatting.GOLD +"] " //Ranga
             + EnumChatFormatting.DARK_GREEN + "tlro" + EnumChatFormatting.GRAY + ": NIGGER" )); //nazwa/Tekst
            Utils.createTitle(EnumChatFormatting.RED + "NIGGER", 3);
            return;
        }
        if (message.contains("niglet")) {
            player.addChatMessage(new ChatComponentText(EnumChatFormatting.LIGHT_PURPLE + "From " + //From
            EnumChatFormatting.GOLD + "[MVP"+EnumChatFormatting.DARK_GREEN+"++"+EnumChatFormatting.GOLD +"] " //Ranga
            + EnumChatFormatting.DARK_GREEN + "tlro" + EnumChatFormatting.GRAY + ": n!gg3r" )); //nazwa/Tekst
            Utils.createTitle(EnumChatFormatting.RED + "OOOOH DAAADDDDYYYY", 2);
            return;
        }
        if (message.contains("300 Score Reached")) {
            player.addChatMessage(new ChatComponentText(EnumChatFormatting.LIGHT_PURPLE + "From " + //From
            EnumChatFormatting.RED + "[ADMIN] " //Ranga
            + EnumChatFormatting.RED + "Minikloon" //Nazwa
            + EnumChatFormatting.WHITE + ": Niggerze jebany BLOOD ROOM TERAZ" )); //Tekst
            Utils.createTitle(EnumChatFormatting.RED + "WYPIERDALAJ DO BLOOD ROOMA", 3);
            return;
        }
        if (message.contains("has obtained Wither Key") || message.contains("A Wither Key was picked up")) {
            player.addChatMessage(new ChatComponentText( 
            EnumChatFormatting.RED + "[ADMIN] " //Ranga
            + EnumChatFormatting.RED + "Minikloon" //Nazwa
            + EnumChatFormatting.WHITE + ": Niggerze ślepy" )); //nazwa/Tekst
            Utils.createTitle(EnumChatFormatting.BLACK + "Nigga Key", 5/2);
            return;
        }
        if (message.contains("The BLOOD DOOR has been opened")) {
            player.addChatMessage(new ChatComponentText(EnumChatFormatting.LIGHT_PURPLE + "From " + //From
            EnumChatFormatting.RED + "[ADMIN] " //Ranga
            + EnumChatFormatting.RED + "Minikloon" //Nazwa
            + EnumChatFormatting.WHITE + ": NIGGERS" )); //Tekst
            Utils.createTitle(EnumChatFormatting.RED + "BLOOD OTWARTY", 3);
            return;
        }
        if (message.contains("niko")) {
            player.addChatMessage(new ChatComponentText( 
            EnumChatFormatting.GOLD + "[MVP"+EnumChatFormatting.DARK_GREEN+"++"+EnumChatFormatting.GOLD +"] " //Ranga
             + EnumChatFormatting.DARK_GREEN + "tlro" + EnumChatFormatting.WHITE + ": NIGGER" )); //nazwa/Tekst
            Utils.createTitle(EnumChatFormatting.GOLD + "NikoClient is the best", 3/2);
            return;
        }
    }

}
