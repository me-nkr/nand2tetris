package com.menkr.hack.io.screen;

import javax.swing.*;
import java.awt.*;

/**
 * The component to show the screen contents.
 */
public class ScreenComponent extends JComponent {

    private static final Color[] PALETTE = { Color.WHITE, Color.BLACK };

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

        int pw = 480 / width;
        if (pw < 1) pw = 1;
        int ph = 270 / height;
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
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x+=16) {
                    for (int b = 0; b < 16; b++) {
                        int xPos = (x + b) * getWidth() / width;
                        int dx = (x + b + 1) * getWidth() / width - xPos;
                        int ypos = y * getHeight() / height;
                        int dy = (y + 1) * getHeight() / height - ypos;

                        int p = ((int) data[y * (width / 16) + x / 16] & 1 << b) > 0 ? 1 : 0;

                        if (p >= PALETTE.length) p = 0;
                        g.setColor(PALETTE[p]);
                        g.fillRect(xPos, ypos, dx, dy);
                    }
                }
            }
    }

}
