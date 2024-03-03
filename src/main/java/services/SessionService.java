package services;

import models.Session;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SessionService implements IService<Session> {

    private Connection connection;

    public SessionService() {
        connection = MyDatabase.getInstance().getConnection();
    }


    @Override
    public void create(Session session) throws SQLException {
        String sql = "INSERT INTO session (sessionName, sessionDescription, subjectId, sessionDriveLink)" +
                "VALUES (?, ?, ?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, session.getSessionName());
            ps.setString(2, session.getSessionDescription());
            ps.setInt(3, session.getSubjectId());
            ps.setString(4, session.getSessionDriveLink());
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    session.setSessionId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating session failed, no ID obtained.");
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void update(Session session) throws SQLException {
        String sql = "UPDATE session SET sessionName = ?, sessionDescription = ?, sessionDriveLink = ? WHERE sessionId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, session.getSessionName());
            ps.setString(2, session.getSessionDescription());
            ps.setInt(3, session.getSessionId());
            ps.setString(4, session.getSessionDriveLink());
            ps.executeUpdate();
        }

    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM session WHERE sessionId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Session> read() throws SQLException {
        String sql = "SELECT * FROM session";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            List<Session> sessions = new ArrayList<>();
            while (rs.next()) {
                Session session = new Session();
                session.setSessionId(rs.getInt("sessionId"));
                session.setSessionName(rs.getString("sessionName"));
                session.setSessionDescription(rs.getString("sessionDescription"));
                session.setSessionDescription(rs.getString("sessionDriverLink"));
                sessions.add(session);
            }
            return sessions;
        }
    }

    public List<Session> readBySubjectId(int subjectId) throws SQLException {
        String sql = "SELECT * FROM session WHERE subjectId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, subjectId);

            try (ResultSet rs = ps.executeQuery()) {
                List<Session> sessions = new ArrayList<>();
                while (rs.next()) {
                    Session session = new Session();
                    session.setSessionId(rs.getInt("sessionId"));
                    session.setSessionName(rs.getString("sessionName"));
                    session.setSessionDescription(rs.getString("sessionDescription"));
                    session.setSubjectId(rs.getInt("subjectId"));
                    session.setSessionDriveLink(rs.getString("sessionDriveLink"));
                    sessions.add(session);
                }
                return sessions;
            }
        }
    }
}