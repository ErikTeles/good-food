package br.com.goodfood.domain.user;

import br.com.goodfood.domain.auth.AuthService;
import br.com.goodfood.domain.follow.FollowRepository;
import br.com.goodfood.infra.exception.ObjectNotFoundException;
import br.com.goodfood.service.MapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final MapperService mapperService;
    private final AuthService authService;

    @Value("${app.images.base-url}")
    private String imageBaseURL;

    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Usuário com ID " + id + " não encontrado."));

        UserDTO dto = mapperService.transform(user, UserDTO.class);

        dto.setProfilePic(user.getProfilePic() != null ? imageBaseURL + user.getProfilePic() : null);
        dto.setFollowersNumber(followRepository.countByFollower(user));
        dto.setFollowingNumber(followRepository.countByFollowing(user));

        return dto;
    }

    @Transactional(readOnly = true)
    public UserDTO getAuthenticatedUser() {
        User user = authService.getAuthenticatedUser();

        UserDTO dto = mapperService.transform(user, UserDTO.class);
        dto.setProfilePic(user.getProfilePic() != null ? imageBaseURL + user.getProfilePic() : null);
        dto.setFollowersNumber(followRepository.countByFollower(user));
        dto.setFollowingNumber(followRepository.countByFollowing(user));

        return dto;
    }
}
