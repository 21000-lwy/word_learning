package com.example.wordlearning.controller;

import com.example.wordlearning.entity.User;
import com.example.wordlearning.entity.Word;
import com.example.wordlearning.service.UserService;
import com.example.wordlearning.service.WordService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private WordService wordService;

    // 管理员首页
    @GetMapping("/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.isAdmin()) {
            return "redirect:/login";
        }

        // 获取统计数据
        List<User> allUsers = userService.getAllUsers();
        List<Word> allWords = wordService.getAllWords();
        
        model.addAttribute("totalUsers", allUsers.size());
        model.addAttribute("totalWords", allWords.size());
        model.addAttribute("adminUser", user);

        return "admin/dashboard";
    }

    // 用户管理页面
    @GetMapping("/users")
    public String userManagement(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.isAdmin()) {
            return "redirect:/login";
        }

        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("adminUser", user);

        return "admin/users";
    }

    // 删除用户
    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.isAdmin()) {
            return "redirect:/login";
        }

        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("success", "用户删除成功");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "删除用户失败：" + e.getMessage());
        }

        return "redirect:/admin/users";
    }

    // 切换用户角色
    @PostMapping("/users/toggle-role/{id}")
    public String toggleUserRole(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.isAdmin()) {
            return "redirect:/login";
        }

        try {
            userService.toggleUserRole(id);
            redirectAttributes.addFlashAttribute("success", "用户角色修改成功");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "修改用户角色失败：" + e.getMessage());
        }

        return "redirect:/admin/users";
    }

    // 单词管理页面
    @GetMapping("/words")
    public String wordManagement(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.isAdmin()) {
            return "redirect:/login";
        }

        List<Word> words = wordService.getAllWords();
        model.addAttribute("words", words);
        model.addAttribute("adminUser", user);

        return "admin/words";
    }

    // 添加单词页面
    @GetMapping("/words/add")
    public String addWordPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.isAdmin()) {
            return "redirect:/login";
        }

        model.addAttribute("word", new Word());
        model.addAttribute("adminUser", user);

        return "admin/add-word";
    }

    // 添加单词
    @PostMapping("/words/add")
    public String addWord(@ModelAttribute Word word, HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.isAdmin()) {
            return "redirect:/login";
        }

        try {
            wordService.saveWord(word);
            redirectAttributes.addFlashAttribute("success", "单词添加成功");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "添加单词失败：" + e.getMessage());
        }

        return "redirect:/admin/words";
    }

    // 编辑单词页面
    @GetMapping("/words/edit/{id}")
    public String editWordPage(@PathVariable Long id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.isAdmin()) {
            return "redirect:/login";
        }

        Word word = wordService.getWordById(id).orElse(null);
        if (word == null) {
            return "redirect:/admin/words";
        }

        model.addAttribute("word", word);
        model.addAttribute("adminUser", user);

        return "admin/edit-word";
    }

    // 更新单词
    @PostMapping("/words/edit/{id}")
    public String updateWord(@PathVariable Long id, @ModelAttribute Word word, HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.isAdmin()) {
            return "redirect:/login";
        }

        try {
            word.setId(id);
            wordService.saveWord(word);
            redirectAttributes.addFlashAttribute("success", "单词更新成功");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "更新单词失败：" + e.getMessage());
        }

        return "redirect:/admin/words";
    }

    // 删除单词
    @PostMapping("/words/delete/{id}")
    public String deleteWord(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.isAdmin()) {
            return "redirect:/login";
        }

        try {
            wordService.deleteWord(id);
            redirectAttributes.addFlashAttribute("success", "单词删除成功");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "删除单词失败：" + e.getMessage());
        }

        return "redirect:/admin/words";
    }
}