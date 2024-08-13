package com.menkr.hack.io.screen;

import de.neemann.digital.core.memory.DataField;
import de.neemann.digital.gui.components.graphics.MoveFocusTo;

import javax.swing.*;
import java.awt.*;

/**
 * The dialog used to show the screen contents.
 */
public class ScreenDialog extends JDialog {

    private final ScreenComponent screenComponent;

    /**
     * Creates a new instance of the given size
     *
     * @param parent the parent window
     * @param width  width in pixel
     * @param height height in pixel
     */
    public ScreenDialog(Window parent, int width, int height) {
        super(parent, "Hack Screen", ModalityType.MODELESS);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        screenComponent = new ScreenComponent(width, height);
        getContentPane().add(screenComponent);
        pack();

        setLocationRelativeTo(null);
        setVisible(true);

        MoveFocusTo.addListener(this, parent);
    }

    /**
     * Updates the graphics data
     *
     * @param memory the raw data to use
     * @param bank   the bank to show
     */
    public void updateGraphic(DataField memory, boolean bank) {
        screenComponent.updateGraphic(memory.getData(), bank);
    }
}
