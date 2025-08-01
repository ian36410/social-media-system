package com.example.social.controller;

import com.example.social.entity.Comment;
import com.example.social.entity.Post;
import com.example.social.entity.User;
import com.example.social.repository.CommentRepository;
import com.example.social.repository.PostRepository;
import com.example.social.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    // 新增留言
    @PostMapping
    public Comment createComment(@RequestBody Comment comment) {
        // TODO: 可加驗證 post/user 是否存在
        return commentRepository.save(comment);
    }

    // 查詢某篇貼文下的所有留言
    @GetMapping("/post/{postId}")
    public List<Comment> getCommentsByPost(@PathVariable Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) return null;
        return commentRepository.findByPost(post);
    }
}
