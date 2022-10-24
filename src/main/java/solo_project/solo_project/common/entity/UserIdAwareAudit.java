package solo_project.solo_project.common.entity;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import solo_project.solo_project.domain.user.pojo.CustomUserDetails;

@Component
public class UserIdAwareAudit implements AuditorAware<Long> {

  @Override
  public Optional<Long> getCurrentAuditor() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      return null;
    }
    CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
    Long id = principal.getId();
    return Optional.of(id);
  }

}
