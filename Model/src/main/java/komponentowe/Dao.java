package komponentowe;

import komponentowe.exceptions.LoadException;
import komponentowe.exceptions.SaveException;

import java.util.ArrayList;

public interface Dao<T> extends AutoCloseable {

    void write(String name, T object) throws Exception;

    T read(String path) throws Exception;

    ArrayList<String> showNameOfBoards() throws LoadException;

    void removeBoard(String boardName) throws SaveException;

    void close();
}
