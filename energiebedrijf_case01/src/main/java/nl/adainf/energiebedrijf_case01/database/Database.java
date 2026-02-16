package nl.adainf.energiebedrijf_case01.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String DB_NAME = "energiebedrijf_case01";
    private static final String USER = "root";
    private static final String PASS = ""; // XAMPP meestal leeg

    private static final String URL =
            "jdbc:mysql://localhost:3306/" + DB_NAME +
                    "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USER, PASS);
        if (conn.isValid(2)) {
            System.out.println("Verbinding OK!");
        }
        return conn;
    }
}