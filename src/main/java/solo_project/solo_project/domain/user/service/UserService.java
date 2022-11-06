package solo_project.solo_project.domain.user.service;

import static solo_project.solo_project.domain.user.util.UserConverter.*;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solo_project.solo_project.domain.user.mapper.dto.request.SignUpRequest;
import solo_project.solo_project.domain.user.mapper.dto.request.UpdatePasswordRequest;
import solo_project.solo_project.domain.user.mapper.dto.request.UpdateRequest;
import solo_project.solo_project.domain.user.entity.User;
import solo_project.solo_project.domain.user.mapper.dto.response.UserSelfInfoResponse;
import solo_project.solo_project.domain.user.repository.UserRepository;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public Long signUp(SignUpRequest signUpRequest) {

    boolean duplicateEmail = isDuplicatedEmail(signUpRequest.getEmail());
    boolean duplicateNickname = isDuplicatedNickname(signUpRequest.getNickname());

    if (duplicateEmail || duplicateNickname) {
      throw new RuntimeException();
    }

    User user = toUser(signUpRequest);
    user.changePassword(passwordEncoder.encode(signUpRequest.getPassword()));
    return userRepository.save(user).getId();
  }

  public void update(Long userId, UpdateRequest updateRequest) {
    User user = userRepository.findById(userId)
        .orElseThrow(RuntimeException::new);

    user.changeNickname(updateRequest.getNickname());
    user.changeAddress(updateRequest.getCity(), updateRequest.getDetailAddress());
    user.changePhoneNumber(updateRequest.getPhoneNumber());
  }

  public void updatePassword(Long userId, UpdatePasswordRequest updatePasswordRequest) {
    User user = userRepository.findById(userId)
        .orElseThrow(RuntimeException::new);

    if (!passwordEncoder.matches(updatePasswordRequest.getPrePassword(), user.getPassword())) {
      throw new RuntimeException();
    }
    user.changePassword(passwordEncoder.encode(updatePasswordRequest.getNewPassword()));
  }

  public void delete(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(RuntimeException::new);

    userRepository.delete(user);
  }

  public UserSelfInfoResponse findUserSelfInfo(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(RuntimeException::new);
    return toUserSelfInfo(user);
  }

  @Transactional(readOnly = true)
  public boolean isDuplicatedNickname(String nickname) {
    return userRepository.existsByNicknameNickname(nickname);
  }

  @Transactional(readOnly = true)
  public boolean isDuplicatedEmail(String email) {
    return userRepository.existsByEmailEmailAddress(email);
  }
}
