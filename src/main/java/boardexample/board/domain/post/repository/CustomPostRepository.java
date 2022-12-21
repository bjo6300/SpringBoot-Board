package boardexample.board.domain.post.repository;

import boardexample.board.domain.post.Post;
import boardexample.board.domain.post.cond.PostSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CustomPostRepository {
    Page<Post> search(PostSearchCondition postSearchCondition, Pageable pageable);
}

