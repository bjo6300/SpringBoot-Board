//package boardexample.board;
//
//import boardexample.board.domain.comment.Comment;
//import boardexample.board.domain.comment.repository.CommentRepository;
//import boardexample.board.domain.member.Member;
//import boardexample.board.domain.member.Role;
//import boardexample.board.domain.member.repository.MemberRepository;
//import boardexample.board.domain.post.Post;
//import boardexample.board.domain.post.repository.PostRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.transaction.Transactional;
//
//import static java.lang.Long.parseLong;
//import static java.lang.String.format;
//import static java.lang.String.valueOf;
//
//@RequiredArgsConstructor
//@Component
//public class InitService {
//
//
//    private final Init init;
//
//
//    @PostConstruct
//    public void init(){
//
//        init.save();
//    }
//
//    @RequiredArgsConstructor
//    @Component
//    private static class Init{
//        private final MemberRepository memberRepository;
//
//        private final PostRepository postRepository;
//        private final CommentRepository commentRepository;
//
//        @Transactional
//        public void save() {
//            PasswordEncoder delegatingPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//
//
//            //== MEMBER 저장 ==//
//            memberRepository.save(Member.builder().username("username1").password(delegatingPasswordEncoder.encode("1234567890")).name("USER1").nickName("밥 잘먹는 동훈이1").role(Role.USER).age(22).build());
//
//            memberRepository.save(Member.builder().username("username2").password(delegatingPasswordEncoder.encode("1234567890")).name("USER2").nickName("밥 잘먹는 동훈이2").role(Role.USER).age(22).build());
//
//            memberRepository.save(Member.builder().username("username3").password(delegatingPasswordEncoder.encode("1234567890")).name("USER3").nickName("밥 잘먹는 동훈이3").role(Role.USER).age(22).build());
//
//            Member member = memberRepository.findById(1L).orElse(null);
//
//
//            for(int i = 0; i<=50; i++ ){
//                Post post = Post.builder().title(format("게시글 %s", i)).content(format("내용 %s", i)).build();
//                post.confirmWriter(memberRepository.findById((long) (i % 3 + 1)).orElse(null));
//                postRepository.save(post);
//            }
//
//            for(int i = 1; i<=150; i++ ){
//                Comment comment = Comment.builder().content("댓글" + i).build();
//                comment.confirmWriter(memberRepository.findById((long) (i % 3 + 1)).orElse(null));
//
//                comment.confirmPost(postRepository.findById(parseLong(valueOf(i%50 + 1))).orElse(null));
//                commentRepository.save(comment);
//            }
//
//
//            commentRepository.findAll().stream().forEach(comment -> {
//
//                for(int i = 1; i<=50; i++ ){
//                    Comment recomment = Comment.builder().content("대댓글" + i).build();
//                    recomment.confirmWriter(memberRepository.findById((long) (i % 3 + 1)).orElse(null));
//
//                    recomment.confirmPost(comment.getPost());
//                    recomment.confirmParent(comment);
//                    commentRepository.save(recomment);
//                }
//
//            });
//        }
//    }
//
//
//}
