package com.example.wordlearning.service;

import com.example.wordlearning.entity.User;
import com.example.wordlearning.entity.UserWordProgress;
import com.example.wordlearning.entity.Word;
import com.example.wordlearning.repository.UserWordProgressRepository;
import com.example.wordlearning.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class WordService {
    
    @Autowired
    private WordRepository wordRepository;
    
    @Autowired
    private UserWordProgressRepository progressRepository;
    
    /**
     * 获取用户的学习统计
     */
    public UserStats getUserStats(User user) {
        Long totalLearned = progressRepository.countByUser(user);
        Long mastered = progressRepository.countMasteredByUser(user);
        Long learning = totalLearned - mastered;
        
        return new UserStats(totalLearned, mastered, learning);
    }
    
    /**
     * 获取下一个要学习的单词
     */
    public Word getNextWordToLearn(User user) {
        // 先获取所有单词
        List<Word> allWords = wordRepository.findAll();
        
        // 找到用户还没学过的单词
        for (Word word : allWords) {
            Optional<UserWordProgress> progress = progressRepository.findByUserAndWord(user, word);
            if (progress.isEmpty()) {
                return word;
            }
        }
        
        // 如果所有单词都学过了，返回随机单词
        if (!allWords.isEmpty()) {
            Random random = new Random();
            return allWords.get(random.nextInt(allWords.size()));
        }
        
        return null;
    }
    
    /**
     * 获取需要复习的单词进度
     */
    public List<UserWordProgress> getWordsForReview(User user) {
        return progressRepository.findWordsForReview(user, LocalDateTime.now());
    }
    
    /**
     * 获取需要复习的单词列表
     */
    public List<Word> getReviewWords(User user) {
        return getWordsForReview(user)
                .stream()
                .map(UserWordProgress::getWord)
                .collect(Collectors.toList());
    }
    
    /**
     * 记录答题结果
     */
    public void recordAnswer(User user, Word word, boolean isCorrect) {
        Optional<UserWordProgress> progressOpt = progressRepository.findByUserAndWord(user, word);
        UserWordProgress progress;
        
        if (progressOpt.isPresent()) {
            progress = progressOpt.get();
        } else {
            progress = new UserWordProgress(user, word);
        }
        
        // 更新答题统计
        if (isCorrect) {
            progress.setCorrectCount(progress.getCorrectCount() + 1);
        } else {
            progress.setWrongCount(progress.getWrongCount() + 1);
        }
        
        // 更新掌握程度
        updateMasteryLevel(progress);
        
        // 设置下次复习时间
        setNextReviewTime(progress, isCorrect);
        
        progress.setLastReviewed(LocalDateTime.now());
        progressRepository.save(progress);
    }
    
    /**
     * 更新掌握程度
     */
    private void updateMasteryLevel(UserWordProgress progress) {
        int correct = progress.getCorrectCount();
        int wrong = progress.getWrongCount();
        
        if (correct >= 5 && (wrong == 0 || correct / (double) wrong >= 3)) {
            progress.setMasteryLevel(2); // 已掌握
        } else if (correct >= 1) {
            progress.setMasteryLevel(1); // 学习中
        } else {
            progress.setMasteryLevel(0); // 未学习
        }
    }
    
    /**
     * 设置下次复习时间
     */
    private void setNextReviewTime(UserWordProgress progress, boolean isCorrect) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextReview;
        
        if (isCorrect) {
            // 答对了，延长复习间隔
            int correctCount = progress.getCorrectCount();
            if (correctCount == 1) {
                nextReview = now.plusHours(1);
            } else if (correctCount == 2) {
                nextReview = now.plusHours(6);
            } else if (correctCount == 3) {
                nextReview = now.plusDays(1);
            } else if (correctCount == 4) {
                nextReview = now.plusDays(3);
            } else {
                nextReview = now.plusDays(7);
            }
        } else {
            // 答错了，缩短复习间隔
            nextReview = now.plusMinutes(10);
        }
        
        progress.setNextReview(nextReview);
    }
    
    /**
     * 获取所有单词
     */
    public List<Word> getAllWords() {
        return wordRepository.findAll();
    }
    
    /**
     * 根据ID获取单词
     */
    public Optional<Word> getWordById(Long id) {
        return wordRepository.findById(id);
    }
    
    /**
     * 获取用户所有学习进度
     */
    public List<UserWordProgress> getUserAllProgress(User user) {
        return progressRepository.findByUser(user);
    }
    
    /**
     * 获取用户学习进度（用于历史页面）
     */
    public List<UserWordProgress> getUserWordProgress(User user) {
        return progressRepository.findByUser(user);
    }
    
    /**
     * 保存单词
     */
    public Word saveWord(Word word) {
        return wordRepository.save(word);
    }
    

    
    /**
     * 删除单词
     */
    public void deleteWord(Long id) {
        wordRepository.deleteById(id);
    }
    
    /**
     * 为用户创建测试学习进度（用于演示复习功能）
     */
    @Transactional
    public void createTestProgressForUser(User user) {
        List<Word> allWords = wordRepository.findAll();
        if (allWords.size() >= 10) {
            // 为前10个单词创建学习进度
            for (int i = 0; i < 10; i++) {
                Word word = allWords.get(i);
                Optional<UserWordProgress> existingProgress = progressRepository.findByUserAndWord(user, word);
                
                if (existingProgress.isEmpty()) {
                    UserWordProgress progress = new UserWordProgress(user, word);
                    // 设置一些学习数据，让这些单词需要复习
                    progress.setCorrectCount(2);
                    progress.setWrongCount(1);
                    progress.setMasteryLevel(1); // 学习中
                    progress.setLastReviewed(LocalDateTime.now().minusHours(2));
                    progress.setNextReview(LocalDateTime.now().minusMinutes(30)); // 设置为需要复习
                    
                    progressRepository.save(progress);
                }
            }
        }
    }
    
    /**
     * 用户统计内部类
     */
    public static class UserStats {
        private Long totalWords;
        private Long totalLearned;
        private Long masteredWords;
        private Long mastered;
        private Long learning;
        
        public UserStats(Long totalLearned, Long mastered, Long learning) {
            this.totalLearned = totalLearned;
            this.mastered = mastered;
            this.learning = learning;
            this.totalWords = totalLearned;
            this.masteredWords = mastered;
        }
        
        // Getter方法
        public Long getTotalWords() { return totalWords; }
        public Long getTotalLearned() { return totalLearned; }
        public Long getMasteredWords() { return masteredWords; }
        public Long getMastered() { return mastered; }
        public Long getLearning() { return learning; }
    }
}