package org.inspirerobotics.sumobots.driverstation.gui;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import org.inspirerobotics.sumobots.driverstation.gui.style.StyleManager;
import org.inspirerobotics.sumobots.driverstation.gui.style.StyleType;
import org.inspirerobotics.sumobots.driverstation.state.DriverstationState;

public class RootPane extends AnchorPane {

    private final BorderPane borderPane;
    private final StatusPane statusPane;
    private final SettingsMenu settingsMenu;
    private final LogPane logPane;

    public RootPane() {
        this.borderPane = new BorderPane();
        this.statusPane = new StatusPane();
        this.settingsMenu = new SettingsMenu();
        this.logPane = new LogPane();

        borderPane.setBottom(statusPane);
        borderPane.setTop(settingsMenu);
        borderPane.setCenter(logPane);

        GuiUtils.anchorInAnchorPane(borderPane);

        this.getChildren().add(borderPane);

        StyleManager.getInstance().styleChildren(StyleType.DARK);
    }

    public Scene createScene() {
       return new Scene(this);
    }

    public void onStateUpdated(DriverstationState currentState) {
        statusPane.onStateUpdated(currentState);
    }

    BorderPane getBorderPane() {
        return borderPane;
    }

    SettingsMenu getSettingsMenu() {
        return settingsMenu;
    }

    StatusPane getStatusPane() {
        return statusPane;
    }

    public LogPane getLogPane() {
        return logPane;
    }
}
