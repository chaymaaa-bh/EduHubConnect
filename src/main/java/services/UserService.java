package services;

import models.User;
import utils.MyDatabase;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IService<User> {

    private Connection connection;

    public UserService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void create(User user) throws SQLException {
        String sql = "INSERT INTO Users (first_name, last_name, email, password, date_of_birth, gender,role,bio,phone_number,profile_picture,module_name) VALUES (?, ?, ?, ?, ?,?,?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        Date sqlDate = Date.valueOf(user.getDate_of_birth());
        ps.setString(1, user.getFirst_name());
        ps.setString(2, user.getLast_name());
        ps.setString(3, user.getEmail());
        ps.setString(4, user.getPassword());
        ps.setDate(5, sqlDate);
        ps.setString(6, user.getGender());
        ps.setString(7, user.getRole());
        ps.setString(8, user.getBio());
        ps.setString(9, user.getPhone_number());
        ps.setString(10, user.getProfile_picture());
        ps.setString(11, user.getModule_name());
        ps.executeUpdate();
        ResultSet generatedKeys = ps.getGeneratedKeys();
        if (generatedKeys.next()) {
            user.setUser_id(generatedKeys.getInt(1));
        } else {
            throw new SQLException("Creating user failed, no ID obtained.");
        }
    }

    @Override
    public void update(User user) throws SQLException {
        String sql = "UPDATE Users SET first_name = ?, last_name = ?, email = ?, password = ?, date_of_birth = ?, gender = ?, role = ?, bio = ?, phone_number = ?, profile_picture = ?, module_name = ? WHERE user_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, user.getFirst_name());
        ps.setString(2, user.getLast_name());
        ps.setString(3, user.getEmail());
        ps.setString(4, user.getPassword());

        // Convert LocalDate to java.sql.Date
        Date sqlDate = Date.valueOf(user.getDate_of_birth());
        ps.setDate(5, sqlDate);

        ps.setString(6, user.getGender());
        ps.setString(7, user.getRole());
        ps.setString(8, user.getBio());
        ps.setString(9, user.getPhone_number());
        ps.setString(10, user.getProfile_picture());
        ps.setString(11, user.getModule_name());
        ps.setInt(12, user.getUser_id()); // Assuming user.getUserId() returns the ID of the user to update

        ps.executeUpdate();
    }


    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Users WHERE user_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }


    @Override
    public List<User> read() throws SQLException {
        String sql = "SELECT * FROM Users";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<User> users = new ArrayList<>();
        while (rs.next()) {
            User user = new User();
            user.setUser_id(rs.getInt("user_id"));
            user.setFirst_name(rs.getString("first_name"));
            user.setLast_name(rs.getString("last_name"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setDate_of_birth(rs.getDate("date_of_birth").toLocalDate()); // Convert java.sql.Date to LocalDate
            user.setGender(rs.getString("gender"));
            user.setRole(rs.getString("role"));
            user.setBio(rs.getString("bio"));
            user.setPhone_number(rs.getString("phone_number"));
            user.setProfile_picture(rs.getString("profile_picture"));
            user.setModule_name(rs.getString("module_name"));
            users.add(user);
        }
        return users;
    }
    public User getUserByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                }
            }
        }
        return null; // Return null if no user found with the provided email
    }

    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        int user_id = rs.getInt("user_id");
        String module_name = rs.getString("module_name");
        String first_name = rs.getString("first_name");
        String last_name = rs.getString("last_name");
        String email = rs.getString("email");
        String password = rs.getString("password");
        LocalDate date_of_birth = rs.getDate("date_of_birth").toLocalDate();
        String gender = rs.getString("gender");
        String role = rs.getString("role");
        String bio = rs.getString("bio");
        String phone_number = rs.getString("phone_number");
        String profile_picture = rs.getString("profile_picture");

        return new User(user_id, module_name, first_name, last_name, email, password, gender, role, bio, phone_number, profile_picture, date_of_birth);
    }


}
