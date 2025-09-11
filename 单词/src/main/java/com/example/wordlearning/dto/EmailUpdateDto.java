package com.example.wordlearning.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmailUpdateDto {

    @NotBlank(message = "新邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String newEmail;

    @NotBlank(message = "当前密码不能为空")
    private String currentPassword;

    // Getters and Setters
    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }
}