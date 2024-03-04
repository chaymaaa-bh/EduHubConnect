package services;

import models.Rating;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RatingService implements IService<Rating> {

    private Connection connection;

    public RatingService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void create(Rating rating) throws SQLException {
        String sql = "INSERT INTO Rating (ratingValue, ratedCourse, ratingUser, ratingDate) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, rating.getRatingValue());
        ps.setInt(2, rating.getRatedCourse());
        ps.setInt(3, rating.getRatingUser());
        ps.setTimestamp(4, rating.getRatingDate());
        ps.executeUpdate();
        ResultSet generatedKeys = ps.getGeneratedKeys();
        if (generatedKeys.next()) {
            rating.setRatingId(generatedKeys.getInt(1));
        } else {
            throw new SQLException("Creating rating failed, no ID obtained.");
        }
    }

    @Override
    public void update(Rating rating) throws SQLException {
        String sql = "UPDATE Rating SET ratingValue = ?, ratedCourse = ?, ratingUser = ?, ratingDate = ? WHERE ratingId = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, rating.getRatingValue());
        ps.setInt(2, rating.getRatedCourse());
        ps.setInt(3, rating.getRatingUser());
        ps.setTimestamp(4, rating.getRatingDate());
        ps.setInt(5, rating.getRatingId());
        ps.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Rating WHERE ratingId = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public List<Rating> read() throws SQLException {
        String sql = "SELECT * FROM Rating";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Rating> ratings = new ArrayList<>();
        while (rs.next()) {
            Rating rating = new Rating();
            rating.setRatingId(rs.getInt("ratingId"));
            rating.setRatingValue(rs.getInt("ratingValue"));
            rating.setRatedCourse(rs.getInt("ratedCourse"));
            rating.setRatingUser(rs.getInt("ratingUser"));
            rating.setRatingDate(rs.getTimestamp("ratingDate"));
            ratings.add(rating);
        }
        return ratings;
    }
}
