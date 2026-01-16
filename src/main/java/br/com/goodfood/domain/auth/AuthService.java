package br.com.goodfood.domain.auth;

import br.com.goodfood.domain.user.User;
import br.com.goodfood.domain.user.UserRepository;
import br.com.goodfood.infra.exception.AuthenticationException;
import br.com.goodfood.infra.exception.ObjectNotFoundException;
import br.com.goodfood.infra.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public LoginResponseDTO login(LoginRequestDTO loginData) {
        User user = userRepository.findByEmail(loginData.email()).orElseThrow(() -> new ObjectNotFoundException("Erro! Não foi possível realizar o login! E-mail ou senha incorretos."));

        if (!passwordEncoder.matches(loginData.password(), user.getPassword())) {
            throw new AuthenticationException("Erro! Não foi possível realizar o login! E-mail ou senha incorretos.");
        }

        String token = tokenService.generateToken(user);
        return new LoginResponseDTO(user.getName(), token);
    }
}