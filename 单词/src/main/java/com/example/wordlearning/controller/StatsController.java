package com.example.wordlearning.controller;

import com.example.wordlearning.entity.User;
import com.example.wordlearning.entity.UserWordProgress;
import com.example.wordlearning.service.UserService;
import com.example.wordlearning.service.WordService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/stats")
public class StatsController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private WordService wordService;
    
    /**
     * 学习统计页面
     */
    @GetMapping("/learning")
    public String learningStats(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/auth/login";
        }
        
        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isEmpty()) {
            return "redirect:/auth/login";
        }
        
        User user = userOpt.get();
        
        // 获取基本统计
        WordService.UserStats stats = wordService.getUserStats(user);
        
        // 获取学习进度数据
        List<UserWordProgress> allProgress = wordService.getUserAllProgress(user);
        
        // 计算学习天数
        long learningDays = calculateLearningDays(allProgress);
        
        // 生成学习进度图表数据
        Map<String, Object> chartData = generateChartData(allProgress);
        
        // 计算掌握度分布
        Map<String, Integer> masteryDistribution = calculateMasteryDistribution(allProgress);
        
        // 计算学习效率
        double accuracy = calculateAccuracy(allProgress);
        
        model.addAttribute("username", username);
        model.addAttribute("stats", stats);
        model.addAttribute("learningDays", learningDays);
        model.addAttribute("chartData", chartData);
        model.addAttribute("masteryDistribution", masteryDistribution);
        model.addAttribute("accuracy", Math.round(accuracy * 100));
        
        return "stats";
    }
    
    /**
     * 计算学习天数
     */
    private long calculateLearningDays(List<UserWordProgress> progressList) {
        if (progressList.isEmpty()) {
            return 0;
        }
        
        Set<LocalDate> learningDates = progressList.stream()
            .filter(p -> p.getCreatedAt() != null)
            .map(p -> p.getCreatedAt().toLocalDate())
            .collect(Collectors.toSet());
            
        return learningDates.size();
    }
    
    /**
     * 生成图表数据
     */
    private Map<String, Object> generateChartData(List<UserWordProgress> progressList) {
        Map<String, Object> chartData = new HashMap<>();
        
        // 按日期统计学习数量
        Map<LocalDate, Long> dailyStats = progressList.stream()
            .filter(p -> p.getCreatedAt() != null)
            .collect(Collectors.groupingBy(
                p -> p.getCreatedAt().toLocalDate(),
                Collectors.counting()
            ));
        
        // 获取最近7天的数据
        List<String> dates = new ArrayList<>();
        List<Long> counts = new ArrayList<>();
        
        LocalDate today = LocalDate.now();
        for (int i = 6; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            dates.add(date.format(DateTimeFormatter.ofPattern("MM-dd")));
            counts.add(dailyStats.getOrDefault(date, 0L));
        }
        
        chartData.put("dates", dates);
        chartData.put("counts", counts);
        
        return chartData;
    }
    
    /**
     * 计算掌握度分布
     */
    private Map<String, Integer> calculateMasteryDistribution(List<UserWordProgress> progressList) {
        Map<String, Integer> distribution = new HashMap<>();
        distribution.put("未学习", 0);
        distribution.put("学习中", 0);
        distribution.put("已掌握", 0);
        
        for (UserWordProgress progress : progressList) {
            String level = switch (progress.getMasteryLevel()) {
                case 0 -> "未学习";
                case 1 -> "学习中";
                case 2 -> "已掌握";
                default -> "未学习";
            };
            distribution.put(level, distribution.get(level) + 1);
        }
        
        return distribution;
    }
    
    /**
     * 计算学习准确率
     */
    private double calculateAccuracy(List<UserWordProgress> progressList) {
        if (progressList.isEmpty()) {
            return 0.0;
        }
        
        int totalCorrect = progressList.stream()
            .mapToInt(UserWordProgress::getCorrectCount)
            .sum();
            
        int totalWrong = progressList.stream()
            .mapToInt(UserWordProgress::getWrongCount)
            .sum();
            
        int total = totalCorrect + totalWrong;
        
        return total > 0 ? (double) totalCorrect / total : 0.0;
    }
}