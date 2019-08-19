package dbmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection
{
    /**
     * Connect to a sample database
     */
    public static Connection connect(String dbName)
    {
        Connection connection = null;
        try
        {
            // URL de conexao com o database
            String url = "jdbc:sqlite:" + dbName;
            if (!url.endsWith(".db"))
                url += ".db";

            // Cria uma conexao com o database
            connection = DriverManager.getConnection(url);
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return connection;
    }
}
