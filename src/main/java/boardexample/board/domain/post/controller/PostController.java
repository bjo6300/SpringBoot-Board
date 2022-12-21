package boardexample.board.domain.post.controller;

import boardexample.board.domain.post.cond.PostSearchCondition;
import boardexample.board.domain.post.dto.PostSaveDto;
import boardexample.board.domain.post.dto.PostUpdateDto;
import boardexample.board.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import org.springframework.data.domain.Pageable;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    /**
     * 게시글 저장
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/post")
    public void save(@Valid @ModelAttribute PostSaveDto postSaveDto){
        postService.save(postSaveDto);
    }

    /**
     * 게시글 수정
     */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/post/{postId}")
    public void update(@PathVariable("postId") Long postId,
                       @ModelAttribute PostUpdateDto postUpdateDto){

        postService.update(postId, postUpdateDto);
    }

    /**
     * 게시글 삭제
     */
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/post/{postId}")
    public void delete(@PathVariable("postId") Long postId){
        postService.delete(postId);
    }


    /**
     * 게시글 조회
     */
    @GetMapping("/post/{postId}")
    public ResponseEntity getInfo(@PathVariable("postId") Long postId){
        return ResponseEntity.ok(postService.getPostInfo(postId));
    }

    /**
     * 게시글 검색
     */
    @GetMapping("/post")
    public ResponseEntity search(Pageable pageable,
                                 @ModelAttribute PostSearchCondition postSearchCondition){

        return ResponseEntity.ok(postService.getPostList(pageable,postSearchCondition));
    }
}
