package gui;

import gui.exceptions.ViewResourceException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import komponentowe.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class MenuController {
    @FXML
    private Slider rows;

    @FXML
    private Slider columns;

    @FXML
    private ToggleGroup densityRadioButtons;

    @FXML
    private RadioButton lowDensityRadio;

    @FXML
    private RadioButton mediumDensityRadio;

    @FXML
    private RadioButton highDensityRadio;

    @FXML
    private Label rowsValueLabel;

    @FXML
    private Label columnsValueLabel;

    @FXML
    private ToggleGroup languageRadioButtons;

    @FXML
    private RadioButton plRadioButton;

    @FXML
    private RadioButton enRadioButton;

    private ResourceBundle bundle;
    private GameOfLifeBoard board;
    private String currentLanguage = "en";
    private String currentRegion = "EN";
    private static final Logger logger = LoggerFactory.getLogger(SimulationController.class);
    private App mainApp;

    @FXML
    public void initialize() {
        if ("pl".equals(currentLanguage)) {
            plRadioButton.setSelected(true);
        } else {
            enRadioButton.setSelected(true);
        }

        loadBundle(currentLanguage, currentRegion);

        rows.valueProperty().addListener((observable, oldValue, newValue) ->
                rowsValueLabel.setText(String.format("%d", newValue.intValue())));
        columns.valueProperty().addListener((observable, oldValue, newValue) ->
                columnsValueLabel.setText(String.format("%d", newValue.intValue())));

        mediumDensityRadio.setSelected(true);
    }

    private Density getSelectedDensity() {
        RadioButton selectedButton = (RadioButton) densityRadioButtons.getSelectedToggle();
        if (selectedButton == lowDensityRadio) {
            return Density.MALE;
        } else if (selectedButton == mediumDensityRadio) {
            return Density.SREDNIE;
        } else {
            return Density.DUZE;
        }
    }

    public void setMainApp(App app) {
        this.mainApp = app;
    }

    @FXML
    private void rozpocznijButton(ActionEvent event) throws IOException {
        if (board == null) {
            board = new GameOfLifeBoard((int) rows.getValue(), (int) columns.getValue());
            getSelectedDensity().setBoardDensity(board);
        }

        mainApp.rozpocznij(board, bundle);

        logger.info(mainApp.bundle.getString("loggerSetBoard"));
    }

    @FXML
    private void languageChange() throws ViewResourceException, IOException {
        RadioButton selectedRadioButton = (RadioButton) languageRadioButtons.getSelectedToggle();
        String selectedLanguage = selectedRadioButton.getText();

        String language;
        String region;

        if (selectedLanguage.equals("Polski")) {
            language = "pl";
            region = "PL";
        } else {
            language = "en";
            region = "GB";
        }

        Locale locale = new Locale.Builder().setLanguage(language).setRegion(region).build();

        mainApp.languageChange(locale);

        logger.info(mainApp.bundle.getString("loggerLanguageChange"));
    }

    public void setLanguage(String language, String region) {
        this.currentLanguage = language;
        this.currentRegion = region;

        if (plRadioButton != null) {
            if ("pl".equals(language)) {
                plRadioButton.setSelected(true);
            } else {
                enRadioButton.setSelected(true);
            }
            loadBundle(language, region);
        }
    }

    private void loadBundle(String language, String region) {
        Locale locale = new Locale.Builder().setLanguage(language).setRegion(region).build();

        bundle = ResourceBundle.getBundle("gui.text", locale);
    }

    @FXML
    private void showAuthors(ActionEvent event) {
        ResourceBundle authorsBundle = ResourceBundle.getBundle("gui.Authors");

        StringBuilder authorsInfo = new StringBuilder();
        int i = 1;
        while (authorsBundle.containsKey("author." + i)) {
            authorsInfo.append(authorsBundle.getString("author." + i)).append("\n");
            i++;
        }

        VBox authorsWindow = new VBox();
        authorsWindow.setSpacing(10);
        authorsWindow.setStyle("-fx-padding: 20px;");

        Label authorsLabel = new Label(authorsInfo.toString());

        authorsWindow.getChildren().add(authorsLabel);

        Stage authorsStage = new Stage();
        authorsStage.setTitle(bundle.getString("authorsTitle"));
        authorsStage.setScene(new Scene(authorsWindow, 300, 75));
        authorsStage.show();
    }
}