package com.example.demo.service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.entity.User;

public interface UserService {
    User registerAdmin(User user);
    LoginResponse login(LoginRequest request);
}
