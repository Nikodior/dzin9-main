package me.Danker.utils;

import java.awt.Color;
import me.oringo.oringoclient.OringoClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class RenderUtils {
    public static void draw2D(EntityLivingBase entityLiving, float partialTicks, float lineWidth, Color color) {
        Matrix4f mvMatrix = WorldToScreen.getMatrix(2982);
        Matrix4f projectionMatrix = WorldToScreen.getMatrix(2983);
        GL11.glPushAttrib(8192);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glMatrixMode(5889);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0D, OringoClient.mc.field_71443_c, OringoClient.mc.field_71440_d, 0.0D, -1.0D, 1.0D);
        GL11.glMatrixMode(5888);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glDisable(2929);
        GL11.glBlendFunc(770, 771);
        GlStateManager.func_179098_w();
        GlStateManager.func_179132_a(true);
        GL11.glLineWidth(lineWidth);
        RenderManager renderManager = OringoClient.mc.func_175598_ae();
        AxisAlignedBB bb = entityLiving.func_174813_aQ().func_72317_d(-entityLiving.field_70165_t, -entityLiving.field_70163_u, -entityLiving.field_70161_v).func_72317_d(entityLiving.field_70142_S + (entityLiving.field_70165_t - entityLiving.field_70142_S) * partialTicks, entityLiving.field_70137_T + (entityLiving.field_70163_u - entityLiving.field_70137_T) * partialTicks, entityLiving.field_70136_U + (entityLiving.field_70161_v - entityLiving.field_70136_U) * partialTicks).func_72317_d(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n);
        GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, 1.0F);
        double[][] boxVertices = { { bb.field_72340_a, bb.field_72338_b, bb.field_72339_c }, { bb.field_72340_a, bb.field_72337_e, bb.field_72339_c }, { bb.field_72336_d, bb.field_72337_e, bb.field_72339_c }, { bb.field_72336_d, bb.field_72338_b, bb.field_72339_c }, { bb.field_72340_a, bb.field_72338_b, bb.field_72334_f }, { bb.field_72340_a, bb.field_72337_e, bb.field_72334_f }, { bb.field_72336_d, bb.field_72337_e, bb.field_72334_f }, { bb.field_72336_d, bb.field_72338_b, bb.field_72334_f } };
        float minX = Float.MAX_VALUE;
        float minY = Float.MAX_VALUE;
        float maxX = -1.0F;
        float maxY = -1.0F;
        for (double[] boxVertex : boxVertices) {
            Vector2f screenPos = WorldToScreen.worldToScreen(new Vector3f((float)boxVertex[0], (float)boxVertex[1], (float)boxVertex[2]), mvMatrix, projectionMatrix, OringoClient.mc.field_71443_c, OringoClient.mc.field_71440_d);
            if (screenPos != null) {
                minX = Math.min(screenPos.x, minX);
                minY = Math.min(screenPos.y, minY);
                maxX = Math.max(screenPos.x, maxX);
                maxY = Math.max(screenPos.y, maxY);
            }
        }
        if (minX > 0.0F || minY > 0.0F || maxX <= OringoClient.mc.field_71443_c || maxY <= OringoClient.mc.field_71443_c) {
            GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, 1.0F);
            GL11.glBegin(2);
            GL11.glVertex2f(minX, minY);
            GL11.glVertex2f(minX, maxY);
            GL11.glVertex2f(maxX, maxY);
            GL11.glVertex2f(maxX, minY);
            GL11.glEnd();
        }
        GL11.glEnable(2929);
        GL11.glMatrixMode(5889);
        GL11.glPopMatrix();
        GL11.glMatrixMode(5888);
        GL11.glPopMatrix();
        GL11.glPopAttrib();
    }

    public static void tracerLine(Entity entity, Color color) {
        double x = entity.field_70165_t - (Minecraft.func_71410_x().func_175598_ae()).field_78730_l;
        double y = entity.field_70163_u + (entity.field_70131_O / 2.0F) - (Minecraft.func_71410_x().func_175598_ae()).field_78731_m;
        double z = entity.field_70161_v - (Minecraft.func_71410_x().func_175598_ae()).field_78728_n;
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glLineWidth(1.0F);
        GL11.glDepthMask(false);
        setColor(color);
        GL11.glBegin(1);
        GL11.glVertex3d(0.0D, (Minecraft.func_71410_x()).field_71439_g.func_70047_e(), 0.0D);
        GL11.glVertex3d(x, y, z);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }

    public static void drawRoundRect(int left, int top, int right, int bottom, int radius, int color) {
        left += radius;
        right -= radius;
        if (left < right) {
            int i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            int j = top;
            top = bottom;
            bottom = j;
        }
        float f3 = (color >> 24 & 0xFF) / 255.0F;
        float f = (color >> 16 & 0xFF) / 255.0F;
        float f1 = (color >> 8 & 0xFF) / 255.0F;
        float f2 = (color & 0xFF) / 255.0F;
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a(770, 771, 1, 0);
        GlStateManager.func_179131_c(f, f1, f2, f3);
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        worldrenderer.func_181662_b(left, bottom, 0.0D).func_181675_d();
        worldrenderer.func_181662_b(right, bottom, 0.0D).func_181675_d();
        worldrenderer.func_181662_b(right, top, 0.0D).func_181675_d();
        worldrenderer.func_181662_b(left, top, 0.0D).func_181675_d();
        tessellator.func_78381_a();
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        worldrenderer.func_181662_b((right - radius), (top - radius), 0.0D).func_181675_d();
        worldrenderer.func_181662_b(right, (top - radius), 0.0D).func_181675_d();
        worldrenderer.func_181662_b(right, (bottom + radius), 0.0D).func_181675_d();
        worldrenderer.func_181662_b((right - radius), (bottom + radius), 0.0D).func_181675_d();
        tessellator.func_78381_a();
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        worldrenderer.func_181662_b(left, (top - radius), 0.0D).func_181675_d();
        worldrenderer.func_181662_b((left + radius), (top - radius), 0.0D).func_181675_d();
        worldrenderer.func_181662_b((left + radius), (bottom + radius), 0.0D).func_181675_d();
        worldrenderer.func_181662_b(left, (bottom + radius), 0.0D).func_181675_d();
        tessellator.func_78381_a();
        drawArc(right, bottom + radius, radius, 180);
        drawArc(left, bottom + radius, radius, 90);
        drawArc(right, top - radius, radius, 270);
        drawArc(left, top - radius, radius, 0);
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }

    public static void drawArc(int x, int y, int radius, int angleStart) {
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        worldrenderer.func_181668_a(6, DefaultVertexFormats.field_181705_e);
        GlStateManager.func_179137_b(x, y, 0.0D);
        worldrenderer.func_181662_b(0.0D, 0.0D, 0.0D).func_181675_d();
        int points = 21;
        double i;
        for (i = 0.0D; i < points; i++) {
            double radians = Math.toRadians(i / points * 90.0D + angleStart);
            worldrenderer.func_181662_b(radius * Math.sin(radians), radius * Math.cos(radians), 0.0D).func_181675_d();
        }
        tessellator.func_78381_a();
        GlStateManager.func_179137_b(-x, -y, 0.0D);
    }

    public static void tracerLine(int x, int y, int z, Color color) {
        x = (int)(x + 0.5D - (Minecraft.func_71410_x().func_175598_ae()).field_78730_l);
        y = (int)(y + 0.5D - (Minecraft.func_71410_x().func_175598_ae()).field_78731_m);
        z = (int)(z + 0.5D - (Minecraft.func_71410_x().func_175598_ae()).field_78728_n);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(2.5F);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        setColor(color);
        GL11.glBegin(1);
        GL11.glVertex3d(0.0D, (Minecraft.func_71410_x()).field_71439_g.func_70047_e(), 0.0D);
        GL11.glVertex3d(x, y, z);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }

    public static void setColor(Color c) {
        GL11.glColor4f(c.getRed() / 255.0F, c.getGreen() / 255.0F, c.getBlue() / 255.0F, c
                .getAlpha() / 255.0F);
    }

    public static void entityESPBox(Entity entity, Color color) {
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(1.5F);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        setColor(color);
        Minecraft.func_71410_x().func_175598_ae();
        RenderGlobal.func_181561_a(new AxisAlignedBB(

                (entity.func_174813_aQ()).field_72340_a - entity.field_70165_t + entity.field_70165_t -

                        (Minecraft.func_71410_x().func_175598_ae()).field_78730_l,
                (entity.func_174813_aQ()).field_72338_b - entity.field_70163_u + entity.field_70163_u -

                        (Minecraft.func_71410_x().func_175598_ae()).field_78731_m,
                (entity.func_174813_aQ()).field_72339_c - entity.field_70161_v + entity.field_70161_v -

                        (Minecraft.func_71410_x().func_175598_ae()).field_78728_n,
                (entity.func_174813_aQ()).field_72336_d - entity.field_70165_t + entity.field_70165_t -

                        (Minecraft.func_71410_x().func_175598_ae()).field_78730_l,
                (entity.func_174813_aQ()).field_72337_e - entity.field_70163_u + entity.field_70163_u -

                        (Minecraft.func_71410_x().func_175598_ae()).field_78731_m,
                (entity.func_174813_aQ()).field_72334_f - entity.field_70161_v + entity.field_70161_v -

                        (Minecraft.func_71410_x().func_175598_ae()).field_78728_n));
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }

    public static void blockBox(TileEntity block, Color color) {
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(2.0F);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        setColor(color);
        Minecraft.func_71410_x().func_175598_ae();
        RenderGlobal.func_181561_a(new AxisAlignedBB(

                (block.getRenderBoundingBox()).field_72340_a -

                        (Minecraft.func_71410_x().func_175598_ae()).field_78730_l,
                (block.getRenderBoundingBox()).field_72338_b -

                        (Minecraft.func_71410_x().func_175598_ae()).field_78731_m,
                (block.getRenderBoundingBox()).field_72339_c -

                        (Minecraft.func_71410_x().func_175598_ae()).field_78728_n,
                (block.getRenderBoundingBox()).field_72336_d -

                        (Minecraft.func_71410_x().func_175598_ae()).field_78730_l,
                (block.getRenderBoundingBox()).field_72337_e -

                        (Minecraft.func_71410_x().func_175598_ae()).field_78731_m,
                (block.getRenderBoundingBox()).field_72334_f -

                        (Minecraft.func_71410_x().func_175598_ae()).field_78728_n));
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }

    public static void enableChams() {
        GL11.glEnable(32823);
        GlStateManager.func_179088_q();
        GlStateManager.func_179136_a(1.0F, -1000000.0F);
    }

    public static void disableChams() {
        GL11.glDisable(32823);
        GlStateManager.func_179136_a(1.0F, 1000000.0F);
        GlStateManager.func_179113_r();
    }

    public static void unForceColor() {
        MobRenderUtils.unsetColor();
    }

    public static void renderStarredNametag(Entity entityIn, String str, double x, double y, double z, int maxDistance) {
        double d0 = entityIn.func_70068_e((OringoClient.mc.func_175598_ae()).field_78734_h);
        if (d0 <= (maxDistance * maxDistance)) {
            FontRenderer fontrenderer = OringoClient.mc.func_175598_ae().func_78716_a();
            float f = 1.6F;
            float f1 = 0.016666668F * f;
            GlStateManager.func_179094_E();
            GlStateManager.func_179109_b((float)x + 0.0F, (float)y + entityIn.field_70131_O + 0.5F, (float)z);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GlStateManager.func_179114_b(-(OringoClient.mc.func_175598_ae()).field_78735_i, 0.0F, 1.0F, 0.0F);
            GlStateManager.func_179114_b((OringoClient.mc.func_175598_ae()).field_78732_j, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179152_a(-f1, -f1, f1);
            GlStateManager.func_179140_f();
            GlStateManager.func_179132_a(false);
            GlStateManager.func_179097_i();
            GlStateManager.func_179147_l();
            GlStateManager.func_179120_a(770, 771, 1, 0);
            int i = 0;
            fontrenderer.func_78276_b(str, -fontrenderer.func_78256_a(str) / 2, i, -1);
            GlStateManager.func_179126_j();
            GlStateManager.func_179132_a(true);
            fontrenderer.func_78276_b(str, -fontrenderer.func_78256_a(str) / 2, i, -1);
            GlStateManager.func_179145_e();
            GlStateManager.func_179084_k();
            GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.func_179121_F();
        }
    }

    public static void renderLivingLabel(Entity entityIn, String str, double x, double y, double z, int maxDistance) {
        double d0 = entityIn.func_70068_e((OringoClient.mc.func_175598_ae()).field_78734_h);
        if (d0 <= (maxDistance * maxDistance)) {
            FontRenderer fontrenderer = OringoClient.mc.func_175598_ae().func_78716_a();
            float f = 1.6F;
            float f1 = 0.016666668F * f;
            GlStateManager.func_179094_E();
            GlStateManager.func_179109_b((float)x + 0.0F, (float)y + entityIn.field_70131_O + 0.5F, (float)z);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GlStateManager.func_179114_b(-(OringoClient.mc.func_175598_ae()).field_78735_i, 0.0F, 1.0F, 0.0F);
            GlStateManager.func_179114_b((OringoClient.mc.func_175598_ae()).field_78732_j, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179152_a(-f1, -f1, f1);
            GlStateManager.func_179140_f();
            GlStateManager.func_179132_a(false);
            GlStateManager.func_179097_i();
            GlStateManager.func_179147_l();
            GlStateManager.func_179120_a(770, 771, 1, 0);
            Tessellator tessellator = Tessellator.func_178181_a();
            WorldRenderer worldrenderer = tessellator.func_178180_c();
            int i = 0;
            if (str.equals("deadmau5"))
                i = -10;
            int j = fontrenderer.func_78256_a(str) / 2;
            GlStateManager.func_179090_x();
            worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
            worldrenderer.func_181662_b((-j - 1), (-1 + i), 0.0D).func_181666_a(0.0F, 0.0F, 0.0F, 0.25F).func_181675_d();
            worldrenderer.func_181662_b((-j - 1), (8 + i), 0.0D).func_181666_a(0.0F, 0.0F, 0.0F, 0.25F).func_181675_d();
            worldrenderer.func_181662_b((j + 1), (8 + i), 0.0D).func_181666_a(0.0F, 0.0F, 0.0F, 0.25F).func_181675_d();
            worldrenderer.func_181662_b((j + 1), (-1 + i), 0.0D).func_181666_a(0.0F, 0.0F, 0.0F, 0.25F).func_181675_d();
            tessellator.func_78381_a();
            GlStateManager.func_179098_w();
            fontrenderer.func_78276_b(str, -fontrenderer.func_78256_a(str) / 2, i, 553648127);
            GlStateManager.func_179126_j();
            GlStateManager.func_179132_a(true);
            fontrenderer.func_78276_b(str, -fontrenderer.func_78256_a(str) / 2, i, -1);
            GlStateManager.func_179145_e();
            GlStateManager.func_179084_k();
            GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.func_179121_F();
        }
    }
}
