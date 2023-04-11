package edu.wpi.teamc.dao.requests;

import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConferenceRoomRequestDAO implements IDao<ConferenceRoomRequest> {
  public List<ConferenceRoomRequest> fetchAllObjects() throws SQLException {
    List<ConferenceRoomRequest> returnList = new ArrayList<>();
    DBConnection db = new DBConnection();
    try {
      Statement stmt = db.getConnection().createStatement();
      // Table Name
      String table = "\"ServiceRequest\".\"conferenceRoomRequest\"";
      // Query
      String query = "SELECT * FROM " + table;

      ResultSet rs = stmt.executeQuery(query);

      while (rs.next()) {
        // Get all the data from the table
        int requestID = rs.getInt("requestID");
        String requester = rs.getString("Requester");
        String conferenceRoom = rs.getString("roomName");
        String additionalNotes = rs.getString("additionalNotes");
        String startTime = rs.getString("startTime");
        String endTime = rs.getString("endTime");
        returnList.add(
            new ConferenceRoomRequest(
                requestID,
                new Requester(requestID, requester),
                new ConferenceRoom(conferenceRoom, "", null),
                additionalNotes,
                startTime,
                endTime));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return returnList;
  }

  public ConferenceRoomRequest addRow(ConferenceRoomRequest orm) {
    DBConnection db = new DBConnection();
    ConferenceRoomRequest request = null;
    try {
      String query =
          "INSERT INTO \"ServiceRequests\".\"conferenceRoomRequest\" (Requester, roomName, status, additionalNotes, startTime, endTime) VALUES (?,?,?,?,?,?)";
      PreparedStatement ps =
          db.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

      ps.setString(1, orm.getRequester().toString());
      ps.setString(2, orm.getRoomName());
      ps.setString(3, orm.getStatus().toString());
      ps.setString(4, orm.getAdditionalNotes());
      ps.setString(5, orm.getStartTime());
      ps.setString(6, orm.getEndTime());
      ps.executeUpdate();

      ResultSet rs = ps.getGeneratedKeys();
      rs.next();
      int requestID = rs.getInt("requestID");
      orm.setRequestID(requestID);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return orm;
  }

  public ConferenceRoomRequest updateRow(ConferenceRoomRequest orm, ConferenceRoomRequest repl)
      throws SQLException {
    DBConnection db = new DBConnection();
    ConferenceRoomRequest crr = null;
    try {
      Statement stmtNode = db.getConnection().createStatement();
      // table names
      String table = "\"ServiceRequests\".\"conferenceRoomRequest\"";
      // queries
      String query =
          "UPDATE "
              + table
              + " SET "
              + "Requester = ?, "
              + "roomName = ?, "
              + "additionalNotes = ?, "
              + "startTime = ?, "
              + "endTime = ? "
              + "WHERE requestID = ?";

      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setString(1, orm.getRequester().toString());
      ps.setString(2, orm.getRoomName());
      ps.setString(3, orm.getAdditionalNotes());
      ps.setString(4, orm.getStartTime());
      ps.setString(5, orm.getEndTime());
      ps.setInt(6, repl.getRequestID());

      ps.execute();
    } catch (Exception e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return repl;
  }

  public ConferenceRoomRequest deleteRow(ConferenceRoomRequest orm) throws SQLException {
    DBConnection db = new DBConnection();
    ConferenceRoomRequest crr = null;
    try {
      Statement stmtNode = db.getConnection().createStatement();
      // table names
      String table = "\"ServiceRequests\".\"conferenceRoomRequest\"";
      // queries
      String query = "DELETE FROM " + table + " WHERE requestID = " + orm.getRequestID();

      stmtNode.executeUpdate(query);

    } catch (Exception e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return orm;
  }
}
