package com.example.social.repository;

import com.example.social.entity.Post;
import com.example.social.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 查詢某個使用者的所有發文
    List<Post> findByUser(User user);

    // 依照時間排序
    List<Post> findAllByOrderByCreatedAtDesc();
}
