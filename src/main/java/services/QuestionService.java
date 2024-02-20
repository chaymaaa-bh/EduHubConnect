package services;

import models.Question;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionService implements IService<Question> {

    private Connection connection;

    public QuestionService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void create(Question question) throws SQLException {
        String sql = "INSERT INTO questions (quiz_id, question_text, difficulty_level, options, correct_answers)" +
                " VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, question.getQuiz_id());
        ps.setString(2, question.getQuestion_text());
        ps.setString(3, question.getDifficulty_level());
        ps.setString(4, question.getOptions());
        ps.setString(5, question.getCorrect_answers());
        ps.executeUpdate();
    }

    @Override
    public void update(Question question) throws SQLException {
        String sql = "UPDATE questions SET quiz_id = ?, question_text = ?, difficulty_level = ?, options = ?, correct_answers = ? WHERE question_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, question.getQuiz_id());
        ps.setString(2, question.getQuestion_text());
        ps.setString(3, question.getDifficulty_level());
        ps.setString(4, question.getOptions());
        ps.setString(5, question.getCorrect_answers());
        ps.setInt(6, question.getQuestion_id());
        ps.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM questions WHERE question_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public List<Question> read() throws SQLException {
        String sql = "SELECT * FROM questions";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Question> questions = new ArrayList<>();
        while (rs.next()) {
            Question q = new Question();
            q.setQuestion_id(rs.getInt("question_id"));
            q.setQuiz_id(rs.getInt("quiz_id"));
            q.setQuestion_text(rs.getString("question_text"));
            q.setDifficulty_level(rs.getString("difficulty_level"));
            q.setOptions(rs.getString("options"));
            q.setCorrect_answers(rs.getString("correct_answers"));
            questions.add(q);
        }
        return questions;
    }
}
