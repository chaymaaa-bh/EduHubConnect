package services;

import models.Post;
import utils.MyDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostService {
    private final Connection connection;

    public PostService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    public void create(Post post) throws SQLException {
        String sql = "INSERT INTO post (content, likes, dislikes) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, post.getContent());
            ps.setInt(2, post.getLikes());
            ps.setInt(3, post.getDislikes());
            ps.executeUpdate();
        }
    }

    public void update(Post post) throws SQLException {
        String sql = "UPDATE post SET content = ?, likes = ?, dislikes = ? WHERE IdP = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, post.getContent());
            ps.setInt(2, post.getLikes());
            ps.setInt(3, post.getDislikes());
            ps.setInt(4, post.getIdP());
            ps.executeUpdate();
        }
    }

    public void delete(int IdP) throws SQLException {
        String sql = "DELETE FROM post WHERE IdP = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, IdP);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No post found with IdP: " + IdP);
            }
        }
    }



    public List<Post> getAllPosts() throws SQLException {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT * FROM post";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Post post = new Post(
                            rs.getInt("IdP"),
                            rs.getString("content"),
                            rs.getInt("likes"),
                            rs.getInt("dislikes")
                    );
                    posts.add(post);
                }
            }
        }
        return posts;
    }
    public List<Post> getPostsSortedByCommentCount(boolean ascending) throws SQLException {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT p.*, COUNT(c.id) AS comment_count " +
                "FROM post p " +
                "LEFT JOIN comment c ON p.IdP = c.post_id " +
                "GROUP BY p.IdP " +
                "ORDER BY comment_count " + (ascending ? "ASC" : "DESC");
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Post post = new Post(
                            rs.getInt("IdP"),
                            rs.getString("content"),
                            rs.getInt("likes"),
                            rs.getInt("dislikes")
                    );
                    posts.add(post);
                }
            }
        }
        return posts;
    }

    public List<Post> getPostsSortedByContentLength() throws SQLException {
        List<Post> posts = new ArrayList<>();
        String query = "SELECT * FROM post ORDER BY LENGTH(content)";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int IdP = resultSet.getInt("IdP");
                String content = resultSet.getString("content");
                int likes = resultSet.getInt("likes");
                int dislikes = resultSet.getInt("dislikes");
                // Create a Post object and add it to the list
                Post post = new Post(IdP, content, likes, dislikes);
                posts.add(post);
            }
        }

        return posts;
    }

    public List<Post> getPostsSortedAlphabetically() throws SQLException {
        List<Post> posts = new ArrayList<>();
        String query = "SELECT * FROM post ORDER BY content";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int IdP = resultSet.getInt("IdP");
                String content = resultSet.getString("content");
                int likes = resultSet.getInt("likes");
                int dislikes = resultSet.getInt("dislikes");
                // Create a Post object and add it to the list
                Post post = new Post(IdP, content, likes, dislikes);
                posts.add(post);
            }
        }

        return posts;
    }






}
