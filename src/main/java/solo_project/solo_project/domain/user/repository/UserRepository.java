package solo_project.solo_project.domain.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import solo_project.solo_project.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long>, UserValidateRepository {

  Optional<User> findByEmail(String email);

  Boolean existsByNicknameNickname(String nickname);

  Boolean existsByEmailEmailAddress(String emailAddress);

}
