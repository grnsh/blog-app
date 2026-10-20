package com.gouransh.blogplatform.Post;

import com.gouransh.blogplatform.GlobalException.UnauthorizedAccessException;
import com.gouransh.blogplatform.Post.Dto.PostRequestDto;
import com.gouransh.blogplatform.Post.Dto.PostResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(
            @Valid @RequestBody PostRequestDto postRequestDto,
            @AuthenticationPrincipal Jwt jwt) {

        String authorId = jwt.getSubject();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postService.create(postRequestDto, authorId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostResponseDto>> retrievePost() {
        return ResponseEntity.ok(postService.retrieve());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody PostRequestDto postRequestDto,
            @AuthenticationPrincipal Jwt jwt) throws UnauthorizedAccessException {
        String authorId = jwt.getSubject();
        return ResponseEntity.ok(postService.update(id, postRequestDto, authorId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long id,
            @AuthenticationPrincipal Jwt jwt) throws UnauthorizedAccessException {
        String authorId = jwt.getSubject();
        postService.delete(id, authorId);
        return ResponseEntity.noContent().build();
    }
}
