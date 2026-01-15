package br.com.goodfood.domain.user;

import br.com.goodfood.service.ImageStorageService;
import br.com.goodfood.service.MapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageStorageService imageStorageService;

    @Autowired
    private MapperService mapperService;

    public UserDTO userRegister(UserRegisterDTO dto, MultipartFile profilePic) {
        User user;

        try {
            if (profilePic != null && !profilePic.isEmpty()) {
                String imageName = imageStorageService.upload(profilePic);

                user = new User(dto, imageName);
            } else {
                user = new User(dto, null);
            }

            userRepository.save(user);
        } catch (IOException e) {
            throw new RuntimeException("Erro! Não foi possível salvar a foto de perfil do usuário." + e.getMessage());
        }

        return mapperService.transform(user, UserDTO.class);
    }
}
