package com.gouransh.blogplatform.Comment;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.gouransh.blogplatform.Comment.Dto.CommentRequestDto;
import com.gouransh.blogplatform.Comment.Dto.CommentResponseDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {
    Comment MapCommentRequestDtoToComment(CommentRequestDto commentRequestDto);

    CommentResponseDto MapCommentToCommentResponseDto(Comment comment);

    List<CommentResponseDto> MapCommentToCommentResponseDto(List<Comment> comments);
}
