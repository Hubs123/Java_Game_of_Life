package komponentowe;

import komponentowe.exceptions.DaoException;

public interface DaoFactory<T> {
    Dao<T> getGameOfLifeBoardDao() throws DaoException;
}
