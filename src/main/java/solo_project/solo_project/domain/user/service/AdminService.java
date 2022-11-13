package solo_project.solo_project.domain.user.service;

import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solo_project.solo_project.domain.user.entity.Authority;
import solo_project.solo_project.domain.user.entity.User;
import solo_project.solo_project.domain.user.pojo.CustomUserDetails;
import solo_project.solo_project.domain.user.repository.AuthorityRepository;
import solo_project.solo_project.domain.user.repository.UserRepository;
import solo_project.solo_project.domain.user.value.AuthorityLevel;

@Transactional
@Service
@RequiredArgsConstructor
public class AdminService {

  private final UserRepository userRepository;
  private final AuthorityRepository authorityRepository;

  public void banUser(Long userId, CustomUserDetails customUserDetails) {
    validateAdminAuth(customUserDetails);
    User user = userRepository.findById(userId)
        .orElseThrow(RuntimeException::new);
    user.changeIsNonLocked(false);
  }

  public void giveAuth(Long userId, AuthorityLevel auth, CustomUserDetails customUserDetails) {
    validateAdminAuth(customUserDetails);
    User user = userRepository.findById(userId)
        .orElseThrow(RuntimeException::new);
    auth.giveAuth(user);
  }

  public void removeAuth(Long userId, AuthorityLevel auth, CustomUserDetails customUserDetails) {
    validateAdminAuth(customUserDetails);
    User user = userRepository.findById(userId)
        .orElseThrow(RuntimeException::new);
    Authority authority = authorityRepository.findByUserAndRole(user, auth.getRole())
        .orElseThrow(RuntimeException::new);
    authorityRepository.delete(authority);
  }

  public void delete(Long userId, CustomUserDetails customUserDetails) {
    validateAdminAuth(customUserDetails);
    User user = userRepository.findById(userId)
        .orElseThrow(RuntimeException::new);

    userRepository.delete(user);
  }

  private void validateAdminAuth(CustomUserDetails customUserDetails) {
    Collection<? extends GrantedAuthority> authorities = customUserDetails.getAuthorities();
    if (!authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
      throw new RuntimeException("ban 권한이 없습니다.");
    }
  }

}
