package com.example.wordlearning.service;

import com.example.wordlearning.dto.UserLoginDto;
import com.example.wordlearning.dto.UserRegistrationDto;
import com.example.wordlearning.entity.User;
import com.example.wordlearning.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    /**
     * 用户注册
     */
    public String registerUser(UserRegistrationDto registrationDto) {
        // 检查用户名是否已存在
        if (userRepository.existsByUsername(registrationDto.getUsername())) {
            return "用户名已存在";
        }
        
        // 检查邮箱是否已存在
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            return "邮箱已被注册";
        }
        
        // 检查密码确认
        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
            return "两次输入的密码不一致";
        }
        
        // 创建新用户
        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(registrationDto.getPassword()); // 实际项目中应该加密密码
        
        userRepository.save(user);
        return "注册成功";
    }
    
    /**
     * 用户登录
     */
    public String loginUser(UserLoginDto loginDto) {
        Optional<User> userOptional = userRepository.findByUsername(loginDto.getUsername());
        
        if (userOptional.isEmpty()) {
            return "用户名不存在";
        }
        
        User user = userOptional.get();
        if (!user.getPassword().equals(loginDto.getPassword())) {
            return "密码错误";
        }
        
        return "登录成功";
    }
    
    /**
     * 根据用户名查找用户
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    /**
     * 获取所有用户
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    /**
     * 根据ID查找用户
     */
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    
    /**
     * 删除用户
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    /**
     * 切换用户角色
     */
    public void toggleUserRole(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if ("USER".equals(user.getRole())) {
                user.setRole("ADMIN");
            } else {
                user.setRole("USER");
            }
            userRepository.save(user);
        }
    }

    /**
     * 更新用户密码
     */
    public String updatePassword(String username, String currentPassword, String newPassword, String confirmPassword) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            return "用户不存在";
        }

        User user = userOptional.get();

        // 验证当前密码
        if (!user.getPassword().equals(currentPassword)) {
            return "当前密码不正确";
        }

        // 验证新密码
        if (!newPassword.equals(confirmPassword)) {
            return "两次输入的新密码不一致";
        }

        // 验证新密码长度
        if (newPassword.length() < 6) {
            return "密码长度至少6个字符";
        }

        // 更新密码
        user.setPassword(newPassword); // 实际项目中应该加密密码
        userRepository.save(user);

        return "密码更新成功";
    }


    /**
     * 更新用户个人资料（用户名）
     */
    public String updateProfile(String username, String newUsername, String currentPassword) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            return "用户不存在";
        }

        User user = userOptional.get();

        // 验证当前密码
        if (!user.getPassword().equals(currentPassword)) {
            return "当前密码不正确";
        }

        // 检查新用户名是否已存在
        if (!username.equals(newUsername) && userRepository.existsByUsername(newUsername)) {
            return "用户名已被使用";
        }

        // 更新用户名
        user.setUsername(newUsername);
        userRepository.save(user);

        return "个人资料更新成功";
    }

    /**
     * 更新用户邮箱
     */
    public String updateEmail(String username, String newEmail, String currentPassword) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            return "用户不存在";
        }

        User user = userOptional.get();

        // 验证当前密码
        if (!user.getPassword().equals(currentPassword)) {
            return "当前密码不正确";
        }

        // 检查新邮箱是否已存在
        if (!user.getEmail().equals(newEmail) && userRepository.existsByEmail(newEmail)) {
            return "邮箱已被注册";
        }

        // 更新邮箱
        user.setEmail(newEmail);
        userRepository.save(user);

        return "邮箱更新成功";
    }

    /**
     * 根据用户名获取邮箱
     */
    public String getEmailByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.map(User::getEmail).orElse("");
    }
}