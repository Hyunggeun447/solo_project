package solo_project.solo_project.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import solo_project.solo_project.domain.user.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

  @Modifying(clearAutomatically = true)
  @Query("update Authority a set a.isDeleted = true where a.user.id =:userId and a.role =:role")
  void deleteAuthorityByUserIdAndRole(@Param("userId") Long userId, @Param("role") String role);

}
