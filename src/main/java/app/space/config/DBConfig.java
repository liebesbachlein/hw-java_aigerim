package app.space.config;

import app.space.entity.Reservation;
import app.space.entity.Space;
import app.space.repo.ReservationRepo;
import app.space.repo.SpaceRepo;
import lombok.Value;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DBConfig {
    private String dbUrl;
    private String dbUsername = "root";
    private String dbPassword = "admin";
    private String dbDriver;
    Connection dbConnection;
    private static final DBConfig instance = new DBConfig();
    private ReservationRepo  reservationRepo;
    private SpaceRepo spaceRepo;

    public DBConfig() {
        try {
            PropertiesConfiguration config = new PropertiesConfiguration();
            config.load("application.properties");
            dbDriver = config.getString("datasource.driver-class-name");
            Class.forName(dbDriver).newInstance();
            dbUrl = config.getString("datasource.url");
            dbUsername = config.getString("datasource.username");
            dbPassword = config.getString("datasource.password");
            dbConnection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            runMigrations();
            initRepositories();
        } catch (SQLException | ConfigurationException | ClassNotFoundException | InstantiationException |
                 IllegalAccessException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void runMigrations() throws SQLException {
        try(Statement statement = dbConnection.createStatement()) {
            statement.execute(Space.migration());
            statement.execute(Reservation.migration());
        } catch (SQLException e) {
            System.err.println("Error occurred while running migrations.");
            throw e;
        }
    }

    public Connection getDbConnection() {
        return dbConnection;
    }

    private void initRepositories() {
         reservationRepo = new ReservationRepo(dbConnection);
         spaceRepo = new SpaceRepo(dbConnection);
    }

    public void close() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static DBConfig getInstance() {
        return instance;
    }

    public ReservationRepo getReservationRepo() {
        return reservationRepo;
    }

    public SpaceRepo getSpaceRepo() {
        return spaceRepo;
    }
}
