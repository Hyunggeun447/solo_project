package solo_project.solo_project.domain.user.service;

import static solo_project.solo_project.domain.user.util.UserConverter.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solo_project.solo_project.domain.user.mapper.dto.request.SignUpRequest;
import solo_project.solo_project.domain.user.mapper.dto.request.UpdatePasswordRequest;
import solo_project.solo_project.domain.user.mapper.dto.request.UpdateRequest;
import solo_project.solo_project.domain.user.entity.User;
import solo_project.solo_project.domain.user.repository.UserRepository;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public Long signUp(SignUpRequest request) {

    boolean duplicateEmail = isDuplicatedEmail(request.getEmail());
    boolean duplicateNickname = isDuplicatedNickname(request.getNickname());

    if (duplicateEmail || duplicateNickname) {
      throw new RuntimeException();
    }

    User user = toUser(request);
    return userRepository.save(user).getId();
  }

  public void update(Long userId, UpdateRequest request) {
    User user = userRepository.findById(userId)
        .orElseThrow(RuntimeException::new);

    user.changeNickname(request.getNickname());
    user.changeAddress(request.getCity(), request.getDetailAddress());
    user.changePhoneNumber(request.getPhoneNumber());
  }

  public void updatePassword(Long userId, UpdatePasswordRequest request) {
    User user = userRepository.findById(userId)
        .orElseThrow(RuntimeException::new);

    user.changePassword(request.getPrePassword(), request.getNewPassword());

    // TODO: 2022/10/27 로그아웃 구현
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
