/*
 * Copyright (c) 2016 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */

 package com.menkr.hack.io.keyboard;

import de.neemann.digital.core.SyncAccess;
import de.neemann.digital.lang.Lang;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * A simple keyboard implementation
 */
public class KBDDialog extends JDialog {
    private final JLabel textLabel;
    private int keyCode;

    /**
     * Create a new Instance
     *
     * @param owner     the owner frame
     * @param keyboard  the keyboard node which has opened this dialog
     * @param modelSync used to access the model
     */
    public KBDDialog(Frame owner, KBD keyboard, SyncAccess modelSync) {
        super(owner, "KBD", false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        textLabel = new JLabel(Lang.get("msg_enterText") + "          ");
        textLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        getContentPane().add(textLabel);

        textLabel.setFocusable(true);
        textLabel.setFocusTraversalKeysEnabled(false);
        textLabel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                updateKeyCode(e.getKeyCode());
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
                updateKeyCode(0);
            }

            private void updateKeyCode(int kCode) {
                keyCode = kCode;
                String k;
                k = "" + (char) kCode;
                modelSync.modify(keyboard::hasChanged);
                textLabel.setText(k);
            }
        });
        
        pack();
        setLocationRelativeTo(owner);
        setVisible(true);
    }

    public int getKBDKeyCode() {
        return keyCode;
    }
}
