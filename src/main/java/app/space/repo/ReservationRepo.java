package app.space.repo;

import app.space.entity.Reservation;

import java.sql.*;
import java.util.*;
import java.sql.Date;

public class ReservationRepo implements Repo<Reservation> {
    private final Connection dbConnection;

    public ReservationRepo(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Optional<Reservation> findById(int id) {
        String query = "SELECT * FROM "
                + Reservation.tableName
                + " WHERE id="
                + id;
        try(Statement statement = dbConnection.createStatement()) {
            ResultSet res = statement.executeQuery(query);
            while(res.next()) {
                int spaceId = res.getInt("space_id");
                String ownerName = res.getString("owner_name");
                Date date = res.getDate("date");
                Time startHour = res.getTime("start_hour");
                Time endHour = res.getTime("end_hour");
                return Optional.of(new Reservation(id, ownerName, spaceId, date, startHour, endHour));
            }
        } catch (SQLException e) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    public List<Reservation> findBySpaceId(int spaceId) {
        String query = "SELECT * FROM "
                + Reservation.tableName
                + " WHERE space_id="
                + spaceId;
        List<Reservation> list = new ArrayList<>();
        try(Statement statement = dbConnection.createStatement()) {
            ResultSet res = statement.executeQuery(query);

            while(res.next()) {
                int id = res.getInt("id");
                String ownerName = res.getString("owner_name");
                Date date = res.getDate("date");
                Time startHour = res.getTime("start_hour");
                Time endHour = res.getTime("end_hour");
                list.add(new Reservation(id, ownerName, spaceId, date, startHour, endHour));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }

    public List<Reservation> findBySpaceIdAndDateAndTime(int spaceId, Date date, Time startHour, Time endHour) {
        String query = "SELECT * FROM "
                + Reservation.tableName
                + " WHERE date='" + date + "'"
                + " AND space_id=" + spaceId
                + " AND ("
                + "( HOUR(start_hour)>=" + startHour.getHours() + " AND " +  "HOUR(start_hour)<" + endHour.getHours() + " )"
                + " OR ( " + "HOUR(end_hour)>" + startHour.getHours() + " AND " +  "HOUR(end_hour)<=" + endHour.getHours() + " )"
                + " OR ( " + "HOUR(start_hour)<=" + startHour.getHours() + " AND " +  "HOUR(end_hour)>" + startHour.getHours() + " )"
                + ")";
        List<Reservation> list = new ArrayList<>();
        try(Statement statement = dbConnection.createStatement()) {
            ResultSet res = statement.executeQuery(query);

            while(res.next()) {
                int id = res.getInt("id");
                String ownerName = res.getString("owner_name");
                Time resStartHour = res.getTime("start_hour");
                Time resEndHour = res.getTime("end_hour");
                list.add(new Reservation(id, ownerName, spaceId, date, resStartHour, resEndHour));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }

    public List<Reservation> getAll() {
        String query = "SELECT * FROM "
                + Reservation.tableName;
        List<Reservation> list = new ArrayList<>();
        try(Statement statement = dbConnection.createStatement()) {
            ResultSet res = statement.executeQuery(query);

            while(res.next()) {
                int id = res.getInt("id");
                String ownerName = res.getString("owner_name");
                int spaceId = res.getInt("space_id");
                Date date = res.getDate("date");
                Time startHour = res.getTime("start_hour");
                Time endHour = res.getTime("end_hour");
                list.add(new Reservation(id, ownerName, spaceId, date, startHour, endHour));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }

    public Optional<Reservation> save(Reservation item) {
        String query = "INSERT INTO "
                + Reservation.tableName
                + " (owner_name, space_id, date, start_hour, end_hour)"
                + " VALUES (?, ?, ?, ?, ?)";
        try(PreparedStatement addItem = dbConnection.prepareStatement(query)) {
            addItem.setString(1, item.getOwnerName());
            addItem.setInt(2, item.getSpaceId());
            addItem.setDate(3, item.getDate());
            addItem.setTime(4, item.getStartHour());
            addItem.setTime(5, item.getEndHour());
            addItem.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return Optional.empty();
        }

        return Optional.of(item);
    }

    public boolean delete(int id) {
        String query = "DELETE FROM "
                + Reservation.tableName
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
