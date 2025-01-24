package komponentowe;

import komponentowe.exceptions.*;

import java.io.*;
import java.util.ArrayList;

public class FileGameOfLifeBoardDao implements Dao<GameOfLifeBoard>, AutoCloseable {
    private String path;

    public FileGameOfLifeBoardDao(String name) {
        path = name;
    }

    @Override
    public GameOfLifeBoard read(String name) throws LoadException {
        this.path = name;
        try (FileInputStream fis = new FileInputStream(path);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (GameOfLifeBoard) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new LoadException(e);
        }
    }

    @Override
    public ArrayList<String> showNameOfBoards() throws LoadException {
        File file = new File(path);
        ArrayList<String> boards = new ArrayList<>();

        try {
            for (String name : file.list()) {
                boards.add(name);
            }
            return boards;
        } catch (Exception e) {
            throw new LoadException(e);
        }
    }

    @Override
    public void removeBoard(String boardName) throws SaveException {
        File file = new File(path);

        try {
            file.delete();
        } catch (Exception e) {
            throw new SaveException(e);
        }
    }

    @Override
    public void write(String name, GameOfLifeBoard board) throws SaveException {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(board);
        } catch (IOException e) {
            throw new SaveException(e);
        }
    }


    @Override
    public void close() {
    }
}
