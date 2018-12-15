package org.inspirerobotics.sumobots.driverstation.gui.file;

import javafx.fxml.FXMLLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class FXMLFileLoader {

    private static final Logger logger = LogManager.getLogger(FXMLFileLoader.class);

    public static void load(String name, Object controller) {
        load(name, controller, controller);
    }

    public static void load(String name, Object controller, Object root) {
        logger.trace("Loading " + name);
        FXMLLoader fxmlLoader = new FXMLLoader(FXMLFileLoader.class.getClass().getResource("/fxml/" + name));
        fxmlLoader.setController(controller);
        fxmlLoader.setRoot(root);

        if (fxmlLoader.getLocation() == null) {
            RuntimeException e = new GuiFileLoadException("Failed to find file " + name);
            throw e;
        }

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new GuiFileLoadException("Failed to load file: " + name, e);
        }

        logger.debug("Loaded " + name);
    }
}
