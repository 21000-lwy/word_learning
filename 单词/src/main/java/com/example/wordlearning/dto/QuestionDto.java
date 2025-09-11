package com.example.wordlearning.dto;

import java.util.List;

public class QuestionDto {
    
    private Long wordId;
    private String question;
    private List<String> options;
    private String correctAnswer;
    private String type; // "english_to_chinese" 或 "chinese_to_english"
    private String english;
    private String chinese;
    private String pronunciation;
    private String example;
    private Integer difficultyLevel;
    
    public QuestionDto() {}
    
    public QuestionDto(Long wordId, String question, List<String> options, String correctAnswer, String type) {
        this.wordId = wordId;
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.type = type;
    }
    
    // Getter和Setter方法
    public Long getWordId() {
        return wordId;
    }
    
    public void setWordId(Long wordId) {
        this.wordId = wordId;
    }
    
    public String getQuestion() {
        return question;
    }
    
    public void setQuestion(String question) {
        this.question = question;
    }
    
    public List<String> getOptions() {
        return options;
    }
    
    public void setOptions(List<String> options) {
        this.options = options;
    }
    
    public String getCorrectAnswer() {
        return correctAnswer;
    }
    
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getEnglish() {
        return english;
    }
    
    public void setEnglish(String english) {
        this.english = english;
    }
    
    public String getChinese() {
        return chinese;
    }
    
    public void setChinese(String chinese) {
        this.chinese = chinese;
    }
    
    public String getPronunciation() {
        return pronunciation;
    }
    
    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }
    
    public String getExample() {
        return example;
    }
    
    public void setExample(String example) {
        this.example = example;
    }
    
    public Integer getDifficultyLevel() {
        return difficultyLevel;
    }
    
    public void setDifficultyLevel(Integer difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }
}