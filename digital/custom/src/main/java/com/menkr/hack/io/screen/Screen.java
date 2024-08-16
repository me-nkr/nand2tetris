package com.menkr.hack.io.screen;

import de.neemann.digital.core.*;
import de.neemann.digital.core.element.Element;
import de.neemann.digital.core.element.ElementAttributes;
import de.neemann.digital.core.element.ElementTypeDescription;
import de.neemann.digital.core.element.Keys;
import de.neemann.digital.core.memory.DataField;
import de.neemann.digital.core.memory.RAMInterface;

import javax.swing.*;

import java.util.concurrent.atomic.AtomicBoolean;

import static de.neemann.digital.core.element.PinInfo.input;


/**
 * Hack Screen.
 *
 * Implementation of Hack Screen Component from Nand2Tetris.
 * Essentially a part of memory that maps each address to 16 pixels of a monochromatic screen.
 */
public class Screen extends Node implements Element, RAMInterface {

    /**
     * The terminal description
     */
    public static final ElementTypeDescription DESCRIPTION
            = new ElementTypeDescription(Screen.class,
            input("A"),
            input("Din"),
            input("str"),
            input("C").setClock())
            .addAttribute(Keys.ROTATE)
            .addAttribute(Keys.LABEL)
            .addAttribute(Keys.GRAPHIC_WIDTH)
            .addAttribute(Keys.GRAPHIC_HEIGHT);

    private final DataField memory;
    private final int width;
    private final int height;

    private ScreenDialog screenDialog;
    private final int size;
    private final String label;
    private final int bits = 1; //
    private final int addrBits;
    private ObservableValue dataOut;
    private ObservableValue addrIn;
    private ObservableValue dataIn;
    private ObservableValue strIn;
    private ObservableValue clkIn;
    private boolean lastClk;
    private int addr;
    private boolean runningInMainFrame;

    /**
     * Creates a new Hack Screen instance
     *
     * @param attr the attributes
     */
    public Screen(ElementAttributes attr) {
        label = attr.getLabel();
        width = attr.get(Keys.GRAPHIC_WIDTH);
        height = attr.get(Keys.GRAPHIC_HEIGHT);
        size = (width * height) / 1; //

        int aBits = 1;
        while (((1 << aBits) < size)) aBits++;

        addrBits = aBits;
        memory = new DataField(size);

        dataOut = new ObservableValue("D", bits)
                .setToHighZ()
                .setPinDescription(DESCRIPTION);
    }

    @Override
    public void setInputs(ObservableValues inputs) throws NodeException {
        addrIn = inputs.get(0).checkBits(addrBits, this).addObserverToValue(this);
        dataIn = inputs.get(1).checkBits(bits, this).addObserverToValue(this); //
        strIn = inputs.get(2).checkBits(1, this).addObserverToValue(this);
        clkIn = inputs.get(3).checkBits(1, this).addObserverToValue(this);
    }

    @Override
    public ObservableValues getOutputs() {
        return dataOut.asList();
    }

    @Override
    public void readInputs() throws NodeException {
        long data = 0;
        boolean clk = clkIn.getBool();
        boolean str;
        if (!lastClk && clk) {
            str = strIn.getBool();
            if (str)
                data = dataIn.getValue();
        } else
            str = false;

        if (str) {
            addr = (int) addrIn.getValue();
            memory.setData(addr, data);
            updateGraphic();
        }

        lastClk = clk;
    }

    @Override
    public void writeOutputs() throws NodeException {
        dataOut.setValue(memory.getDataWord(addr));
    }

    @Override
    public void init(Model model) throws NodeException {
        model.addObserver(event -> runningInMainFrame = model.runningInMainFrame(), ModelEventType.STARTED);
    }

    @Override
    public DataField getMemory() {
        return memory;
    }

    private final AtomicBoolean paintPending = new AtomicBoolean();

    private void updateGraphic() {
        if (runningInMainFrame) {
            if (paintPending.compareAndSet(false, true)) {
                SwingUtilities.invokeLater(() -> {
                    if (screenDialog == null || !screenDialog.isVisible()) {
                        screenDialog = new ScreenDialog(getModel().getWindowPosManager().getMainFrame(), width, height);
                        getModel().getWindowPosManager().register("HackScreen_" + label, screenDialog);
                    }
                    paintPending.set(false);
                    screenDialog.updateGraphic(memory);
                });
            }
        }
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getDataBits() {
        return bits;
    }

    @Override
    public int getAddrBits() {
        return addrBits;
    }

    @Override
    public boolean isProgramMemory() {
        return false;
    }

    @Override
    public void setProgramMemory(DataField dataField) {
        memory.setDataFrom(dataField);
    }
}
