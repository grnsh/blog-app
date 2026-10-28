package com.gouransh.blogplatform.Comment;

import com.gouransh.blogplatform.Comment.Dto.CommentRequestDto;
import com.gouransh.blogplatform.Comment.Dto.CommentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {
    Comment MapCommentRequestDtoToComment(CommentRequestDto commentRequestDto);

    CommentResponseDto MapCommentToCommentResponseDto(Comment comment);

    List<CommentResponseDto> MapCommentToCommentResponseDto(List<Comment> comments);
}
