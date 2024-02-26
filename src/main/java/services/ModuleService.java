package services;

import models.Module;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModuleService implements IService<Module> {

    private Connection connection;

    public ModuleService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void create(Module module) throws SQLException {
        String sql = "INSERT INTO modules (level_id, module_name) VALUES (?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, module.getLevel_id());
        ps.setString(2, module.getModule_name());
        ps.executeUpdate();
        ResultSet generatedKeys = ps.getGeneratedKeys();
        if (generatedKeys.next()) {
            module.setModule_id(generatedKeys.getInt(1));
        } else {
            throw new SQLException("Creating module failed, no ID obtained.");
        }
    }

    @Override
    public void update(Module module) throws SQLException {
        String sql = "UPDATE modules SET level_id = ?, module_name = ? WHERE module_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, module.getLevel_id());
        ps.setString(2, module.getModule_name());
        ps.setInt(3, module.getModule_id());
        ps.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM modules WHERE module_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public List<Module> read() throws SQLException {
        String sql = "SELECT * FROM modules";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Module> modules = new ArrayList<>();
        while (rs.next()) {
            Module module = new Module();
            module.setModule_id(rs.getInt("module_id"));
            module.setLevel_id(rs.getInt("level_id"));
            module.setModule_name(rs.getString("module_name"));
            modules.add(module);
        }
        return modules;
    }
}
