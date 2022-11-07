package solo_project.solo_project.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import solo_project.solo_project.domain.user.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

}
