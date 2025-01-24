package komponentowe;

import komponentowe.exceptions.LoadException;
import komponentowe.exceptions.SaveException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameOfLifeDaoTest {

    String path = "./src/test/resources/file";
    GameOfLifeBoardDaoFactoryFile factory = new GameOfLifeBoardDaoFactoryFile(path);

    @Test
    void testReadAndWrite() {
        GameOfLifeBoard board = new GameOfLifeBoard(5, 5);
        GameOfLifeBoard read;

        try (FileGameOfLifeBoardDao dao = factory.getGameOfLifeBoardDao()) {
            dao.write(path, board);
            read = dao.read(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertEquals(board, read);
    }
}