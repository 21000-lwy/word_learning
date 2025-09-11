package com.example.wordlearning.controller;

import com.example.wordlearning.dto.AnswerDto;
import com.example.wordlearning.dto.QuestionDto;
import com.example.wordlearning.entity.User;
import com.example.wordlearning.entity.UserWordProgress;
import com.example.wordlearning.entity.Word;
import com.example.wordlearning.service.UserService;
import com.example.wordlearning.service.WordService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/learning")
public class LearningController {
    
    @Autowired
    private WordService wordService;
    
    @Autowired
    private UserService userService;
    
    /**
     * 学习页面
     */
    @GetMapping("/study")
    public String studyPage(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/auth/login";
        }
        
        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isEmpty()) {
            return "redirect:/auth/login";
        }
        
        User user = userOpt.get();
        WordService.UserStats stats = wordService.getUserStats(user);
        
        model.addAttribute("username", username);
        model.addAttribute("stats", stats);
        
        return "study";
    }
    
    /**
     * 学习历史页面
     */
    @GetMapping("/history")
    public String historyPage(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/auth/login";
        }
        
        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isEmpty()) {
            return "redirect:/auth/login";
        }
        
        User user = userOpt.get();
        
        // 获取用户的学习历史
        List<UserWordProgress> allProgress = wordService.getUserAllProgress(user);
        
        // 转换为前端可用的DTO对象
        List<Map<String, Object>> historyDto = new ArrayList<>();
        for (UserWordProgress progress : allProgress) {
            Map<String, Object> historyItem = new HashMap<>();
            historyItem.put("id", progress.getId());
            historyItem.put("english", progress.getWord().getEnglish());
            historyItem.put("chinese", progress.getWord().getChinese());
            historyItem.put("pronunciation", progress.getWord().getPronunciation());
            historyItem.put("example", progress.getWord().getExample());
            historyItem.put("correctCount", progress.getCorrectCount());
            historyItem.put("wrongCount", progress.getWrongCount());
            historyItem.put("masteryLevel", progress.getMasteryLevel());
            historyItem.put("lastReviewed", progress.getLastReviewed());
            historyItem.put("createdAt", progress.getCreatedAt());
            
            // 计算正确率
            int total = progress.getCorrectCount() + progress.getWrongCount();
            double accuracy = total > 0 ? (double) progress.getCorrectCount() / total * 100 : 0;
            historyItem.put("accuracy", Math.round(accuracy));
            
            // 掌握程度文字
            String masteryText = switch (progress.getMasteryLevel()) {
                case 0 -> "未学习";
                case 1 -> "学习中";
                case 2 -> "已掌握";
                default -> "未知";
            };
            historyItem.put("masteryText", masteryText);
            
            historyDto.add(historyItem);
        }
        
        // 按学习时间倒序排列
        historyDto.sort((a, b) -> {
            LocalDateTime timeA = (LocalDateTime) a.get("createdAt");
            LocalDateTime timeB = (LocalDateTime) b.get("createdAt");
            return timeB.compareTo(timeA);
        });
        
        // 计算统计数据
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalWords", historyDto.size());
        stats.put("masteredCount", historyDto.stream().mapToInt(item -> (Integer) item.get("masteryLevel") == 2 ? 1 : 0).sum());
        stats.put("learningCount", historyDto.stream().mapToInt(item -> (Integer) item.get("masteryLevel") == 1 ? 1 : 0).sum());
        stats.put("unstudiedCount", historyDto.stream().mapToInt(item -> (Integer) item.get("masteryLevel") == 0 ? 1 : 0).sum());
        
        // 计算平均正确率
        double avgAccuracy = historyDto.isEmpty() ? 0 : 
            historyDto.stream().mapToDouble(item -> ((Number) item.get("accuracy")).doubleValue()).average().orElse(0);
        stats.put("avgAccuracy", Math.round(avgAccuracy));
        
        model.addAttribute("username", username);
        model.addAttribute("historyList", historyDto);
        model.addAttribute("stats", stats);
        
        return "history";
    }
    
    /**
     * 获取下一个学习题目
     */
    @GetMapping("/api/next-question")
    @ResponseBody
    public ResponseEntity<QuestionDto> getNextQuestion(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        User user = userOpt.get();
        Word word = wordService.getNextWordToLearn(user);
        
        if (word == null) {
            return ResponseEntity.noContent().build();
        }
        
        QuestionDto question = generateQuestion(word);
        return ResponseEntity.ok(question);
    }
    

    
    /**
     * 提交答案
     */
    @PostMapping("/api/submit-answer")
    @ResponseBody
    public ResponseEntity<AnswerDto> submitAnswer(@RequestBody AnswerDto answerDto, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        User user = userOpt.get();
        Optional<Word> wordOpt = wordService.getWordById(answerDto.getWordId());
        
        if (wordOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        Word word = wordOpt.get();
        boolean isCorrect = answerDto.getUserAnswer().trim().equalsIgnoreCase(answerDto.getCorrectAnswer().trim());
        
        // 记录答题结果
        wordService.recordAnswer(user, word, isCorrect);
        
        // 返回结果
        AnswerDto result = new AnswerDto();
        result.setWordId(word.getId());
        result.setUserAnswer(answerDto.getUserAnswer());
        result.setCorrectAnswer(answerDto.getCorrectAnswer());
        result.setCorrect(isCorrect);
        
        return ResponseEntity.ok(result);
    }
    

    
    /**
     * 生成题目
     */
    private QuestionDto generateQuestion(Word word) {
        Random random = new Random();
        List<Word> allWords = wordService.getAllWords();
        
        // 随机选择题目类型
        boolean isEnglishToChinese = random.nextBoolean();
        
        QuestionDto question = new QuestionDto();
        question.setWordId(word.getId());
        question.setEnglish(word.getEnglish());
        question.setChinese(word.getChinese());
        question.setPronunciation(word.getPronunciation());
        question.setExample(word.getExample());
        question.setDifficultyLevel(word.getDifficultyLevel());
        
        if (isEnglishToChinese) {
            // 英译中
            question.setQuestion(word.getEnglish());
            question.setCorrectAnswer(word.getChinese());
            question.setType("english_to_chinese");
            
            // 生成选项
            Set<String> options = new HashSet<>();
            options.add(word.getChinese());
            
            // 添加3个错误选项
            while (options.size() < 4 && allWords.size() >= 4) {
                Word randomWord = allWords.get(random.nextInt(allWords.size()));
                if (!randomWord.getId().equals(word.getId())) {
                    options.add(randomWord.getChinese());
                }
            }
            
            List<String> optionsList = new ArrayList<>(options);
            Collections.shuffle(optionsList);
            question.setOptions(optionsList);
            
        } else {
            // 中译英
            question.setQuestion(word.getChinese());
            question.setCorrectAnswer(word.getEnglish());
            question.setType("chinese_to_english");
            
            // 生成选项
            Set<String> options = new HashSet<>();
            options.add(word.getEnglish());
            
            // 添加3个错误选项
            while (options.size() < 4 && allWords.size() >= 4) {
                Word randomWord = allWords.get(random.nextInt(allWords.size()));
                if (!randomWord.getId().equals(word.getId())) {
                    options.add(randomWord.getEnglish());
                }
            }
            
            List<String> optionsList = new ArrayList<>(options);
            Collections.shuffle(optionsList);
            question.setOptions(optionsList);
        }
        
        return question;
    }
}