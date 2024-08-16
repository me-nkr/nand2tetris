package com.menkr.hack.io.screen;

import javax.swing.*;
import java.awt.*;

/**
 * The component to show the screen contents.
 */
public class ScreenComponent extends JComponent {

    private static final Color[] PALETTE = createPalette();

    private final int width;
    private final int height;
    private long[] data;

    /**
     * Creates a new instance.
     *
     * @param width  the width in pixels
     * @param height the height in pixels
     */
    public ScreenComponent(int width, int height) {
        this.width = width;
        this.height = height;

        int pw = 640 / width;
        if (pw < 1) pw = 1;
        int ph = 400 / height;
        if (ph < 1) ph = 1;
        int pixSize = (pw + ph) / 2;

        Dimension size = new Dimension(width * pixSize, height * pixSize);
        setPreferredSize(size);
        setOpaque(true);
    }

    /**
     * Updates the screen content
     *
     * @param data the data to show
     * @param bank the bank to show
     */
    public void updateGraphic(long[] data) {
        this.data = data;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (data != null)
            for (int x = 0; x < width; x++) {
                int xPos = x * getWidth() / width;
                int dx = (x + 1) * getWidth() / width - xPos;
                for (int y = 0; y < height; y++) {
                    int p = (int) data[y * width + x];
                    if (p >= PALETTE.length) p = 1;
                    g.setColor(PALETTE[p]);

                    int ypos = y * getHeight() / height;
                    int dy = (y + 1) * getHeight() / height - ypos;

                    g.fillRect(xPos, ypos, dx, dy);
                }
            }
    }

    private static Color[] createPalette() {
        Color[] col = new Color[0x10000];
        for (int i = 0; i < col.length; i++)
            col[i] = Color.BLACK;
        col[0] = Color.WHITE;
        col[1] = Color.BLACK;
        col[2] = Color.RED;
        col[3] = Color.GREEN;
        col[4] = Color.BLUE;
        col[5] = Color.YELLOW;
        col[6] = Color.CYAN;
        col[7] = Color.MAGENTA;
        col[8] = Color.ORANGE;
        col[9] = Color.PINK;

        for (int g = 0; g < 32; g++) {
            int in = 255 - getComp(g, 32);
            col[32 + g] = new Color(in, in, in);
        }

        int index = 64;
        for (int r = 0; r < 4; r++)
            for (int g = 0; g < 4; g++)
                for (int b = 0; b < 4; b++) {
                    col[index] = new Color(getComp(r, 4), getComp(g, 4), getComp(b, 4));
                    index++;
                }

        index = 0x8000;
        for (int r = 0; r < 32; r++)
            for (int g = 0; g < 32; g++)
                for (int b = 0; b < 32; b++) {
                    col[index] = new Color(getComp(r, 32), getComp(g, 32), getComp(b, 32));
                    index++;
                }

        return col;
    }

    private static int getComp(int c, int values) {
        return (255 * c) / (values - 1);
    }
}
