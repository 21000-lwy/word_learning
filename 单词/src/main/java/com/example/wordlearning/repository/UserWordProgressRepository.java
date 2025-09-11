package com.example.wordlearning.repository;

import com.example.wordlearning.entity.User;
import com.example.wordlearning.entity.UserWordProgress;
import com.example.wordlearning.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserWordProgressRepository extends JpaRepository<UserWordProgress, Long> {
    
    Optional<UserWordProgress> findByUserAndWord(User user, Word word);
    
    List<UserWordProgress> findByUser(User user);
    
    List<UserWordProgress> findByUserAndMasteryLevel(User user, Integer masteryLevel);
    
    @Query("SELECT COUNT(p) FROM UserWordProgress p WHERE p.user = :user")
    Long countByUser(@Param("user") User user);
    
    @Query("SELECT COUNT(p) FROM UserWordProgress p WHERE p.user = :user AND p.masteryLevel = 2")
    Long countMasteredByUser(@Param("user") User user);
    
    @Query("SELECT p FROM UserWordProgress p WHERE p.user = :user AND p.nextReview <= :now ORDER BY p.nextReview ASC")
    List<UserWordProgress> findWordsForReview(@Param("user") User user, @Param("now") LocalDateTime now);
}