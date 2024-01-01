package com.example.examplemod.Menu;

import org.lwjgl.opengl.GL11;

public class RotatingCube {
    private float rotationX = 1.0f;
    private float rotationY = 0.0f;
    private float rotationZ = 0.0f;

    private float rotationX2 = 1.0f;
    private float rotationY2 = 0.0f;
    private float rotationZ2 = 0.0f;
    private float size = 100.0f; // Размер кубика

    public void update() {
        // Увеличиваем углы вращения
        rotationX += 1.0f;
        rotationY += 1.9f;
        rotationZ += 1.0f;

        rotationX2 -= 1.0f;
        rotationY2 -= 1.9f;
        rotationZ2 -= 1.0f;

        if (rotationX >= 360.0f) {
            rotationX -= 360.0f;
        }

        if (rotationY >= 360.0f) {
            rotationY -= 360.0f;
        }

        if (rotationZ >= 360.0f) {
            rotationZ -= 360.0f;
        }
    }

    public void render() {

        GL11.glPushMatrix();
        GL11.glTranslatef(460, 250, 0);
        GL11.glRotatef(rotationX, 1, 0, 0);
        GL11.glRotatef(rotationY, 0, 1, 0);
        GL11.glRotatef(rotationZ, 0, 0, 1);
        GL11.glTranslatef(-size / 2.0f, -size / 2.0f, size * 1.5f);

        GL11.glBegin(GL11.GL_LINES);

        // Линии для передней грани
        GL11.glVertex3f(0.0f, 0.0f, size);
        GL11.glVertex3f(size, 0.0f, size);

        GL11.glVertex3f(size, 0.0f, size);
        GL11.glVertex3f(size, size, size);

        GL11.glVertex3f(size, size, size);
        GL11.glVertex3f(0.0f, size, size);

        GL11.glVertex3f(0.0f, size, size);
        GL11.glVertex3f(0.0f, 0.0f, size);

        // Линии для задней грани
        GL11.glVertex3f(size, 0.0f, 0.0f);
        GL11.glVertex3f(0.0f, 0.0f, 0.0f);

        GL11.glVertex3f(0.0f, 0.0f, 0.0f);
        GL11.glVertex3f(0.0f, size, 0.0f);

        GL11.glVertex3f(0.0f, size, 0.0f);
        GL11.glVertex3f(size, size, 0.0f);

        GL11.glVertex3f(size, size, 0.0f);
        GL11.glVertex3f(size, 0.0f, 0.0f);

        // Линии для верхней грани
        GL11.glVertex3f(0.0f, size, size);
        GL11.glVertex3f(size, size, size);

        GL11.glVertex3f(size, size, size);
        GL11.glVertex3f(size, size, 0.0f);

        GL11.glVertex3f(size, size, 0.0f);
        GL11.glVertex3f(0.0f, size, 0.0f);

        GL11.glVertex3f(0.0f, size, 0.0f);
        GL11.glVertex3f(0.0f, size, size);

        // Линии для нижней грани
        GL11.glVertex3f(0.0f, 0.0f, size);
        GL11.glVertex3f(size, 0.0f, size);

        GL11.glVertex3f(size, 0.0f, size);
        GL11.glVertex3f(size, 0.0f, 0.0f);

        GL11.glVertex3f(size, 0.0f, 0.0f);
        GL11.glVertex3f(0.0f, 0.0f, 0.0f);

        GL11.glVertex3f(0.0f, 0.0f, 0.0f);
        GL11.glVertex3f(0.0f, 0.0f, size);

        // Линии для левой грани
        GL11.glVertex3f(0.0f, 0.0f, 0.0f);
        GL11.glVertex3f(0.0f, 0.0f, size);

        GL11.glVertex3f(0.0f, 0.0f, size);
        GL11.glVertex3f(0.0f, size, size);

        GL11.glVertex3f(0.0f, size, size);
        GL11.glVertex3f(0.0f, size, 0.0f);

        GL11.glVertex3f(0.0f, size, 0.0f);
        GL11.glVertex3f(0.0f, 0.0f, 0.0f);

        // Линии для правой грани
        GL11.glVertex3f(size, 0.0f, size);
        GL11.glVertex3f(size, 0.0f, 0.0f);

        GL11.glVertex3f(size, 0.0f, 0.0f);
        GL11.glVertex3f(size, size, 0.0f);

        GL11.glVertex3f(size, size, 0.0f);
        GL11.glVertex3f(size, size, size);

        GL11.glVertex3f(size, size, size);
        GL11.glVertex3f(size, 0.0f, size);

        GL11.glEnd();

        GL11.glPopMatrix();

        // Второй куб
        GL11.glPushMatrix();
        GL11.glTranslatef(560, 250, 0); // Смещаем второй куб правее
        GL11.glRotatef(rotationX2, 1, 0, 0);
        GL11.glRotatef(rotationY2, 0, 1, 0);
        GL11.glRotatef(rotationZ2, 0, 0, 1);
        GL11.glTranslatef(-size / 2.0f, -size / 2.0f, size * 1.5f);

        GL11.glBegin(GL11.GL_LINES);

        // Линии для передней грани
        GL11.glVertex3f(0.0f, 0.0f, size);
        GL11.glVertex3f(size, 0.0f, size);

        GL11.glVertex3f(size, 0.0f, size);
        GL11.glVertex3f(size, size, size);

        GL11.glVertex3f(size, size, size);
        GL11.glVertex3f(0.0f, size, size);

        GL11.glVertex3f(0.0f, size, size);
        GL11.glVertex3f(0.0f, 0.0f, size);

        // Линии для задней грани
        GL11.glVertex3f(size, 0.0f, 0.0f);
        GL11.glVertex3f(0.0f, 0.0f, 0.0f);

        GL11.glVertex3f(0.0f, 0.0f, 0.0f);
        GL11.glVertex3f(0.0f, size, 0.0f);

        GL11.glVertex3f(0.0f, size, 0.0f);
        GL11.glVertex3f(size, size, 0.0f);

        GL11.glVertex3f(size, size, 0.0f);
        GL11.glVertex3f(size, 0.0f, 0.0f);

        // Линии для верхней грани
        GL11.glVertex3f(0.0f, size, size);
        GL11.glVertex3f(size, size, size);

        GL11.glVertex3f(size, size, size);
        GL11.glVertex3f(size, size, 0.0f);

        GL11.glVertex3f(size, size, 0.0f);
        GL11.glVertex3f(0.0f, size, 0.0f);

        GL11.glVertex3f(0.0f, size, 0.0f);
        GL11.glVertex3f(0.0f, size, size);

        // Линии для нижней грани
        GL11.glVertex3f(0.0f, 0.0f, size);
        GL11.glVertex3f(size, 0.0f, size);

        GL11.glVertex3f(size, 0.0f, size);
        GL11.glVertex3f(size, 0.0f, 0.0f);

        GL11.glVertex3f(size, 0.0f, 0.0f);
        GL11.glVertex3f(0.0f, 0.0f, 0.0f);

        GL11.glVertex3f(0.0f, 0.0f, 0.0f);
        GL11.glVertex3f(0.0f, 0.0f, size);

        // Линии для левой грани
        GL11.glVertex3f(0.0f, 0.0f, 0.0f);
        GL11.glVertex3f(0.0f, 0.0f, size);

        GL11.glVertex3f(0.0f, 0.0f, size);
        GL11.glVertex3f(0.0f, size, size);

        GL11.glVertex3f(0.0f, size, size);
        GL11.glVertex3f(0.0f, size, 0.0f);

        GL11.glVertex3f(0.0f, size, 0.0f);
        GL11.glVertex3f(0.0f, 0.0f, 0.0f);

        // Линии для правой грани
        GL11.glVertex3f(size, 0.0f, size);
        GL11.glVertex3f(size, 0.0f, 0.0f);

        GL11.glVertex3f(size, 0.0f, 0.0f);
        GL11.glVertex3f(size, size, 0.0f);

        GL11.glVertex3f(size, size, 0.0f);
        GL11.glVertex3f(size, size, size);

        GL11.glVertex3f(size, size, size);
        GL11.glVertex3f(size, 0.0f, size);

        GL11.glEnd();

        GL11.glPopMatrix();
    }
}
