package models;

public class Question {

    private int question_id, question_quiz_id;
    private String question_text, question_difficulty_level, question_option1, question_option2, question_option3, question_correct_answer;

    public Question() {
    }

    public Question(int question_id, int question_quiz_id, String question_text, String  question_difficulty_level, String question_option1, String question_option2, String question_option3, String question_correct_answer) {
        this.question_id = question_id;
        this.question_quiz_id = question_quiz_id;
        this.question_text = question_text;
        this.question_difficulty_level =  question_difficulty_level;
        this.question_option1 = question_option1;
        this.question_option2 = question_option2;
        this.question_option3 = question_option3;
        this.question_correct_answer = question_correct_answer;
    }

    public Question(int question_quiz_id, String question_text, String  question_difficulty_level, String question_option1, String question_option2, String question_option3, String question_correct_answer) {
        this.question_quiz_id = question_quiz_id;
        this.question_text = question_text;
        this.question_difficulty_level =  question_difficulty_level;
        this.question_option1 = question_option1;
        this.question_option2 = question_option2;
        this.question_option3 = question_option3;
        this.question_correct_answer = question_correct_answer;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_quiz_id;
    }

    public int getQuestion_quiz_id() {
        return question_quiz_id;
    }

    public void setQuestion_quiz_id(int question_quiz_id) {
        this.question_quiz_id = question_quiz_id;
    }

    public String getQuestion_text() {
        return question_text;
    }

    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }

    public String getQuestion_difficulty_level() {
        return question_difficulty_level;
    }

    public void setDifficulty_level(String question_difficulty_level) {
        this.question_difficulty_level = question_difficulty_level;
    }

    public String getQuestion_option1() {
        return question_option1;
    }

    public void setQuestion_option1(String question_option1) {
        this.question_option1 = question_option1;
    }

    public String getQuestion_option2() {
        return question_option2;
    }

    public void setQuestion_option2(String question_option2) {
        this.question_option2 = question_option2;
    }

    public String getQuestion_option3() {
        return question_option3;
    }

    public void setQuestion_option3(String question_option3) {
        this.question_option3 = question_option3;
    }

    public String getQuestion_correct_answer() {
        return question_correct_answer;
    }

    public void setQuestion_correct_answer(String question_correct_answer) {
        this.question_correct_answer = question_correct_answer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question_id=" + question_id +
                ", question_quiz_id=" + question_quiz_id +
                ", question_text='" + question_text + '\'' +
                ", question_difficulty_level='" + question_difficulty_level + '\'' +
                ", question_option1='" + question_option1 + '\'' +
                ", question_option2='" + question_option2 + '\'' +
                ", question_option3='" + question_option3 + '\'' +
                ", question_correct_answer='" + question_correct_answer + '\'' +
                '}';
    }
}
