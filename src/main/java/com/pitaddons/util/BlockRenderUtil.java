package com.pitaddons.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

public class BlockRenderUtil {

    static Minecraft mc = Minecraft.getMinecraft();

    public static void drawBlockFacesFilled(BlockPos pos, int red, int green, int blue, float alpha, float partialTicks) {
        EntityPlayerSP player = mc.thePlayer;
        if (player == null) return;

        double interpolatedX = interpolate(player.prevPosX, player.posX, partialTicks);
        double interpolatedY = interpolate(player.prevPosY, player.posY, partialTicks);
        double interpolatedZ = interpolate(player.prevPosZ, player.posZ, partialTicks);

        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glBegin(GL11.GL_QUADS);
        float redRelative = (float) red / 255;
        float greenRelative = (float) green / 255;
        float blueRelative = (float) blue / 255;
        GL11.glColor4f(redRelative, greenRelative, blueRelative, alpha);

        double blockX = pos.getX();
        double blockY = pos.getY();
        double blockZ = pos.getZ();

        double x = blockX - interpolatedX;
        double y = blockY - interpolatedY;
        double z = blockZ - interpolatedZ;

        // Bottom face
        GL11.glVertex3d(x, y, z);
        GL11.glVertex3d(x + 1, y, z);
        GL11.glVertex3d(x + 1, y, z + 1);
        GL11.glVertex3d(x, y, z + 1);

        // Top face
        GL11.glVertex3d(x, y + 1, z);
        GL11.glVertex3d(x, y + 1, z + 1);
        GL11.glVertex3d(x + 1, y + 1, z + 1);
        GL11.glVertex3d(x + 1, y + 1, z);

        // Front face
        GL11.glVertex3d(x, y, z);
        GL11.glVertex3d(x, y + 1, z);
        GL11.glVertex3d(x + 1, y + 1, z);
        GL11.glVertex3d(x + 1, y, z);

        // Back face
        GL11.glVertex3d(x, y, z + 1);
        GL11.glVertex3d(x + 1, y, z + 1);
        GL11.glVertex3d(x + 1, y + 1, z + 1);
        GL11.glVertex3d(x, y + 1, z + 1);

        // Left face
        GL11.glVertex3d(x, y, z);
        GL11.glVertex3d(x, y, z + 1);
        GL11.glVertex3d(x, y + 1, z + 1);
        GL11.glVertex3d(x, y + 1, z);

        // Right face
        GL11.glVertex3d(x + 1, y, z);
        GL11.glVertex3d(x + 1, y + 1, z);
        GL11.glVertex3d(x + 1, y + 1, z + 1);
        GL11.glVertex3d(x + 1, y, z + 1);

        GL11.glEnd();

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();  // Re-enable depth
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawBlockFacesOutline(BlockPos pos, float red, float green, float blue, float alpha, float width, float partialTicks) {
        EntityPlayerSP player = mc.thePlayer;
        if (player == null) return;

        double interpolatedX = interpolate(player.prevPosX, player.posX, partialTicks);
        double interpolatedY = interpolate(player.prevPosY, player.posY, partialTicks);
        double interpolatedZ = interpolate(player.prevPosZ, player.posZ, partialTicks);

        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glLineWidth(width);
        GL11.glBegin(GL11.GL_LINES);
        GL11.glColor4f(red, green, blue, alpha);

        double blockX = pos.getX();
        double blockY = pos.getY();
        double blockZ = pos.getZ();

        double x = blockX - interpolatedX;
        double y = blockY - interpolatedY;
        double z = blockZ - interpolatedZ;

        // Bottom Layer
        GL11.glVertex3d(x, y, z);
        GL11.glVertex3d(x + 1, y, z);

        GL11.glVertex3d(x + 1, y, z);
        GL11.glVertex3d(x + 1, y, z + 1);

        GL11.glVertex3d(x + 1, y, z + 1);
        GL11.glVertex3d(x, y, z + 1);

        GL11.glVertex3d(x, y, z + 1);
        GL11.glVertex3d(x, y, z);

        // Top Layer
        GL11.glVertex3d(x, y + 1, z);
        GL11.glVertex3d(x + 1, y + 1, z);

        GL11.glVertex3d(x + 1, y + 1, z);
        GL11.glVertex3d(x + 1, y + 1, z + 1);

        GL11.glVertex3d(x + 1, y + 1, z + 1);
        GL11.glVertex3d(x, y + 1, z + 1);

        GL11.glVertex3d(x, y + 1, z + 1);
        GL11.glVertex3d(x, y + 1, z);

        // Sides
        GL11.glVertex3d(x, y, z);
        GL11.glVertex3d(x, y + 1, z);

        GL11.glVertex3d(x + 1, y, z);
        GL11.glVertex3d(x + 1, y + 1, z);

        GL11.glVertex3d(x + 1, y, z + 1);
        GL11.glVertex3d(x + 1, y + 1, z + 1);

        GL11.glVertex3d(x, y, z + 1);
        GL11.glVertex3d(x, y + 1, z + 1);

        GL11.glEnd();

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();  // Re-enable depth
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }


    public static double interpolate(double previous, double current, float partialTicks) {
        return previous + (current - previous) * partialTicks;
    }

}
