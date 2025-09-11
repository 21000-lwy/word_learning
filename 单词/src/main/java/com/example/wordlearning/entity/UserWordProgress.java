package com.example.wordlearning.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_word_progress")
public class UserWordProgress {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "word_id", nullable = false)
    private Word word;
    
    @Column(name = "correct_count")
    private Integer correctCount = 0;
    
    @Column(name = "wrong_count")
    private Integer wrongCount = 0;
    
    @Column(name = "mastery_level")
    private Integer masteryLevel = 0; // 0-未学习, 1-学习中, 2-已掌握
    
    @Column(name = "last_reviewed")
    private LocalDateTime lastReviewed;
    
    @Column(name = "next_review")
    private LocalDateTime nextReview;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // 构造函数
    public UserWordProgress() {}
    
    public UserWordProgress(User user, Word word) {
        this.user = user;
        this.word = word;
    }
    
    // Getter和Setter方法
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Word getWord() {
        return word;
    }
    
    public void setWord(Word word) {
        this.word = word;
    }
    
    public Integer getCorrectCount() {
        return correctCount;
    }
    
    public void setCorrectCount(Integer correctCount) {
        this.correctCount = correctCount;
    }
    
    public Integer getWrongCount() {
        return wrongCount;
    }
    
    public void setWrongCount(Integer wrongCount) {
        this.wrongCount = wrongCount;
    }
    
    public Integer getMasteryLevel() {
        return masteryLevel;
    }
    
    public void setMasteryLevel(Integer masteryLevel) {
        this.masteryLevel = masteryLevel;
    }
    
    public LocalDateTime getLastReviewed() {
        return lastReviewed;
    }
    
    public void setLastReviewed(LocalDateTime lastReviewed) {
        this.lastReviewed = lastReviewed;
    }
    
    public LocalDateTime getNextReview() {
        return nextReview;
    }
    
    public void setNextReview(LocalDateTime nextReview) {
        this.nextReview = nextReview;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}