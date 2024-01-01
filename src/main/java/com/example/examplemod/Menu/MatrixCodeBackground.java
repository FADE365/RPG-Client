package com.example.examplemod.Menu;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MatrixCodeBackground {
    private final FontRenderer fontRenderer;
    private final int screenWidth;
    private final int screenHeight;
    private final Random random = new Random();

    private static class MatrixDigit {
        int x;
        int y;
        char character;
        List<Integer> trailAlphas = new ArrayList<>();
        int maxTrailLength = 10;

        MatrixDigit(int x, int y, char character) {
            this.x = x;
            this.y = y;
            this.character = character;
        }
    }

    private List<MatrixDigit> matrixDigits = new ArrayList<>();
    private int fontSize = 35;

    public MatrixCodeBackground(FontRenderer fontRenderer, int screenWidth, int screenHeight) {
        this.fontRenderer = fontRenderer;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void update() {
        matrixDigits.removeIf(digit -> digit.y >= screenHeight);

        if (random.nextInt(10) < 8) {
            int x = random.nextInt(screenWidth);
            char character = (char) (random.nextInt(10) + '0');
            matrixDigits.add(new MatrixDigit(x, 0, character));
        }

        for (MatrixDigit digit : matrixDigits) {
            digit.trailAlphas.add(255);
            if (digit.trailAlphas.size() > digit.maxTrailLength) {
                digit.trailAlphas.remove(0);
            }

            digit.y += fontRenderer.FONT_HEIGHT;
        }
    }

    public void render() {
        for (MatrixDigit digit : matrixDigits) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(fontSize / 20.0, fontSize / 20.0, 1.0);

            for (int i = 0; i < digit.trailAlphas.size(); i++) {
                int alpha = (int) (digit.trailAlphas.get(i) * ((double) i / digit.maxTrailLength));
                int yOffset = (i + 1) * fontRenderer.FONT_HEIGHT;
                int color = new Color(0, 255, 0, alpha).getRGB();
                fontRenderer.drawString(String.valueOf(digit.character), (int) (digit.x / (fontSize / 20.0)),
                        (int) ((digit.y - yOffset) / (fontSize / 20.0)), color);
            }

            GlStateManager.popMatrix();
        }
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
}
