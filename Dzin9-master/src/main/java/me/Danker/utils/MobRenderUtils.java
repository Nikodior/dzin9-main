package me.Danker.utils; 

import java.awt.Color;
import java.nio.FloatBuffer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.lwjgl.opengl.GL11;

public class MobRenderUtils {
  private static final DynamicTexture empty = new DynamicTexture(16, 16);
  
  protected static final FloatBuffer brightnessBuffer = GLAllocation.createDirectFloatBuffer(4);
  
  static {
    int[] aint = empty.getTextureData();
    for (int i = 0; i < 256; i++)
      aint[i] = -1; 
    empty.updateDynamicTexture();
  }
  
  public static void setColor(Color color) {
    GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    GlStateManager.enableTexture2D();
    GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.defaultTexUnit);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PRIMARY_COLOR);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.defaultTexUnit);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
    GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
    GlStateManager.enableTexture2D();
    GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, OpenGlHelper.GL_INTERPOLATE);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_CONSTANT);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE2_RGB, OpenGlHelper.GL_CONSTANT);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND2_RGB, 770);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.GL_PREVIOUS);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
    brightnessBuffer.position(0);
    brightnessBuffer.put(color.getRed() / 255.0F);
    brightnessBuffer.put(color.getGreen() / 255.0F);
    brightnessBuffer.put(color.getBlue() / 255.0F);
    brightnessBuffer.put(color.getAlpha() / 255.0F);
    brightnessBuffer.flip();
    GL11.glTexEnv(8960, 8705, brightnessBuffer);
    GlStateManager.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
    GlStateManager.enableTexture2D();
    GlStateManager.bindTexture(empty.getGlTextureId());
    GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_PREVIOUS);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.lightmapTexUnit);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.GL_PREVIOUS);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
    GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
  }
  
  public static void unsetColor() {
    GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    GlStateManager.enableTexture2D();
    GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.defaultTexUnit);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PRIMARY_COLOR);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.defaultTexUnit);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_ALPHA, OpenGlHelper.GL_PRIMARY_COLOR);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_ALPHA, 770);
    GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
    GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, 5890);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, 5890);
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    GlStateManager.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
    GlStateManager.disableTexture2D();
    GlStateManager.bindTexture(0);
    GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, 5890);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
    GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, 5890);
    GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
  }
}