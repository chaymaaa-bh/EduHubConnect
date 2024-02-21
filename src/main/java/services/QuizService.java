package services;

import models.Quiz;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizService implements IService<Quiz> {

    private Connection connection;

    public QuizService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void create(Quiz quiz) throws SQLException {
        String sql = "INSERT INTO quizzes (quiz_title, quiz_description, quiz_duration, quiz_subject)" +
                " VALUES (?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, quiz.getQuiz_title());
        ps.setString(2, quiz.getQuiz_description());
        ps.setString(3, quiz.getQuiz_duration());
        ps.setString(4, quiz.getQuiz_subject());
        ps.executeUpdate();
    }

    @Override
    public void update(Quiz quiz) throws SQLException {
        String sql = "UPDATE quizzes SET quiz_title = ?, quiz_description = ?, quiz_duration = ?, quiz_subject = ? WHERE quiz_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, quiz.getQuiz_title());
        ps.setString(2, quiz.getQuiz_description());
        ps.setString(3, quiz.getQuiz_duration());
        ps.setString(4, quiz.getQuiz_subject());
        ps.setInt(5, quiz.getQuiz_id());
        ps.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM quizzes WHERE quiz_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public List<Quiz> read() throws SQLException {
        String sql = "SELECT * FROM quizzes";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Quiz> quizzes = new ArrayList<>();
        while (rs.next()) {
            Quiz q = new Quiz();
            q.setQuiz_id(rs.getInt("quiz_id"));
            q.setQuiz_title(rs.getString("quiz_title"));
            q.setQuiz_description(rs.getString("quiz_description"));
            q.setQuiz_duration(rs.getString("quiz_duration"));
            q.setQuiz_subject(rs.getString("quiz_subject"));
            quizzes.add(q);
        }
        return quizzes;
    }
}
