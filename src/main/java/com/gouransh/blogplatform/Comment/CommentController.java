package com.gouransh.blogplatform.Comment;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gouransh.blogplatform.Comment.Dto.CommentRequestDto;
import com.gouransh.blogplatform.Comment.Dto.CommentResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable Long postId,
            @RequestBody CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal Jwt jwt) {

        String authorId = jwt.getSubject();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentService.create(postId, commentRequestDto, authorId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CommentResponseDto>> retrieveComment(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.retrieve(postId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long postId, @PathVariable Long id,
            @RequestBody CommentRequestDto commentRequestDto) {
        return ResponseEntity.ok(commentService.update(postId, id, commentRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long postId, @PathVariable Long id) {
        commentService.delete(postId, id);
        return ResponseEntity.noContent().build();
    }

}
