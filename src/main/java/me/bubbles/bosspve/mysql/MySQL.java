package me.bubbles.bosspve.mysql;

import me.bubbles.bosspve.util.UtilUserData;
import org.bukkit.configuration.ConfigurationSection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

public class MySQL {

    private final Connection connection;

    public MySQL(ConfigurationSection section) {

        String ip = section.getString("ip");
        int port = section.getInt("port");

        String username = section.getString("username");
        String password = section.getString("password");

        String database = section.getString("database");

        try {
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://" + ip + ":" + port + "/" + database, username, password);

            PreparedStatement statement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS User_Data (" +
                    "uuid CHAR(36)," +
                    "xp INT NOT NULL DEFAULT 0," +
                    "PRIMARY KEY (uuid)" +
                    ")");
            statement.execute();
        } catch (Exception exc) {
            exc.printStackTrace();
            throw new RuntimeException("Failed to connect to database!");
        }

    }

    private ResultSet getResultSet(UUID uuid) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM User_Data WHERE uuid=?");
            statement.setString(1,uuid.toString());
            return statement.executeQuery();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return null;
    }

    public String getString(UUID uuid, Columns key) {
        try {
            ResultSet resultSet = getResultSet(uuid);
            while (resultSet.next()) {
                return resultSet.getString(key.getKey());
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return null;
    }

    public int getInt(UUID uuid, Columns key) {
        try {
            ResultSet resultSet = getResultSet(uuid);
            while (resultSet.next()) {
                return resultSet.getInt(key.getKey());
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return -1;
    }

    public boolean getBoolean(UUID uuid, Columns key) {
        try {
            ResultSet resultSet = getResultSet(uuid);
            while (resultSet.next()) {
                return resultSet.getBoolean(key.getKey());
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return false;
    }

    public UtilUserData getData(UUID uuid) {
        return new UtilUserData(uuid,getInt(uuid,Columns.XP));
    }

    public void save(UtilUserData userData) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM User_Data WHERE uuid=?");
            statement.setString(1,userData.getUUID().toString());
            statement.execute();

            try {
                PreparedStatement setStatement = connection.prepareStatement("INSERT INTO User_Data " +
                        "(uuid, xp) VALUES (?, ?)");

                setStatement.setString(1, userData.getUUID().toString());
                setStatement.setInt(2, userData.getXp());
                setStatement.execute();
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public enum Columns {

        XP("xp");

        private String key;

        Columns(String key) {
            this.key=key;
        }

        public String getKey() {
            return key;
        }

    }

}
