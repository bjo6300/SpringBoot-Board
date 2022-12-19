package boardexample.board.domain.comment.service;

import boardexample.board.domain.comment.Comment;
import boardexample.board.domain.comment.dto.CommentSaveDto;
import boardexample.board.domain.comment.dto.CommentUpdateDto;
import boardexample.board.domain.comment.exception.CommentException;

import java.util.List;

public interface CommentService {

    void save(Long postId, CommentSaveDto commentSaveDto);
    void saveReComment(Long postId, Long parentId, CommentSaveDto commentSaveDto);

    void update(Long id, CommentUpdateDto commentUpdateDto);

    void remove(Long id) throws CommentException;
}
