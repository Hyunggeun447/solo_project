package solo_project.solo_project.domain.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import solo_project.solo_project.domain.user.entity.Authority;
import solo_project.solo_project.domain.user.entity.User;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

  Optional<Authority> findByUserAndRole(User user, String role);
}
