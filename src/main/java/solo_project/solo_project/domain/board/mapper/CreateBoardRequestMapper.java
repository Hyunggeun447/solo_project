package solo_project.solo_project.domain.board.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import solo_project.solo_project.common.mapper.GenericMapper;
import solo_project.solo_project.domain.board.entity.Board;
import solo_project.solo_project.domain.board.mapper.request.CreateBoardRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CreateBoardRequestMapper extends GenericMapper<CreateBoardRequest, Board> {

}
