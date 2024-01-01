package com.example.examplemod.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.BufferBuilder;

public class RenderUtils {
    public static void trace(Minecraft mc, Entity e, float partialTicks, int mode) {
        if (mc.getRenderManager().renderViewEntity != null) {
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glLineWidth(2F);

            GL11.glPushMatrix();
            GL11.glDepthMask(false);
            GL11.glColor4d(0, mode == 1 ? 1 : 0, 0, 1);

            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glBegin(GL11.GL_LINES);

            RenderManager r = mc.getRenderManager();

            Vec3d v = new Vec3d(0.0D, 0.0D, 1.0D).rotatePitch(-((float) Math.toRadians((double) mc.player.rotationPitch))).rotateYaw(-((float) Math.toRadians((double) mc.player.rotationYaw)));

            GL11.glVertex3d(v.x, mc.player.getEyeHeight() + v.y, v.z);

            double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * partialTicks;
            double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * partialTicks;
            double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * partialTicks;

            GL11.glVertex3d(x - r.viewerPosX, y - r.viewerPosY + 0.25, z - r.viewerPosZ);

            GL11.glEnd();
            GL11.glDepthMask(true);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glPopMatrix();
        }
    }

    public static void FillLine(Entity entity, AxisAlignedBB box) {
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(2.0F);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);

