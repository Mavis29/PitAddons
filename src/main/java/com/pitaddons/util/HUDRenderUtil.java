package com.pitaddons.util;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class HUDRenderUtil {

    public static void drawRectangle(float x, float y, float width, float height,
                                     int red, int green, int blue, float alpha) {

        float redPercentage = (float) red / 255;
        float greenPercentage = (float) green / 255;
        float bluePercentage = (float) blue / 255;

        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS); // Save current GL state

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(redPercentage, greenPercentage, bluePercentage, alpha);

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        worldrenderer.pos(x, y + height, 0).endVertex();
        worldrenderer.pos(x + width, y + height, 0).endVertex();
        worldrenderer.pos(x + width, y, 0).endVertex();
        worldrenderer.pos(x, y, 0).endVertex();
        tessellator.draw();

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);

        GL11.glPopAttrib(); // Restore GL state
    }

}
