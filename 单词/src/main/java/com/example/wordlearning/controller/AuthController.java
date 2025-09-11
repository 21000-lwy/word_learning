package com.example.wordlearning.controller;

import com.example.wordlearning.dto.UserLoginDto;
import com.example.wordlearning.dto.UserRegistrationDto;
import com.example.wordlearning.entity.User;
import com.example.wordlearning.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    /**
     * 显示登录页面
     */
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("loginDto", new UserLoginDto());
        return "login";
    }
    
    /**
     * 处理登录请求
     */
    @PostMapping("/login")
    public String processLogin(@Valid @ModelAttribute("loginDto") UserLoginDto loginDto,
                             BindingResult result,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            return "login";
        }
        
        String loginResult = userService.loginUser(loginDto);
        
        if ("登录成功".equals(loginResult)) {
            // 获取用户对象并存储到session中
            User user = userService.findByUsername(loginDto.getUsername()).orElse(null);
            if (user != null) {
                session.setAttribute("user", user);
                session.setAttribute("username", loginDto.getUsername());
                
                // 根据用户角色跳转到不同页面
                if (user.isAdmin()) {
                    return "redirect:/admin/dashboard";
                } else {
                    return "redirect:/dashboard";
                }
            }
        }
        
        redirectAttributes.addFlashAttribute("error", loginResult);
        return "redirect:/auth/login";
    }
    
    /**
     * 显示注册页面
     */
    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("registrationDto", new UserRegistrationDto());
        return "register";
    }
    
    /**
     * 处理注册请求
     */
    @PostMapping("/register")
    public String processRegistration(@Valid @ModelAttribute("registrationDto") UserRegistrationDto registrationDto,
                                    BindingResult result,
                                    RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            return "register";
        }
        
        String registrationResult = userService.registerUser(registrationDto);
        
        if ("注册成功".equals(registrationResult)) {
            redirectAttributes.addFlashAttribute("success", "注册成功，请登录");
            return "redirect:/auth/login";
        } else {
            redirectAttributes.addFlashAttribute("error", registrationResult);
            return "redirect:/auth/register";
        }
    }
    
    /**
     * 退出登录
     */
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("success", "已成功退出登录");
        return "redirect:/auth/login";
    }
}