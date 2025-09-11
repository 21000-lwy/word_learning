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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private WordService wordService;
    
    /**
     * 首页重定向到登录页面
     */
    @GetMapping("/")
    public String home() {
        return "redirect:/auth/login";
    }
    
    /**
     * 用户仪表板页面
     */
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
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
        
        // 计算学习天数
        List<UserWordProgress> allProgress = wordService.getUserAllProgress(user);
        Set<LocalDate> learningDates = allProgress.stream()
            .filter(p -> p.getCreatedAt() != null)
            .map(p -> p.getCreatedAt().toLocalDate())
            .collect(Collectors.toSet());
        long learningDays = learningDates.size();
        
        model.addAttribute("username", username);
        model.addAttribute("totalLearned", stats.getTotalLearned());
        model.addAttribute("mastered", stats.getMastered());
        model.addAttribute("learning", stats.getLearning());
        model.addAttribute("learningDays", learningDays);
        
        return "dashboard";
    }
}