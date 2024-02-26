package services;

import models.Level;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LevelService implements IService<Level> {

    private Connection connection;

    public LevelService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void create(Level level) throws SQLException {
        String sql = "INSERT INTO Levels (level_name) VALUES (?)";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, level.getLevel_name());
        ps.executeUpdate();
        ResultSet generatedKeys = ps.getGeneratedKeys();
        if (generatedKeys.next()) {
            level.setLevel_id(generatedKeys.getInt(1));
        } else {
            throw new SQLException("Creating level failed, no ID obtained.");
        }
    }

    @Override
    public void update(Level level) throws SQLException {
        String sql = "UPDATE Levels SET level_name = ? WHERE level_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, level.getLevel_name());
        ps.setInt(2, level.getLevel_id());
        ps.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Levels WHERE level_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public List<Level> read() throws SQLException {
        String sql = "SELECT * FROM Levels";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Level> levels = new ArrayList<>();
        while (rs.next()) {
            Level level = new Level();
            level.setLevel_id(rs.getInt("level_id"));
            level.setLevel_name(rs.getString("level_name"));
            levels.add(level);
        }
        return levels;
    }
}
