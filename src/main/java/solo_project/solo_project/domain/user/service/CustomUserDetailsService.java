package solo_project.solo_project.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import solo_project.solo_project.domain.user.entity.User;
import solo_project.solo_project.domain.user.pojo.CustomUserDetails;
import solo_project.solo_project.domain.user.repository.UserRepository;

@Transactional
@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmailEmailAddress(email)
        .orElseThrow(() -> new RuntimeException("이메일를 찾을 수 없습니다."));
    return CustomUserDetails.of(user);
  }
}
