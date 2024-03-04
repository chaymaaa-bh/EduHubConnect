package services;

import models.Report;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportService implements IService<Report> {

    private Connection connection;

    public ReportService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void create(Report report) throws SQLException {
        String sql = "INSERT INTO Report (reportedCourse, reporterUser, reportDate, reportType, reportDescription) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, report.getReportedCourse());
        ps.setInt(2, report.getReporterUser());
        ps.setTimestamp(3, report.getReportDate());
        ps.setString(4, report.getReportType());
        ps.setString(5, report.getReportDescription());
        ps.executeUpdate();
        ResultSet generatedKeys = ps.getGeneratedKeys();
        if (generatedKeys.next()) {
            report.setReportId(generatedKeys.getInt(1));
        } else {
            throw new SQLException("Creating report failed, no ID obtained.");
        }
    }

    @Override
    public void update(Report report) throws SQLException {
        String sql = "UPDATE Report SET reportedCourse = ?, reporterUser = ?, reportDate = ?, reportType = ?, reportDescription = ? WHERE reportId = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, report.getReportedCourse());
        ps.setInt(2, report.getReporterUser());
        ps.setTimestamp(3, report.getReportDate());
        ps.setString(4, report.getReportType());
        ps.setString(5, report.getReportDescription());
        ps.setInt(6, report.getReportId());
        ps.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Report WHERE reportId = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public List<Report> read() throws SQLException {
        String sql = "SELECT * FROM Report";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Report> reports = new ArrayList<>();
        while (rs.next()) {
            Report report = new Report();
            report.setReportId(rs.getInt("reportId"));
            report.setReportedCourse(rs.getString("reportedCourse"));
            report.setReporterUser(rs.getInt("reporterUser"));
            report.setReportDate(rs.getTimestamp("reportDate"));
            report.setReportType(rs.getString("reportType"));
            report.setReportDescription(rs.getString("reportDescription"));
            reports.add(report);
        }
        return reports;
    }
}

