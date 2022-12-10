package boardexample.board.domain.post.repository;

import boardexample.board.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}

