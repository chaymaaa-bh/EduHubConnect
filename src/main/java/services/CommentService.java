package services;

import models.Comment;
import utils.MyDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentService {
    private final Connection connection;

    public CommentService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    public void create(Comment comment) throws SQLException {
        String sql = "INSERT INTO comment (C_title, content, date, idP) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, comment.getC_title());
            ps.setString(2, comment.getContent());
            ps.setString(3, comment.getDate());
            ps.executeUpdate();
        }
    }

    public void update(Comment comment) throws SQLException {
        String sql = "UPDATE comment SET C_title = ?, content = ?, date = ? WHERE IdC = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, comment.getC_title());
            ps.setString(2, comment.getContent());
            ps.setString(3, comment.getDate());
            ps.setInt(4, comment.getIdC());
            ps.executeUpdate();
        }
    }

    public void delete(int IdC) throws SQLException {
        String sql = "DELETE FROM comment WHERE IdC = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, IdC);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No comment found with IdC: " + IdC);
            }
        }
    }

    public List<Comment> getAllComments() throws SQLException {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM comment";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    //    public Comment(String c_title, String content, String date, int idC) {
                    Comment comment = new Comment(
                            rs.getString("C_title"),
                            rs.getString("content"),
                            rs.getString("date"),
                            rs.getInt("IdC")
                    );
                    comments.add(comment);
                }
            }
        }
        return comments;
    }

    public List<Comment> getCommentsForPost(int idP) throws SQLException {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM comment WHERE postId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idP);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Comment comment = new Comment(
                            rs.getString("C_title"),
                            rs.getString("content"),
                            rs.getString("date"),
                            rs.getInt("IdC")
                    );
                    comments.add(comment);
                }
            }
        }
        return comments;
    }


}
