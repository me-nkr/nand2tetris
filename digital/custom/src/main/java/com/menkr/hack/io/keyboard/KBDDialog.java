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
import java.util.*;

/**
 * A simple keyboard implementation
 */
public class KBDDialog extends JDialog {
    private final HashMap<Integer, KBDKeyMap> customKeyMap = new HashMap<Integer, KBDKeyMap>(){{
        put(KeyEvent.VK_ENTER, new KBDKeyMap("ENTER", 128));
        put(KeyEvent.VK_BACK_SPACE, new KBDKeyMap("BACKSPACE", 129));
        put(KeyEvent.VK_LEFT, new KBDKeyMap("LEFT", 130));
        put(KeyEvent.VK_UP, new KBDKeyMap("UP", 131));
        put(KeyEvent.VK_RIGHT, new KBDKeyMap("RIGHT", 132));
        put(KeyEvent.VK_DOWN, new KBDKeyMap("DOWN", 133));
        put(KeyEvent.VK_HOME, new KBDKeyMap("HOME", 134));
        put(KeyEvent.VK_END, new KBDKeyMap("END", 135));
        put(KeyEvent.VK_PAGE_UP, new KBDKeyMap("PAGEUP", 136));
        put(KeyEvent.VK_PAGE_DOWN, new KBDKeyMap("PAGEDOWN", 137));
        put(KeyEvent.VK_INSERT, new KBDKeyMap("INSERT", 138));
        put(KeyEvent.VK_DELETE, new KBDKeyMap("DELETE", 139));
        put(KeyEvent.VK_ESCAPE, new KBDKeyMap("ESCAPE", 140));
        put(KeyEvent.VK_F1, new KBDKeyMap("F1", 141));
        put(KeyEvent.VK_F2, new KBDKeyMap("F2", 142));
        put(KeyEvent.VK_F3, new KBDKeyMap("F3", 143));
        put(KeyEvent.VK_F4, new KBDKeyMap("F4", 144));
        put(KeyEvent.VK_F5, new KBDKeyMap("F5", 145));
        put(KeyEvent.VK_F6, new KBDKeyMap("F6", 146));
        put(KeyEvent.VK_F7, new KBDKeyMap("F7", 147));
        put(KeyEvent.VK_F8, new KBDKeyMap("F8", 148));
        put(KeyEvent.VK_F9, new KBDKeyMap("F9", 149));
        put(KeyEvent.VK_F10, new KBDKeyMap("F10", 150));
        put(KeyEvent.VK_F11, new KBDKeyMap("F11", 151));
        put(KeyEvent.VK_F12, new KBDKeyMap("F12", 152));
    }};
    
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
                final int kCode = e.getKeyCode();
                final char kChar = e.getKeyChar();
                
                if (customKeyMap.containsKey(kCode)) updateKeyCode(kCode, customKeyMap.get(kCode).charName);
                if (kChar < 32 || kChar > 126) return;
                else updateKeyCode(kCode, "" + kChar);
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
                final int kCode =  e.getKeyCode();
                final int kChar = e.getKeyChar();
                if (!customKeyMap.containsKey(kCode) && kChar < 32 && kChar > 126) return;
                resetKeyCode();
            }

            private void updateKeyCode(int kCode, String kChar) {
                keyCode = kCode == Character.MIN_VALUE ? 0 : customKeyMap.containsKey(kCode) ? customKeyMap.get(kCode).hackKbdValue : kChar.codePointAt(0);
                String k = kChar;
                modelSync.modify(keyboard::hasChanged);
                textLabel.setText(k);
            }
            
            private void resetKeyCode() {
                updateKeyCode(Character.MIN_VALUE, "");
            }
        });
        
        pack();
        setLocationRelativeTo(owner);
        setVisible(true);
    }

    public int getKBDKeyCode() {
        return keyCode;
    }
    
    private class KBDKeyMap {
        private final String charName;
        private final int hackKbdValue;
        
        public KBDKeyMap(String charName, int hackKbdValue) {
            this.charName = charName;
            this.hackKbdValue = hackKbdValue;
        }
    }
}