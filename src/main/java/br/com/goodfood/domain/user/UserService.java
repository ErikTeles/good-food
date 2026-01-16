package br.com.goodfood.domain.user;

import br.com.goodfood.domain.auth.LoginResponseDTO;
import br.com.goodfood.infra.exception.BusinessRuleException;
import br.com.goodfood.infra.exception.ConstraintException;
import br.com.goodfood.infra.security.TokenService;
import br.com.goodfood.service.ImageStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ImageStorageService imageStorageService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Transactional
    public LoginResponseDTO userRegister(UserRegisterDTO dto, MultipartFile profilePic) {
        Boolean user = userRepository.existsByEmail(dto.email());

        if (user) {
            throw new BusinessRuleException("Erro! Não foi possível cadastrar usuário. Já existe um usuário com esse e-mail cadastrado.");
        }

        try {
            User newUser;

            if (profilePic != null && !profilePic.isEmpty()) {
                String imageName = imageStorageService.upload(profilePic);
                newUser = new User(dto.name(), dto.email(), passwordEncoder.encode(dto.password()), imageName);

            } else {
                newUser = new User(dto.name(), dto.email(), passwordEncoder.encode(dto.password()), null);
            }

            userRepository.save(newUser);
            String token = tokenService.generateToken(newUser);

            return new LoginResponseDTO(newUser.getName(), token);
        } catch (IOException e) {
            throw new RuntimeException("Erro! Não foi possível salvar a foto de perfil do usuário.");
        }
    }
}
