package services;

import models.Payment;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentService implements IService<Payment> {

    private Connection connection;

    public PaymentService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void create(Payment payment) throws SQLException {
        String sql = "INSERT INTO payment (CourseBought, PricePaid, TimePaid, SubjectBoughtId) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, payment.getCourseBought());
            ps.setInt(2, payment.getPricePaid());
            ps.setTimestamp(3, payment.getTimePaid());
            ps.setInt(4, payment.getSubjectBoughtId());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Payment payment) throws SQLException {
        throw new UnsupportedOperationException("Update operation is not supported for PaymentService");
    }

    @Override
    public void delete(int id) throws SQLException {
        throw new UnsupportedOperationException("Delete operation is not supported for PaymentService");
    }

    @Override
    public List<Payment> read() throws SQLException {
        String sql = "SELECT * FROM payment";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Payment> payments = new ArrayList<>();
        while (rs.next()) {
            Payment payment = new Payment();
            payment.setPaymentId(rs.getInt("paymentID"));
            payment.setCourseBought(rs.getString("CourseBought"));
            payment.setPricePaid(rs.getInt("PricePaid"));
            payment.setTimePaid(rs.getTimestamp("TimePaid"));
            payment.setSubject(rs.getString("courseBought"));
            payment.setSubjectBoughtId(rs.getInt("SubjectBoughtId")); // Added this line
            payments.add(payment);
        }
        return payments;
    }

    public boolean isSubjectPurchased(String subjectName) throws SQLException {
        String sql = "SELECT COUNT(*) FROM payment WHERE CourseBought = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, subjectName);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            return count > 0;
        }
    }

    public boolean isSessionPurchased(String sessionName) throws SQLException {
        String sql = "SELECT COUNT(*) FROM payment WHERE CourseBought = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, sessionName);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            return count > 0;
        }
    }
}
