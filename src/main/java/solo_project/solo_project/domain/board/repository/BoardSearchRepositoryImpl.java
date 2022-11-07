package solo_project.solo_project.domain.board.repository;

import static solo_project.solo_project.domain.board.entity.QBoard.board;
import static solo_project.solo_project.domain.user.entity.QUser.user;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import solo_project.solo_project.domain.board.mapper.response.BoardDetailsResponse;

public class BoardSearchRepositoryImpl implements BoardSearchRepository{

  private final JPAQueryFactory jpaQueryFactory;

  public BoardSearchRepositoryImpl(EntityManager entityManager) {
    this.jpaQueryFactory = new JPAQueryFactory(entityManager);
  }

  @Override
  public BoardDetailsResponse findBoardDetails(Long boardId) {
    return jpaQueryFactory.select(
            Projections.constructor(BoardDetailsResponse.class,
                board.title,
                board.description,
                user.nickname.nickname
                )
        )
        .from(board)
        .leftJoin(user).on(board.userId.eq(user.id))
        .where(board.id.eq(boardId))
        .fetchOne();
  }
}
