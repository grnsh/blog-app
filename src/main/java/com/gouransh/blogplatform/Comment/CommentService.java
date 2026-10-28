package com.gouransh.blogplatform.Comment;

import com.gouransh.blogplatform.Comment.Dto.CommentRequestDto;
import com.gouransh.blogplatform.Comment.Dto.CommentResponseDto;

import java.util.List;

public interface CommentService {
    CommentResponseDto create(Long postId, CommentRequestDto commentRequestDto, String authorId);
    List<CommentResponseDto> retrieve(Long postId);
    CommentResponseDto update(Long postId, Long id, CommentRequestDto commentRequestDto, String authorId);
    void delete(Long postId, Long id, String authorId);
}
