package boardexample.board.domain.post.dto;

import boardexample.board.domain.post.Post;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BriefPostInfo {

    private Long postId;

    private String title;//제목
    private String content;//내용
    private String writerName;//작성자의 이름
    private String createdDate; //작성일

    public BriefPostInfo(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.writerName = post.getWriter().getName();
        this.createdDate = post.getCreatedDate().toString();
    }
}
