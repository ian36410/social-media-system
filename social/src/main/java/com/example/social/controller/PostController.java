package com.example.social.controller;

import com.example.social.entity.Post;
import com.example.social.entity.User;
import com.example.social.repository.PostRepository;
import com.example.social.repository.UserRepository;
import com.example.social.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    // 發文（新增一則貼文）
    @PostMapping("/posts")
    public ResponseEntity<?> createPost(@RequestHeader("Authorization") String authHeader, @RequestBody Post post) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("未授權");
        }
        String token = authHeader.replace("Bearer ", "");
        Long userId = JwtUtil.getUserIdFromToken(token);
        // 這裡 userId 就是登入者的ID
        User user = userRepository.findById(userId).orElse(null);
        post.setUser(user);
        postRepository.save(post);
        return ResponseEntity.ok("發文成功");
    }

    // 查詢所有貼文（依建立時間排序）
    @GetMapping
    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    // 查詢某一貼文
    @GetMapping("/{id}")
    public Post getPostById(@PathVariable Long id) {
        return postRepository.findById(id).orElse(null);
    }

    // 修改貼文
    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody Post postDetails) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        post.setContent(postDetails.getContent());
        post.setImage(postDetails.getImage());

        return postRepository.save(post);
    }

    // 刪除貼文
    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable Long id) {
        postRepository.deleteById(id);
        return "Deleted post with id: " + id;
    }
}
