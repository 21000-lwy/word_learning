package com.example.wordlearning.dto;

public class AnswerDto {
    
    private Long wordId;
    private String userAnswer;
    private String correctAnswer;
    private boolean isCorrect;
    
    public AnswerDto() {}
    
    // Getter和Setter方法
    public Long getWordId() {
        return wordId;
    }
    
    public void setWordId(Long wordId) {
        this.wordId = wordId;
    }
    
    public String getUserAnswer() {
        return userAnswer;
    }
    
    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }
    
    public String getCorrectAnswer() {
        return correctAnswer;
    }
    
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
    
    public boolean isCorrect() {
        return isCorrect;
    }
    
    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}