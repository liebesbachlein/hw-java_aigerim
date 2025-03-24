package app.space.repo;
import app.space.entity.Reservation;
import app.space.entity.Space;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SpaceRepo implements Repo<Space> {
    private final Connection dbConnection;

    public SpaceRepo(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Optional<Space> findById(int id) {
        String query = "SELECT * FROM "
                + Space.tableName
                + " WHERE id="
                + id;
        try(Statement statement = dbConnection.createStatement()) {
            ResultSet res = statement.executeQuery(query);
            while(res.next()) {
                String name = res.getString("name");
                int price = res.getInt("price");
                return Optional.of(new Space(id, name, price));
            }

        } catch (SQLException e) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    public List<Space> getAll() {
        String query = "SELECT * FROM "
                + Space.tableName;
        List<Space> list = new ArrayList<>();
        try(Statement statement = dbConnection.createStatement()) {
            ResultSet res = statement.executeQuery(query);

            while(res.next()) {
                int id = res.getInt("id");
                String name = res.getString("name");
                int price = res.getInt("price");
                list.add(new Space(id, name, price));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }

    public Optional<Space> save(Space item) {
        String query = "INSERT INTO "
                + Space.tableName
                + " (name, price)"
                + " VALUES (?, ?)";
        try(PreparedStatement addItem = dbConnection.prepareStatement(query)) {
            addItem.setString(1, item.getName());
            addItem.setInt(2, item.getPrice());
            addItem.executeUpdate();
        } catch (SQLException e) {
            return Optional.empty();
        }

        return Optional.of(item);
    }

    public boolean delete(int id) {
        String query = "DELETE FROM "
                + Space.tableName
                + " WHERE id=?";
        try(PreparedStatement addItem = dbConnection.prepareStatement(query)) {
            addItem.setInt(1, id);
            addItem.executeUpdate();
        } catch (SQLException e) {
            return false;
        }

        return true;
    }
}
