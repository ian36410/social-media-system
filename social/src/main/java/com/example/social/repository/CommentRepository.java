package com.example.social.repository;

import com.example.social.entity.Comment;
import com.example.social.entity.Post;
import com.example.social.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 查詢某篇發文下的所有留言
    List<Comment> findByPost(Post post);

    // 查詢某個使用者的所有留言
    List<Comment> findByUser(User user);
}
