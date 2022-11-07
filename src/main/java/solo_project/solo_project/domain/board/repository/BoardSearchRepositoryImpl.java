package solo_project.solo_project.domain.board.repository;

import static solo_project.solo_project.domain.board.entity.QBoard.board;
import static solo_project.solo_project.domain.user.entity.QUser.user;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import solo_project.solo_project.domain.board.mapper.response.BoardDetailsResponse;
import solo_project.solo_project.domain.board.mapper.response.BoardSummaryResponse;

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

  @Override
  public Slice<BoardSummaryResponse> findBoardList(Pageable pageable) {
    List<BoardSummaryResponse> boardSummaryResponseList = jpaQueryFactory.select(
            Projections.constructor(BoardSummaryResponse.class,
                board.title,
                user.nickname.nickname,
                board.createdAt
            )
        )
        .from(board)
        .leftJoin(user).on(board.userId.eq(user.id))
        .limit(pageable.getPageSize() + 1)
        .offset(pageable.getOffset())
        .fetch();

    boolean hasNext = false;

    if (boardSummaryResponseList.size() > pageable.getPageSize()) {
      boardSummaryResponseList.remove(pageable.getPageSize());
      hasNext = true;
    }

    return new SliceImpl<>(boardSummaryResponseList, pageable, hasNext);
  }
}
