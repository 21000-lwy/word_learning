package com.example.wordlearning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.wordlearning.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserSettingsController {

    @Autowired
    private UserService userService;

    /**
     * 显示个人设置页面
     */
    @GetMapping("/settings")
    public String showSettingsPage(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");

        if (username == null) {
            return "redirect:/auth/login";
        }

        // 获取用户当前邮箱
        String userEmail = userService.getEmailByUsername(username);

        model.addAttribute("username", username);
        model.addAttribute("userEmail", userEmail);
        return "settings";
    }

    /**
     * 处理个人资料更新
     */
    @PostMapping("/settings/update-profile")
    public String updateProfile(HttpSession session,
                                @RequestParam String newUsername,
                                @RequestParam String currentPassword,
                                RedirectAttributes redirectAttributes) {

        String username = (String) session.getAttribute("username");

        if (username == null) {
            return "redirect:/auth/login";
        }

        String result = userService.updateProfile(username, newUsername, currentPassword);

        if ("个人资料更新成功".equals(result)) {
            // 更新session中的用户名
            session.setAttribute("username", newUsername);
            redirectAttributes.addFlashAttribute("success", result);
        } else {
            redirectAttributes.addFlashAttribute("error", result);
        }

        return "redirect:/settings";
    }

    /**
     * 处理邮箱更新
     */
    @PostMapping("/settings/update-email")
    public String updateEmail(HttpSession session,
                              @RequestParam String newEmail,
                              @RequestParam String currentPassword,
                              RedirectAttributes redirectAttributes) {

        String username = (String) session.getAttribute("username");

        if (username == null) {
            return "redirect:/auth/login";
        }

        String result = userService.updateEmail(username, newEmail, currentPassword);

        if ("邮箱更新成功".equals(result)) {
            redirectAttributes.addFlashAttribute("success", result);
        } else {
            redirectAttributes.addFlashAttribute("error", result);
        }

        return "redirect:/settings";
    }

    /**
     * 处理密码更新
     */
    @PostMapping("/settings/update-password")
    public String updatePassword(HttpSession session,
                                 @RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 RedirectAttributes redirectAttributes) {

        String username = (String) session.getAttribute("username");

        if (username == null) {
            return "redirect:/auth/login";
        }

        String result = userService.updatePassword(username, currentPassword, newPassword, confirmPassword);

        if ("密码更新成功".equals(result)) {
            redirectAttributes.addFlashAttribute("success", result);
        } else {
            redirectAttributes.addFlashAttribute("error", result);
        }

        return "redirect:/settings";
    }
}