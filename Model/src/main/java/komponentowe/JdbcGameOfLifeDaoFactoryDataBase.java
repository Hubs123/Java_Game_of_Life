package komponentowe;

import komponentowe.exceptions.SqlException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcGameOfLifeDaoFactoryDataBase implements DaoFactory<GameOfLifeBoard> {

    @Override
    public Dao<GameOfLifeBoard> getGameOfLifeBoardDao() throws SqlException {
        Connection connection = openNewConnection();
        createTables(connection);
        return new JdbcGameOfLifeBoardDao(connection);
    }

    public static Connection openNewConnection() throws SqlException {
        String dbDriver = "org.postgresql.Driver";
        String dbUrl = "jdbc:postgresql://localhost:5432/GameOfLife";
        final String username = "user";
        final String password = "password";
        try {
            Class.forName(dbDriver);
            return DriverManager.getConnection(dbUrl, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            throw new SqlException(e);
        }
    }

    public static void createTables(Connection connection) throws SqlException {
        String tableBoards = "CREATE TABLE IF NOT EXISTS boards (\n"
                + "    boardID    SERIAL PRIMARY KEY,\n"
                + "    boardName  VARCHAR(50) NOT NULL,\n"
                + "    rows       INT NOT NULL,\n"
                + "    columns    INT NOT NULL\n"
                + ");";
        String tableCells = "CREATE TABLE IF NOT EXISTS cells (\n"
                + "    cellID   SERIAL PRIMARY KEY,\n"
                + "    boardID  INT NOT NULL,\n"
                + "    rowIndex INT NOT NULL,\n"
                + "    columnIndex INT NOT NULL,\n"
                + "    value     BOOLEAN NOT NULL,\n"
                + "    FOREIGN KEY (boardID) REFERENCES boards (boardID)\n"
                + ");";
        try {
            Statement statement = connection.createStatement();
            statement.execute(tableBoards);
            statement.execute(tableCells);
        } catch (SQLException e) {
            throw new SqlException(e);
        }
    }
}
