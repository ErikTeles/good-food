package br.com.goodfood.domain.follow;

import br.com.goodfood.domain.auth.AuthService;
import br.com.goodfood.domain.user.User;
import br.com.goodfood.domain.user.UserRepository;
import br.com.goodfood.infra.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final AuthService authService;

    @Transactional
    public void follow(Long targetUserId) {

        Long authenticatedUserId = authService.getAuthenticatedUser().getIdUser();

        if (authenticatedUserId.equals(targetUserId)) {
            throw new IllegalArgumentException("Usuário não pode seguir a si mesmo.");
        }

        User follower = userRepository.getReferenceById(authenticatedUserId);
        User following = userRepository.findById(targetUserId)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Usuário a ser seguido não encontrado.")
                );

        if (followRepository.existsByFollowerAndFollowing(follower, following)) {
            throw new IllegalStateException("Usuário já está seguindo este perfil.");
        }

        followRepository.save(new Follow(follower, following));
    }

    @Transactional
    public void unfollow(Long targetUserId) {

        Long authenticatedUserId = authService.getAuthenticatedUser().getIdUser();

        if (authenticatedUserId.equals(targetUserId)) {
            throw new IllegalArgumentException("Usuário não pode deixar de seguir a si mesmo.");
        }

        User follower = userRepository.getReferenceById(authenticatedUserId);
        User following = userRepository.findById(targetUserId)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Usuário a ser deixado de seguir não encontrado.")
                );

        Follow follow = followRepository
                .findByFollowerAndFollowing(follower, following)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Relacionamento de follow não existe.")
                );

        followRepository.delete(follow);
    }
}
