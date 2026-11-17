package com.gouransh.blogplatform.Comment;

import com.gouransh.blogplatform.Comment.Dto.CommentRequestDto;
import com.gouransh.blogplatform.Comment.Dto.CommentResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CommentService {
    CommentResponseDto create(Long postId, CommentRequestDto commentRequestDto, String authorId);

    Slice<CommentResponseDto> retrieve(Long postId, Pageable pageable);

    CommentResponseDto update(Long postId, Long id, CommentRequestDto commentRequestDto, String authorId);

    void delete(Long postId, Long id, String authorId);
}
