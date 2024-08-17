/*
 * Copyright (c) 2016 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package com.menkr.hack.io.keyboard;

import de.neemann.digital.core.Model;
import de.neemann.digital.core.Node;
import de.neemann.digital.core.NodeException;
import de.neemann.digital.core.ObservableValue;
import de.neemann.digital.core.ObservableValues;
import de.neemann.digital.core.element.Element;
import de.neemann.digital.core.element.ElementAttributes;
import de.neemann.digital.core.element.ElementTypeDescription;
import de.neemann.digital.core.element.Keys;

/**
 * The Keyboard component
 */
public class KBD extends Node implements Element {

    /**
     * The keyboard description
     */
    public static final ElementTypeDescription DESCRIPTION
            = new ElementTypeDescription(KBD.class)
            .addAttribute(Keys.ROTATE)
            .addAttribute(Keys.LABEL);

    private final String label;
    private KBDDialog kbdDialog;
    private ObservableValue dataOut;

    /**
     * Creates a new terminal instance
     *
     * @param attributes the attributes
     */
    public KBD(ElementAttributes attributes) {
        dataOut = new ObservableValue("D", 16)
                .setPinDescription(DESCRIPTION);
        label = attributes.getLabel();
    }

    @Override
    public void setInputs(ObservableValues inputs) throws NodeException {}

    @Override
    public ObservableValues getOutputs() {
        return dataOut.asList();
    }

    @Override
    public void readInputs() throws NodeException {}

    @Override
    public void writeOutputs() throws NodeException {
        dataOut.setValue(kbdDialog.getKBDKeyCode());
    }
    
    @Override
    public void init(Model model) throws NodeException {
        kbdDialog = new KBDDialog(model.getWindowPosManager().getMainFrame(), this, model);
        model.getWindowPosManager().register("KBD_" + label, kbdDialog);
    }
}
