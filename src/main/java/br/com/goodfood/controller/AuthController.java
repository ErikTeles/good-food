package br.com.goodfood.controller;

import br.com.goodfood.domain.auth.AuthService;
import br.com.goodfood.domain.auth.LoginRequestDTO;
import br.com.goodfood.domain.auth.LoginResponseDTO;
import br.com.goodfood.domain.user.UserRegisterDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginData) {
        LoginResponseDTO dto = authService.login(loginData);

        return ResponseEntity.ok(dto);
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponseDTO> userRegister(@RequestPart @Valid UserRegisterDTO data, @RequestParam(required = false) MultipartFile profilePic) {
        LoginResponseDTO dto = authService.userRegister(data, profilePic);

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

}
