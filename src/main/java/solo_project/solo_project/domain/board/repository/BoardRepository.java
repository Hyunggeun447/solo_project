package solo_project.solo_project.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import solo_project.solo_project.domain.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearchRepository {

}
