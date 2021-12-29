package me.Danker.events;

import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class ClickEvent extends Event {
  public static class Right extends ClickEvent {}
  
  public static class Left extends ClickEvent {}
  
  public static class Middle extends ClickEvent {}
}


/* Location:              C:\Users\niko\OneDrive\Pulpit\ShadyAddons-2.5.0.jar!\cheaters\get\banned\events\ClickEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */