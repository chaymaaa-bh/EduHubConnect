package services;

import models.Subject;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectService implements IService<Subject> {

    private Connection connection;

    public SubjectService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void create(Subject subject) throws SQLException {
        String sql = "INSERT INTO subject (subjectName, subjectPrice, description, subjectTopic)" +
                "VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, subject.getSubjectName());
            ps.setString(2, subject.getSubjectPrice());
            ps.setString(3, subject.getDescription());
            ps.setString(4, subject.getSubjectTopic());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Subject subject) throws SQLException {
        String sql = "UPDATE subject SET subjectName = ?, subjectPrice = ?, description = ?, subjectTopic = ? WHERE subjectid = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, subject.getSubjectName());
            ps.setString(2, subject.getSubjectPrice());
            ps.setString(3, subject.getDescription());
            ps.setString(4, subject.getSubjectTopic());
            ps.setInt(5, subject.getSubjectId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        try {
            disableForeignKeyChecks();

            String sql = "DELETE FROM subject WHERE subjectid = ?";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        } finally {
            enableForeignKeyChecks();
        }
    }

    @Override
    public List<Subject> read() throws SQLException {
        String sql = "SELECT * FROM subject";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            List<Subject> subjects = new ArrayList<>();
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setSubjectId(rs.getInt("subjectid"));
                subject.setSubjectName(rs.getString("subjectName"));
                subject.setSubjectPrice(rs.getString("subjectPrice"));
                subject.setDescription(rs.getString("description"));
                subject.setSubjectTopic(rs.getString("subjectTopic"));
                subjects.add(subject);
            }
            return subjects;
        }
    }

    private void disableForeignKeyChecks() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("SET foreign_key_checks = 0");
        }
    }

    private void enableForeignKeyChecks() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("SET foreign_key_checks = 1");
        }
    }
}
