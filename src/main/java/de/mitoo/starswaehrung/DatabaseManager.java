package de.mitoo.starswaehrung;

import java.sql.*;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseManager {

    private static final Logger LOGGER = Logger.getLogger(DatabaseManager.class.getName());
    private String url;
    private String user;
    private String password;
    private Connection connection;

    public DatabaseManager(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public void connect() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            createTable();
            LOGGER.info("");
            LOGGER.info("");
            LOGGER.info("Database Connected ✅");
            LOGGER.info("");
            LOGGER.info("");
            LOGGER.info(" $$$$$$\\    $$\\                                         $$\\      $$\\   $\\ $\\   $$\\                                               ");
            LOGGER.info("$$  __$$\\   $$ |                                        $$ | $\\  $$ |  \\_|\\_|  $$ |                                              ");
            LOGGER.info("$$ /  \\__|$$$$$$\\    $$$$$$\\   $$$$$$\\   $$$$$$$\\       $$ |$$$\\ $$ | $$$$$$\\  $$$$$$$\\   $$$$$$\\  $$\\   $$\\ $$$$$$$\\   $$$$$$\\  ");
            LOGGER.info("\\$$$$$$\\  \\_$$  _|   \\____$$\\ $$  __$$\\ $$  _____|      $$ $$ $$\\$$ | \\____$$\\ $$  __$$\\ $$  __$$\\ $$ |  $$ |$$  __$$\\ $$  __$$\\ ");
            LOGGER.info(" \\____$$\\   $$ |     $$$$$$$ |$$ |  \\__|\\$$$$$$\\        $$$$  _$$$$ | $$$$$$$ |$$ |  $$ |$$ |  \\__|$$ |  $$ |$$ |  $$ |$$ /  $$ |");
            LOGGER.info("$$\\   $$ |  $$ |$$\\ $$  __$$ |$$ |       \\____$$\\       $$$  / \\$$$ |$$  __$$ |$$ |  $$ |$$ |      $$ |  $$ |$$ |  $$ |$$ |  $$ |");
            LOGGER.info("\\$$$$$$  |  \\$$$$  |\\$$$$$$$ |$$ |      $$$$$$$  |      $$  /   \\$$ |\\$$$$$$$ |$$ |  $$ |$$ |      \\$$$$$$  |$$ |  $$ |\\$$$$$$$ |");
            LOGGER.info(" \\______/    \\____/  \\_______|\\__|      \\_______/       \\__/     \\__| \\_______|\\__|  \\__|\\__|       \\______/ \\__|  \\__| \\____$$ |");
            LOGGER.info("                                                                                                                       $$\\   $$ |");
            LOGGER.info("                                                                                                                       \\$$$$$$  |");
            LOGGER.info("                                                                                                                        \\______/ ");
            LOGGER.info("                                                _           __  __ _ _");
            LOGGER.info("                                               | |__ _  _  |  \\  (_) |_ ___  ___ ");
            LOGGER.info("                                               | '_ \\ || | | |\\/| | |  _/ _ \\/ _ \\");
            LOGGER.info("                                               |_.__/\\_, | |_|  |_|_|\\__\\___/\\___/");
            LOGGER.info("                                                      |__/                         ");
            LOGGER.info("");
            LOGGER.info("");
        } catch (ClassNotFoundException e) {
            LOGGER.severe("MySQL JDBC Driver not found.");
            e.printStackTrace();
            throw new SQLException("MySQL JDBC Driver not found.", e);
        } catch (SQLException e) {
            LOGGER.severe("Failed to connect to the database.");
            e.printStackTrace();
            throw e;
        }
    }


    private void createTable() {
        if (connection == null) {
            System.err.println("Keine Verbindung zur Datenbank verfügbar.");
            return;
        }

        String sql = "CREATE TABLE IF NOT EXISTS star_balances (" +
                "player_id VARCHAR(36) NOT NULL PRIMARY KEY, " +
                "stars INT NOT NULL);";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Tabelle 'star_balances' erstellt oder bereits vorhanden.");
        } catch (SQLException e) {
            System.err.println("Fehler beim Erstellen der Tabelle: " + e.getMessage());
        }
    }

    public void savePlayerData(UUID playerUUID, int stars) {
        String insertOrUpdateSQL = "INSERT INTO star_balances (player_id, stars) VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE stars = ?";
        try (PreparedStatement ps = connection.prepareStatement(insertOrUpdateSQL)) {
            ps.setString(1, playerUUID.toString());
            ps.setInt(2, stars);
            ps.setInt(3, stars);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getPlayerStars(UUID playerUUID) {
        String selectSQL = "SELECT stars FROM star_balances WHERE player_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(selectSQL)) {
            ps.setString(1, playerUUID.toString());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("stars");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Defaultwert
    }

    public Connection getConnection() {
        return connection;
    }

    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                LOGGER.info("Connection closed successfully.");
            } catch (SQLException e) {
                LOGGER.severe("Failed to close the connection.");
                e.printStackTrace();
            }
        }
    }
}