        RenderGlobal.renderFilledBox(box, 0, 0, 1, 0.3F);
        RenderGlobal.drawSelectionBoundingBox(box, 0, 0, 1, 0.8F);

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static void BlockESP(BlockPos blockPos) {
        GL11.glPushMatrix();

        double x =
                blockPos.getX()
                        - Minecraft.getMinecraft().getRenderManager().viewerPosX;
        double y =
                blockPos.getY()
                        - Minecraft.getMinecraft().getRenderManager().viewerPosY;
        double z =
                blockPos.getZ()
                        - Minecraft.getMinecraft().getRenderManager().viewerPosZ;

        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        GL11.glDepthMask(false);

        RenderGlobal.renderFilledBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0), 2, 0, 1, 0.3F);

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        GL11.glDepthMask(true);

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    public static void renderRainbowCube(float x, float y, float z, float rotation, float scale) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.rotate(rotation, 1.0F, 1.0F, 1.0F); // Угол вращения по всем осям

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        // Задайте цвета куба в рандомном порядке
        float[] colors = {
                1.0F, 0.0F, 0.0F, 1.0F, // Красный
                1.0F, 0.5F, 0.0F, 1.0F, // Оранжевый
                1.0F, 1.0F, 0.0F, 1.0F, // Желтый
                0.0F, 1.0F, 0.0F, 1.0F, // Зеленый
                0.0F, 0.0F, 1.0F, 1.0F, // Синий
                0.5F, 0.0F, 1.0F, 1.0F, // Фиолетовый
        };

        for (int i = 0; i < 6; i++) {
            // Задайте вершины куба
            float x1 = -0.5F * scale;
            float y1 = -0.5F * scale;
            float z1 = -0.5F * scale;
            float x2 = 0.5F * scale;
            float y2 = 0.5F * scale;
            float z2 = 0.5F * scale;

            // Установите цвет для каждой грани
            bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
            bufferBuilder.pos(x1, y1, z1).color(colors[i * 4], colors[i * 4 + 1], colors[i * 4 + 2], colors[i * 4 + 3]).endVertex();
            bufferBuilder.pos(x1, y2, z1).color(colors[i * 4], colors[i * 4 + 1], colors[i * 4 + 2], colors[i * 4 + 3]).endVertex();
            bufferBuilder.pos(x2, y2, z1).color(colors[i * 4], colors[i * 4 + 1], colors[i * 4 + 2], colors[i * 4 + 3]).endVertex();
            bufferBuilder.pos(x2, y1, z1).color(colors[i * 4], colors[i * 4 + 1], colors[i * 4 + 2], colors[i * 4 + 3]).endVertex();
            tessellator.draw();

            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F); // Поверните для следующей грани
        }

        GlStateManager.popMatrix();
    }



    public static void renderBlockSides(BlockPos blockPos, float lineWidth, float r, float g, float b, float alpha) {
        double x = blockPos.getX() - Minecraft.getMinecraft().getRenderManager().viewerPosX;
        double y = blockPos.getY() - Minecraft.getMinecraft().getRenderManager().viewerPosY;
        double z = blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().viewerPosZ;

        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glLineWidth(lineWidth);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);

        RenderGlobal.drawBoundingBox(
                -0.005, -0.005, -0.005,
                1.005, 1.005, 1.005,
                r, g, b, alpha
        );

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    public static void renderRotatingCross(BlockPos blockPos, float lineWidth, float r, float g, float b, float alpha) {
        double x = blockPos.getX() - Minecraft.getMinecraft().getRenderManager().viewerPosX + 0.5;
        double y = blockPos.getY() - Minecraft.getMinecraft().getRenderManager().viewerPosY + 0.5;
        double z = blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().viewerPosZ + 0.5;

        // Угол вращения
        float rotation = (float) (System.currentTimeMillis() % 1800L) / 5.0f; // Скорость вращения уменьшена в 2 раза

        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);

        GL11.glRotatef(rotation, 0.0f, 1.0f, 0.0f); // Вращение по оси Y

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glLineWidth(lineWidth);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);

        GL11.glBegin(GL11.GL_LINES);
        GL11.glColor4f(r, g, b, alpha);

        // Вертикальные линии
        GL11.glVertex3d(0.0, -0.5, 0.0);
        GL11.glVertex3d(0.0, 0.5, 0.0);

        GL11.glVertex3d(0.0, 0.5, 0.0);
        GL11.glVertex3d(0.0, -0.5, 0.0);

        // Горизонтальные линии
        GL11.glVertex3d(-0.5, 0.0, 0.0);
        GL11.glVertex3d(0.5, 0.0, 0.0);

        GL11.glVertex3d(0.5, 0.0, 0.0);
        GL11.glVertex3d(-0.5, 0.0, 0.0);

        // Линия по оси Z
        GL11.glVertex3d(0.0, 0.0, -0.5);
        GL11.glVertex3d(0.0, 0.0, 0.5);

        GL11.glEnd();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    public static boolean isBlockVisibleFromPlayer(double playerX, double playerY, double playerZ, BlockPos blockPos) {
        // Получаем координаты блока
        double blockX = blockPos.getX() + 0.5;
        double blockY = blockPos.getY() + 0.5;
        double blockZ = blockPos.getZ() + 0.5;

        // Вычисляем вектор от игрока к блоку
        double vecX = blockX - playerX;
        double vecY = blockY - playerY;
        double vecZ = blockZ - playerZ;

        // Нормализуем вектор
        double vecLength = Math.sqrt(vecX * vecX + vecY * vecY + vecZ * vecZ);
        vecX /= vecLength;
        vecY /= vecLength;
        vecZ /= vecLength;

        // Получаем направление, в котором смотрит игрок
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        double viewX = -Math.sin(Math.toRadians(player.rotationYaw));
        double viewY = Math.sin(Math.toRadians(player.rotationPitch));
        double viewZ = Math.cos(Math.toRadians(player.rotationYaw));

        // Вычисляем скалярное произведение векторов
        double dotProduct = viewX * vecX + viewY * vecY + viewZ * vecZ;

        // Блок видим, если скалярное произведение положительно
        return dotProduct > 0.0;
    }

    public static void renderEntity(EntityLivingBase entity, int scale, int posX, int posY) {
        GlStateManager.enableTexture2D();
        GlStateManager.depthMask(true);

        GL11.glPushAttrib(GL11.GL_SCISSOR_BIT);
        GL11.glDisable(GL11.GL_SCISSOR_TEST);

        GlStateManager.clear(GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glPopAttrib();

        GlStateManager.enableDepth();
        GlStateManager.disableAlpha();
        GlStateManager.pushMatrix();
        GlStateManager.color(1, 1, 1, 1);

        GuiInventory.drawEntityOnScreen(posX, posY, scale, 1, 1, entity);
        GlStateManager.popMatrix();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
    }

    public static void drawText(String text, float x, float y, float z, int color) {
        GlStateManager.pushMatrix();
        GlStateManager.disableDepth();
        GlStateManager.translate(x, y, z);
        GlStateManager.scale(5F, 5F, 1.0F);
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        Tessellator tessellator = Tessellator.getInstance();
        tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);

        int alpha = (color >> 24) & 255;
        int red = (color >> 16) & 255;
        int green = (color >> 8) & 255;
        int blue = color & 255;

        tessellator.getBuffer().pos(-1, 8, 0).color(red, green, blue, alpha).endVertex();
        tessellator.getBuffer().pos(-1, -1, 0).color(red, green, blue, alpha).endVertex();
        tessellator.getBuffer().pos(text.length() * 4 + 1, -1, 0).color(red, green, blue, alpha).endVertex();
        tessellator.getBuffer().pos(text.length() * 4 + 1, 8, 0).color(red, green, blue, alpha).endVertex();

        tessellator.draw();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
    }


}
