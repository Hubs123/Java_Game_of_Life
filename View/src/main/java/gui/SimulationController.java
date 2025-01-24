package gui;

import gui.exceptions.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import komponentowe.*;
import komponentowe.exceptions.DaoException;
import komponentowe.exceptions.LoadException;
import komponentowe.exceptions.SaveException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class SimulationController implements GameOfLifeListener {
    @FXML
    private GridPane grid;

    @FXML
    private ChoiceBox<String> boardChoice;

    @FXML
    private TextField boardName;

    private GameOfLifeBoard board;
    String path = "./src/main/resources/gui/savedBoard";
    //GameOfLifeBoardDaoFactoryFile factory = new GameOfLifeBoardDaoFactoryFile(path);
    DaoFactory factory = new JdbcGameOfLifeDaoFactoryDataBase();
    private Adapter[][] adapters;
    private final Converter converter = new Converter();
    private App mainApp;
    private static final Logger logger = LoggerFactory.getLogger(SimulationController.class);

    public void setBoard(GameOfLifeBoard board) {
        this.board = board;
        int rows = board.getNumberOfRows();
        int columns = board.getNumberOfColumns();
        board.addListener(this);
        grid.getChildren().clear();
        adapters = new Adapter[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                GameOfLifeCell cell = board.getCell(i, j);
                Adapter adapter = new Adapter(cell);
                adapters[i][j] = adapter;

                Rectangle rectangle = createRectangle(adapter);
                grid.add(rectangle, j, i);

                adapter.getCellProperty().addListener((observable, oldValue, newValue) -> {
                    rectangle.setFill(converter.toColor(newValue));
                });
            }
        }
        updateBoardNames();
        printBoard();
    }

    private Rectangle createRectangle(Adapter adapter) {
        Rectangle cell = new Rectangle(25, 25);
        cell.setStroke(Color.DARKGRAY);
        cell.setFill(converter.toColor(adapter.getCellState()));

        cell.setOnMouseClicked(event -> {
            adapter.setCellState(!adapter.getCellState());
            logger.info(mainApp.bundle.getString("loggerCellStateChange"));
        });

        return cell;
    }

    @Override
    public void boardChanged() {
        if (adapters != null) {
            for (int i = 0; i < board.getNumberOfRows(); i++) {
                for (int j = 0; j < board.getNumberOfColumns(); j++) {
                    GameOfLifeCell cell = board.getCell(i, j);
                    Adapter adapter = adapters[i][j];
                    if (adapter.getCellState() != cell.getCellState()) {
                        adapter.setCellState(cell.getCellState());
                    }
                }
            }
        }
    }

    public void printBoard() {
        if (board != null) {
            int rows = board.getNumberOfRows();
            int columns = board.getNumberOfColumns();
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    Rectangle cell = (Rectangle) grid.getChildren().get(i * columns + j);
                    cell.setFill(board.getCell(i, j).getCellState() ? Color.PURPLE : Color.WHITE);
                }
            }
        }
    }

    public void doStep(ActionEvent event) {
        if (board != null) {
            board.doSimulationStep();
            printBoard();
        }

        logger.info(mainApp.bundle.getString("loggerDoStep"));
    }

    @FXML
    private void saveToFile(ActionEvent event) throws ViewSaveException {
        String fileName = boardName.getText().trim();

        if (fileName.isEmpty()) {
            logger.error(mainApp.bundle.getString("emptyFileName"));
        }

        try (Dao file = factory.getGameOfLifeBoardDao()) {
            board.removeListener(this);
            file.write(fileName, board);
            board.addListener(this);

            ArrayList<String> savedBoards = file.showNameOfBoards();
            if (savedBoards.size() > 10) {
                String oldestBoard = savedBoards.getFirst();
                file.removeBoard(oldestBoard);
            }
            updateBoardNames();
        } catch (SaveException e) {
            ViewSaveException exception = new ViewSaveException(e);
            logger.info(mainApp.bundle.getString("saveException"));
            throw exception;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        logger.info(mainApp.bundle.getString("loggerSaveBoard"));
    }

    @FXML
    private void loadFromFile(ActionEvent event) throws ViewLoadException {
        String fileName = boardChoice.getValue();
        if (fileName == null || fileName.isEmpty()) {
            logger.error(mainApp.bundle.getString("emptyFileName"));
            return;
        }

        try (Dao file = factory.getGameOfLifeBoardDao()) {
            GameOfLifeBoard readBoard = (GameOfLifeBoard) file.read(fileName);
            readBoard.setSimulator(new PlainGameOfLifeSimulator());
            setBoard(readBoard);
            printBoard();
        } catch (LoadException e) {
            ViewLoadException exception = new ViewLoadException(e);
            logger.info(mainApp.bundle.getString("loadException"));
            throw exception;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        logger.info(mainApp.bundle.getString("loggerLoadBoard"));
    }

    public void setMainApp(App app) {
        this.mainApp = app;
    }

    @FXML
    private void returnToMenu(ActionEvent event) throws Exception {
        mainApp.start(mainApp.getStage());
    }

    private void updateBoardNames() throws ViewLoadException {
        try (Dao file = factory.getGameOfLifeBoardDao()) {
            boardChoice.getItems().clear();
            boardChoice.getItems().addAll(file.showNameOfBoards());
            boardChoice.setValue(boardName.getText());
        } catch (DaoException e) {
            ViewLoadException exception = new ViewLoadException(e);
            logger.info(mainApp.bundle.getString("loadException"));
            throw exception;
        }
    }
}