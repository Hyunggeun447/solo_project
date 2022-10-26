package solo_project.solo_project.domain.user.service;

import static solo_project.solo_project.domain.user.util.UserConverter.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import solo_project.solo_project.domain.user.dto.request.SignUpRequest;
import solo_project.solo_project.domain.user.dto.response.LoginResponse;
import solo_project.solo_project.domain.user.entity.User;
import solo_project.solo_project.domain.user.repository.UserRepository;
import solo_project.solo_project.domain.user.security.JwtTokenProvider;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final JwtTokenProvider jwtTokenProvider;

  @PostMapping
  public Long signUp(SignUpRequest request) {

    boolean duplicateEmail = isDuplicateEmail(request.getEmail());
    boolean duplicateNickname = isDuplicateNickname(request.getNickname());

    if (duplicateEmail || duplicateNickname) {
      throw new RuntimeException();
    }

    User user = toUser(request);
    Long id = userRepository.save(user).getId();
    return id;
  }

  public LoginResponse login(String emailAddress, String password) {
    User user = userRepository.findByEmailEmailAddress(emailAddress)
        .orElseThrow(RuntimeException::new);

    user.getPassword().isMatch(password);

    String token = jwtTokenProvider.generateAccessToken(user.getId(),
        user.getEmail().getEmailAddress());

    return toLoginResponse(user, token);
  }

  public boolean isDuplicateNickname(String nickname) {
    return userRepository.existsByNicknameNickname(nickname);
  }

  public boolean isDuplicateEmail(String email) {
    return userRepository.existsByEmailEmailAddress(email);
  }
}
