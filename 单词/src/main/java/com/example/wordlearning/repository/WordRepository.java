package com.example.wordlearning.repository;

import com.example.wordlearning.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
    
    List<Word> findByDifficultyLevel(Integer difficultyLevel);
    
    @Query(value = "SELECT * FROM words ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<Word> findRandomWords(@Param("limit") int limit);
    
    @Query("SELECT w FROM Word w WHERE w.english LIKE %:keyword% OR w.chinese LIKE %:keyword%")
    List<Word> findByKeyword(@Param("keyword") String keyword);
}