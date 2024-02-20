package models;

public class Question {

    private int question_id, quiz_id;
    private String question_text, difficulty_level, options, correct_answers;

    public Question() {
    }

    public Question(int question_id, int quiz_id, String question_text, String difficulty_level, String options, String correct_answers) {
        this.question_id = question_id;
        this.quiz_id = quiz_id;
        this.question_text = question_text;
        this.difficulty_level = difficulty_level;
        this.options = options;
        this.correct_answers = correct_answers;
    }

    public Question(int quiz_id, String question_text, String difficulty_level, String options, String correct_answers) {
        this.quiz_id = quiz_id;
        this.question_text = question_text;
        this.difficulty_level = difficulty_level;
        this.options = options;
        this.correct_answers = correct_answers;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public String getQuestion_text() {
        return question_text;
    }

    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }

    public String getDifficulty_level() {
        return difficulty_level;
    }

    public void setDifficulty_level(String difficulty_level) {
        this.difficulty_level = difficulty_level;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getCorrect_answers() {
        return correct_answers;
    }

    public void setCorrect_answers(String correct_answers) {
        this.correct_answers = correct_answers;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question_id=" + question_id +
                ", quiz_id=" + quiz_id +
                ", question_text='" + question_text + '\'' +
                ", difficulty_level='" + difficulty_level + '\'' +
                ", options='" + options + '\'' +
                ", correct_answers='" + correct_answers + '\'' +
                '}';
    }
}
