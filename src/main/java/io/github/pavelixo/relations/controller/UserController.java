package io.github.pavelixo.relations.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.pavelixo.relations.model.UserDocument;
import io.github.pavelixo.relations.service.UserService;
import lombok.Data;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    
    // DTO
    @Data
    protected static class CreateUserRequest {
        private String username;
    }
    
    @Data
    protected static class AddFriendRequest {
        private String friendId;
    }
    
    @GetMapping
    public ResponseEntity<List<UserDocument>> getAllUsers() {
        List<UserDocument> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    @PostMapping
    public ResponseEntity<UserDocument> createUser(@RequestBody CreateUserRequest request) {
        UserDocument user = userService
                .createUser(request.getUsername());
        return ResponseEntity.ok(user);
    }
    
    @PostMapping("/{userId}/friends")
    public ResponseEntity<Void> addFriend(@PathVariable String userId, @RequestBody AddFriendRequest request) {
        userService.addFriend(userId, request.getFriendId());
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/{userId}/friends")
    public ResponseEntity<List<UserDocument>> getFriends(@PathVariable String userId) {
        List<UserDocument> friends = userService.getFriends(userId);
        return ResponseEntity.ok(friends);
    }
    
    @GetMapping("/{userId}/recommendations")
    public ResponseEntity<List<UserDocument>> getFriendRecommendations(@PathVariable String userId) {
        List<UserDocument> recommendations = userService.getRecommend(userId);
        return ResponseEntity.ok(recommendations);
    }
}