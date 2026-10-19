package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBBDD {
    private static final String URL = "jdbc:mariadb://localhost:3306/asociacion_juegos";
    private static final String USER = "user_asociacion";
    private static final String PASSWORD = "userpassword";

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
