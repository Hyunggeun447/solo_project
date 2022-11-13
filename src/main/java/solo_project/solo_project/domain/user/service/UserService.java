package solo_project.solo_project.domain.user.service;

import static solo_project.solo_project.domain.user.util.UserConverter.*;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import solo_project.solo_project.common.s3.S3UploadService;
import solo_project.solo_project.domain.user.entity.Profile;
import solo_project.solo_project.domain.user.mapper.dto.request.SignUpRequest;
import solo_project.solo_project.domain.user.mapper.dto.request.UpdatePasswordRequest;
import solo_project.solo_project.domain.user.mapper.dto.request.UpdateUserRequest;
import solo_project.solo_project.domain.user.entity.User;
import solo_project.solo_project.domain.user.mapper.dto.response.UserSelfInfoResponse;
import solo_project.solo_project.domain.user.repository.UserRepository;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final S3UploadService s3UploadService;

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

  public void update(Long userId, UpdateUserRequest updateUserRequest) {
    User user = userRepository.findById(userId)
        .orElseThrow(RuntimeException::new);

    user.changeNickname(updateUserRequest.getNickname());
    user.changeAddress(updateUserRequest.getCity(), updateUserRequest.getDetailAddress());
    user.changePhoneNumber(updateUserRequest.getPhoneNumber());
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

  public void addProfile(Long userId, MultipartFile multipartFile) {
    String profileUrl = s3UploadService.uploadImg(multipartFile);
    User user = userRepository.findById(userId)
        .orElseThrow(RuntimeException::new);
    Profile profile = new Profile(profileUrl, user);
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
