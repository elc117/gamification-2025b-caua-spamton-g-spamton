package io.github.univenture;

public class Question {
    private String text;
    private String[] options;
    private int correctAnswerIndex;

    public Question(String text, String[] options, int correctAnswerIndex) {
        this.text = text;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getText() { return text; }
    public String[] getOptions() { return options; }
    public int getCorrectAnswerIndex() { return correctAnswerIndex; }
}
