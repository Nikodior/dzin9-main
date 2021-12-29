/*    */ package me.Danker.utils;
/*    */ 
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import net.minecraft.client.settings.KeyBinding;
        import net.minecraft.client.Minecraft;
/*    */ 
/*    */ 
/*    */ public class KeybindUtils
/*    */ {
/* 10 */   public static HashMap<String, KeyBinding> keyBindings = new HashMap<>();
/*    */   
/*    */   public static void register(String name, int key) {
/* 13 */     keyBindings.put(name, new KeyBinding(name, key, "Dzin9"));
/*    */   }
/*    */   
/*    */   public static boolean isPressed(String name) {
/* 17 */     return get(name).isPressed();
/*    */   }
/*    */   
/*    */   public static KeyBinding get(String name) {
/* 21 */     return keyBindings.get(name);
/*    */   }
/*    */   
/*    */   public static void rightClick() {
/* 25 */     if (ReflectionUtils.invoke(Minecraft.getMinecraft().getClass(), "func_147121_ag"))
             {
/* 26 */       ReflectionUtils.invoke(Minecraft.getMinecraft().getClass(), "rightClickMouse");
/*    */     }
/*    */   }
/*    */   
/*    */   public static void leftClick() {
/* 31 */     if (!ReflectionUtils.invoke(Minecraft.getMinecraft().getClass(), "func_147116_af")) {
/* 32 */       ReflectionUtils.invoke(Minecraft.getMinecraft().getClass(), "clickMouse");
/*    */     }
/*    */   }
/*    */   
/*    */   public static void middleClick() {
/* 37 */     if (!ReflectionUtils.invoke(Minecraft.getMinecraft().getClass(), "func_147112_ai"))
/* 38 */       ReflectionUtils.invoke(Minecraft.getMinecraft().getClass(), "middleClickMouse"); 
/*    */   }
/*    */ }


/* Location:              C:\Users\niko\OneDrive\Pulpit\ShadyAddons-2.5.0.jar!\cheaters\get\banne\\utils\KeybindUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */