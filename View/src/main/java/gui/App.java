package gui;

import gui.exceptions.ViewResourceException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import komponentowe.*;
import komponentowe.exceptions.ResourceException;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class App extends Application {
    public static String lastSelectedLanguage = "en";
    public static String lastSelectedRegion = "EN";

    public static Locale currentLocale = new Locale.Builder().setLanguage(lastSelectedLanguage).setRegion(lastSelectedRegion).build();

    ResourceBundle bundle = ResourceBundle.getBundle("gui.text", currentLocale);

    private Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"), bundle);
        Scene menuScene = new Scene(loader.load());
        MenuController controller = loader.getController();
        controller.setMainApp(this);

        controller.setLanguage(lastSelectedLanguage, lastSelectedRegion);

        stage.setTitle(bundle.getString("title"));
        stage.setScene(menuScene);
        stage.centerOnScreen();
        stage.show();
    }

    public void rozpocznij(GameOfLifeBoard board, ResourceBundle bundle) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("simulation.fxml"), bundle);
        Scene simulationScene = new Scene(loader.load());
        SimulationController simulationController = loader.getController();
        simulationController.setMainApp(this);
        simulationController.setBoard(board);

        stage.setScene(simulationScene);
        stage.centerOnScreen();
        stage.show();
    }

    public Stage getStage() {
        return stage;
    }

    public void languageChange(Locale locale) throws IOException {
        lastSelectedLanguage = locale.getLanguage();
        lastSelectedRegion = locale.getCountry();

        currentLocale = new Locale.Builder().setLanguage(lastSelectedLanguage).setRegion(lastSelectedRegion).build();

        bundle = ResourceBundle.getBundle("gui.text", currentLocale);

        start(stage);
    }
}