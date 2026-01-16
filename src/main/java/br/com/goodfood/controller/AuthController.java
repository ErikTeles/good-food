package br.com.goodfood.controller;

import br.com.goodfood.domain.auth.AuthService;
import br.com.goodfood.domain.auth.LoginRequestDTO;
import br.com.goodfood.domain.auth.LoginResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
