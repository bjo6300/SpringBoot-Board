package boardexample.board.domain.post;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;
import boardexample.board.domain.BaseTimeEntity;
import boardexample.board.domain.comment.Comment;
import boardexample.board.domain.member.Member;
import lombok.*;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "POST")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "post_id")
    private Long id;


    @ManyToOne(fetch = LAZY) // JPA N+1
    @JoinColumn(name = "writer_id")
    private Member writer;


    @Column(length = 40, nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = true)
    private String filePath;



    //== 게시글을 삭제하면 달려있는 댓글 모두 삭제 ==//
    @OneToMany(mappedBy = "post", cascade = ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();




    //== 연관관계 편의 메서드 ==//
    public void confirmWriter(Member writer) {
        //writer는 변경이 불가능하므로 이렇게만 해주어도 될듯
        this.writer = writer;
        writer.addPost(this);
    }
    public void addComment(Comment comment){
        //comment의 Post 설정은 comment에서 함
        commentList.add(comment);
    }



    //== 내용 수정 ==//
    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateFilePath(String filePath) {
        this.filePath = filePath;
    }
}
