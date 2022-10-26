package solo_project.solo_project.common.entity;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import solo_project.solo_project.domain.user.pojo.CustomUserDetails;

@Component
public class UserAwareAudit implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      return null;
    }
    Object principal = authentication.getPrincipal();
    if (principal.equals("anonymousUser")) {
      return null;
    }
    CustomUserDetails customUserDetails = (CustomUserDetails) principal;
    return Optional.of(customUserDetails.getUsername());
  }

}
