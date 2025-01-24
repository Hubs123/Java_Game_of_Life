package komponentowe;


import java.io.File;

public class GameOfLifeBoardDaoFactoryFile implements DaoFactory<GameOfLifeBoard> {

    private final String path;

    public GameOfLifeBoardDaoFactoryFile(String name) {
        this.path = name;
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    @Override
    public FileGameOfLifeBoardDao getGameOfLifeBoardDao() {
        return new FileGameOfLifeBoardDao(path);
    }


}