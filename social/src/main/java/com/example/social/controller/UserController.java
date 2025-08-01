package com.example.social.controller;

import com.example.social.entity.User;
import com.example.social.repository.UserRepository;
import com.example.social.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // 註冊（新增使用者）
    @PostMapping("/register")
    public Map<String, String> register(@RequestBody User user) {
        
        Map<String, String> response = new HashMap<>();
    
        // 檢查 userName 是否為 10 碼數字（手機格式）
        if (user.getUserName() == null || !user.getUserName().matches("^\\d{10}$")) {
            response.put("message", "請輸入10碼數字的手機號碼");
            return response;
        }
    
        // 檢查 userName 是否已註冊
        if (userRepository.findByUserName(user.getUserName()) != null) {
            response.put("message", "手機號碼已被註冊");
            return response;
        }

        // 密碼加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    
        userRepository.save(user);
        response.put("message", "註冊成功");
        return response;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> loginData) {
        Map<String, String> response = new HashMap<>();
        String userName = loginData.get("userName");
        String password = loginData.get("password");

        if (userName == null || !userName.matches("^\\d{10}$")) {
            response.put("message", "請輸入10碼數字的手機號碼");
            return response;
        }

        User user = userRepository.findByUserName(userName);
        if (user == null) {
            response.put("message", "找不到此手機號碼");
            return response;
        }

        // 用 passwordEncoder.matches
        if (!passwordEncoder.matches(password, user.getPassword())) {
            response.put("message", "密碼錯誤");
            return response;
        }

        // 登入成功，產生token
        String token = JwtUtil.generateToken(user.getUserId());
        response.put("message", "登入成功");
        response.put("token", token);
        return response;
    }

    // 查詢單一使用者
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        System.out.println("呼叫 getUserById, id=" + id);
        return userRepository.findById(id).orElse(null);
    }

    // 查詢所有使用者
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}