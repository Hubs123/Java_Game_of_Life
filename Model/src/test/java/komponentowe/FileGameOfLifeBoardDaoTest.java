package komponentowe;

import komponentowe.exceptions.LoadException;
import komponentowe.exceptions.SaveException;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FileGameOfLifeBoardDaoTest {

    private static final String TEST_DIR = "./test-boards";
    private static final String TEST_BOARD_PATH = TEST_DIR + "/testBoard";
    private File testDir;

    @BeforeEach
    void setUp() throws IOException {
        testDir = new File(TEST_DIR);
        if (!testDir.exists()) {
            testDir.mkdir();
        }
    }

    @AfterEach
    void tearDown() throws IOException {
        if (testDir.exists()) {
            File[] files = testDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
            testDir.delete();
        }
    }

    @Test
    void testWriteAndRead() throws SaveException, LoadException {
        GameOfLifeBoard board = new GameOfLifeBoard(5, 5);
        board.setSimulator(new PlainGameOfLifeSimulator());

        board.setCellState(1, 1, true);
        board.setCellState(2, 2, true);
        board.setCellState(3, 3, true);

        try (FileGameOfLifeBoardDao dao = new FileGameOfLifeBoardDao(TEST_BOARD_PATH)) {
            dao.write(TEST_BOARD_PATH, board);
            GameOfLifeBoard readBoard = dao.read(TEST_BOARD_PATH);

            assertEquals(board.getNumberOfRows(), readBoard.getNumberOfRows());
            assertEquals(board.getNumberOfColumns(), readBoard.getNumberOfColumns());

            for (int i = 0; i < board.getNumberOfRows(); i++) {
                for (int j = 0; j < board.getNumberOfColumns(); j++) {
                    assertEquals(board.getCellState(i, j), readBoard.getCellState(i, j));
                }
            }
        }
    }

    @Test
    void testReadNonExistentFile() {
        String nonExistentPath = TEST_DIR + "/nonexistent";

        try (FileGameOfLifeBoardDao dao = new FileGameOfLifeBoardDao(nonExistentPath)) {
            assertThrows(LoadException.class, () -> dao.read(nonExistentPath));
        }
    }

    @Test
    void testShowNameOfBoards() throws SaveException, IOException, LoadException {
        File testFile1 = new File(TEST_DIR + "/board1");
        File testFile2 = new File(TEST_DIR + "/board2");
        testFile1.createNewFile();
        testFile2.createNewFile();

        try (FileGameOfLifeBoardDao dao = new FileGameOfLifeBoardDao(TEST_DIR)) {
            ArrayList<String> boards = dao.showNameOfBoards();
            assertEquals(2, boards.size());
            assertTrue(boards.contains("board1"));
            assertTrue(boards.contains("board2"));
        }
    }

    @Test
    void testShowNameOfBoardsEmptyDirectory() throws LoadException {
        try (FileGameOfLifeBoardDao dao = new FileGameOfLifeBoardDao(TEST_DIR)) {
            ArrayList<String> boards = dao.showNameOfBoards();
            assertTrue(boards.isEmpty());
        }
    }

    @Test
    void testRemoveBoard() throws SaveException, IOException {
        File testFile = new File(TEST_BOARD_PATH);
        testFile.createNewFile();
        assertTrue(testFile.exists());

        try (FileGameOfLifeBoardDao dao = new FileGameOfLifeBoardDao(TEST_BOARD_PATH)) {
            dao.removeBoard(TEST_BOARD_PATH);
            assertFalse(testFile.exists());
        }
    }

    @Test
    void testWriteToInvalidPath() {
        String invalidPath = "/invalid/path/board";
        GameOfLifeBoard board = new GameOfLifeBoard(5, 5);

        try (FileGameOfLifeBoardDao dao = new FileGameOfLifeBoardDao(invalidPath)) {
            assertThrows(SaveException.class, () -> dao.write(invalidPath, board));
        }
    }

    @Test
    void testOverwriteExistingBoard() throws SaveException, LoadException, IOException {
        GameOfLifeBoard originalBoard = new GameOfLifeBoard(5, 5);
        GameOfLifeBoard updatedBoard = new GameOfLifeBoard(6, 6);

        originalBoard.setCellState(1, 1, true);
        updatedBoard.setCellState(2, 2, true);

        try (FileGameOfLifeBoardDao dao = new FileGameOfLifeBoardDao(TEST_BOARD_PATH)) {
            dao.write(TEST_BOARD_PATH, originalBoard);
            dao.write(TEST_BOARD_PATH, updatedBoard);
            GameOfLifeBoard readBoard = dao.read(TEST_BOARD_PATH);

            assertEquals(updatedBoard.getNumberOfRows(), readBoard.getNumberOfRows());
            assertEquals(updatedBoard.getNumberOfColumns(), readBoard.getNumberOfColumns());
            assertEquals(updatedBoard.getCellState(2, 2), readBoard.getCellState(2, 2));
        }
    }
}
