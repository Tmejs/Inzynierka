package com.pjkurs.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tmejs
 */
public class DBConnector {

    Connection connection;

    public DBConnector() {
    }

    public Boolean connect(String db, String login, String password) throws Exception {
        connection = getConnection(db, login, password);

        return !connection.isClosed();
    }

    public Boolean checkConnection() {
        try {
            return !connection.isClosed();
        } catch (SQLException sQLException) {
            Logger.getLogger("NavigatorUI").log(Level.SEVERE, "Stirng", sQLException);
            return false;
        }
    }

    private static Connection getConnection(String database, String login, String password) throws Exception {
        // This will load the MySQL driver, each DB has its own driver
        Class.forName("com.mysql.jdbc.Driver");
        // Setup the connection with the DB
        return DriverManager
                .getConnection("jdbc:mysql://localhost/" + database + "?"
                        + "user=" + login + "&password=" + password);
    }

    public Boolean getBooleanFunctionValue(String function) {
        try {
            if (checkConnection()) {
                Logger.getLogger("NavigatorUI").log(Level.SEVERE, function);
                CallableStatement cStmt = connection.prepareCall("{? = call " + function + "}");
                cStmt.registerOutParameter(1, java.sql.Types.BOOLEAN);
                cStmt.execute();
                return cStmt.getBoolean(1);
            } else {
                //TODO zachowania po rozłączeniu z bazą danych
                return null;
            }
        } catch (Exception sQLException) {
            Logger.getLogger("NavigatorUI").log(Level.SEVERE, "Stirng", sQLException);
            return null;
        }
    }

    void commit() {
        try {
            connection.commit();
        } catch (SQLException sQLException) {
            Logger.getLogger("NavigatorUI").log(Level.SEVERE, "Stirng", sQLException);
        }
    }

}
