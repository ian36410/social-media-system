package com.example.social.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")  // 對應資料庫的 users table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")  // 對應 DB 欄位名稱
    private Long userId;

    @Column(name = "user_name", nullable = false, length = 50)
    private String userName;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "cover_image", length = 255)
    private String coverImage;

    @Column(name = "biography")
    private String biography;

    // 預設建構子（JPA 需要）
    public User() {}

    // 全部參數的建構子
    public User(Long userId, String userName, String email, String password, String coverImage, String biography) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.coverImage = coverImage;
        this.biography = biography;
    }

    // Getter 和 Setter
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }
}
