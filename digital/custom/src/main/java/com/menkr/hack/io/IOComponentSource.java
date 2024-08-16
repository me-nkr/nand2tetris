package com.menkr.hack.io;

import com.menkr.hack.io.screen.Screen;

import de.neemann.digital.draw.library.ComponentManager;
import de.neemann.digital.draw.library.ComponentSource;
import de.neemann.digital.draw.library.ElementLibrary;
import de.neemann.digital.draw.library.InvalidNodeException;
import de.neemann.digital.gui.Main;

/**
 * Adds Hack components form Nand2Tetris to Digital
 */
public class IOComponentSource implements ComponentSource {

    /**
     * Attach your components to the simulator by calling the add methods
     *
     * @param manager the ComponentManager
     * @throws InvalidNodeException InvalidNodeException
     */
    @Override
    public void registerComponents(ComponentManager manager) throws InvalidNodeException {
        
        manager.addComponent("Nand2Tetris", Screen.DESCRIPTION);

    }

    /**
     * Start Digital with this ComponentSource attached to make debugging easier.
     *
     * Do it by either
     *  - running `java -cp "/target/classes:/path/to/Digital.jar" com.menkr.hack.io.IOComponentSource`
     *  - or running the IOComponentSource file with debug of your code editor
     *
     * IMPORTANT: Remove the jar from Digitals settings!!!
     *
     * @param args args
     */
    public static void main(String[] args) {
        new Main.MainBuilder()
                .setLibrary(new ElementLibrary().registerComponentSource(new IOComponentSource()))
                .openLater();
    }
}
