package br.com.goodfood.controller;

import br.com.goodfood.domain.auth.LoginResponseDTO;
import br.com.goodfood.domain.user.UserRegisterDTO;
import br.com.goodfood.domain.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<LoginResponseDTO> userRegister(@RequestPart @Valid UserRegisterDTO data, @RequestParam(required = false) MultipartFile profilePic) {
        LoginResponseDTO dto = userService.userRegister(data, profilePic);

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping
    public ResponseEntity<String> getUser() {
        return ResponseEntity.ok("Sucesso!");
    }
}
