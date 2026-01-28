package br.com.goodfood.controller;

import br.com.goodfood.domain.follow.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{userId}/follow")
    public ResponseEntity<String> follow(@PathVariable Long userId) {
        followService.follow(userId);
        return ResponseEntity.ok().body("Você está seguindo este usuário.");
    }

    @DeleteMapping("/{userId}/unfollow")
    public ResponseEntity<String> unfollow(@PathVariable Long userId) {
        followService.unfollow(userId);
        return ResponseEntity.ok().body("Você não está mais seguindo este usuário.");
    }
}