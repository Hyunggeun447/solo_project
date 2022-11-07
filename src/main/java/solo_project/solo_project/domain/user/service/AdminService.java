package solo_project.solo_project.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solo_project.solo_project.domain.user.entity.Authority;
import solo_project.solo_project.domain.user.entity.User;
import solo_project.solo_project.domain.user.repository.AuthorityRepository;
import solo_project.solo_project.domain.user.repository.UserRepository;
import solo_project.solo_project.domain.user.value.AuthorityLevel;

@Transactional
@Service
@RequiredArgsConstructor
public class AdminService {

  private final UserRepository userRepository;
  private final AuthorityRepository authorityRepository;


  public void banUser(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(RuntimeException::new);
    user.changeIsNonLocked(false);
  }

  public void giveAuth(Long userId, AuthorityLevel auth) {
    User user = userRepository.findById(userId)
        .orElseThrow(RuntimeException::new);
    auth.giveAuth(user);
  }

  public void removeAuth(Long userIdm, AuthorityLevel auth) {

  }

}
