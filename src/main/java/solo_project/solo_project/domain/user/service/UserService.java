package solo_project.solo_project.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solo_project.solo_project.domain.user.dto.request.SignUpRequest;
import solo_project.solo_project.domain.user.entity.User;
import solo_project.solo_project.domain.user.repository.UserRepository;
import solo_project.solo_project.domain.user.security.JwtTokenProvider;
import solo_project.solo_project.domain.user.util.UserConverter;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private JwtTokenProvider jwtTokenProvider;

  public Long signUp(SignUpRequest request) {

    boolean duplicateEmail = isDuplicateEmail(request.getEmail());
    boolean duplicateNickname = isDuplicateNickname(request.getNickname());

    if (duplicateEmail || duplicateNickname) {
      throw new RuntimeException();
    }

    User user = UserConverter.toUser(request);
    Long id = userRepository.save(user).getId();
    return id;
  }

  public boolean isDuplicateNickname(String nickname) {
    return userRepository.existsByNicknameNickname(nickname);
  }

  public boolean isDuplicateEmail(String email) {
    return userRepository.existsByEmailEmailAddress(email);
  }
}
