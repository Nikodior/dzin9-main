/*    */ package me.Danker.utils;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import java.lang.reflect.Field;
/*    */ import java.lang.reflect.Method;
/*    */ 
/*    */ 
/*    */ public class ReflectionUtils
/*    */ {
/*    */   public static boolean invoke(Class<?> _class, String methodName) {
/*    */     try {
/* 12 */       Method method = _class.getDeclaredMethod(methodName, new Class[0]);
/* 13 */       method.setAccessible(true);
/* 14 */       method.invoke(Minecraft.getMinecraft(), new Object[0]);
/* 15 */       return true;
/* 16 */     } catch (Exception exception) {
/* 17 */       return false;
/*    */     } 
/*    */   }
/*    */   public static Object field(Object object, String name) {
/*    */     try {
/* 22 */       Field field = object.getClass().getDeclaredField(name);
/* 23 */       field.setAccessible(true);
/* 24 */       return field.get(object);
/* 25 */     } catch (Exception exception) {
/* 26 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\niko\OneDrive\Pulpit\ShadyAddons-2.5.0.jar!\cheaters\get\banne\\utils\ReflectionUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */