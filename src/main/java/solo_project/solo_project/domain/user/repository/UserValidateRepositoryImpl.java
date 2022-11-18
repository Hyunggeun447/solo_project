package solo_project.solo_project.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;

public class UserValidateRepositoryImpl implements UserValidateRepository{

  private final JPAQueryFactory jpaQueryFactory;

  public UserValidateRepositoryImpl(EntityManager em) {
    this.jpaQueryFactory = new JPAQueryFactory(em);
  }

}
