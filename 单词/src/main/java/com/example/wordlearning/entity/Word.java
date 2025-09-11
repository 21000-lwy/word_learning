package com.example.wordlearning.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "words")
public class Word {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String english;
    
    @Column(nullable = false)
    private String chinese;
    
    @Column(length = 1000)
    private String pronunciation;
    
    @Column(length = 2000)
    private String example;
    
    @Column(name = "difficulty_level")
    private Integer difficultyLevel = 1; // 1-5级难度
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    // 构造函数
    public Word() {}
    
    public Word(String english, String chinese, String pronunciation, String example) {
        this.english = english;
        this.chinese = chinese;
        this.pronunciation = pronunciation;
        this.example = example;
    }
    
    // Getter和Setter方法
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}