package solo_project.solo_project.domain.user.service;

import static solo_project.solo_project.domain.user.util.UserConverter.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solo_project.solo_project.domain.user.dto.request.LoginRequest;
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

  public Long signUp(SignUpRequest request) {

    boolean duplicateEmail = isDuplicatedEmail(request.getEmail());
    boolean duplicateNickname = isDuplicatedNickname(request.getNickname());

    if (duplicateEmail || duplicateNickname) {
      throw new RuntimeException();
    }

    User user = toUser(request);
    return userRepository.save(user).getId();
  }

  public LoginResponse login(LoginRequest request) {
    String email = request.getEmail();
    String password = request.getPassword();
    User user = userRepository.findByEmailEmailAddress(email)
        .orElseThrow(RuntimeException::new);

    user.getPassword().isMatch(password);

    String token = jwtTokenProvider.generateAccessToken(user.getId(),
        user.getEmail().getEmailAddress());

    return toLoginResponse(user, token);
  }

  public boolean isDuplicatedNickname(String nickname) {
    return userRepository.existsByNicknameNickname(nickname);
  }

  public boolean isDuplicatedEmail(String email) {
    return userRepository.existsByEmailEmailAddress(email);
  }
}
