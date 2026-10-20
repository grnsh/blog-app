package com.gouransh.blogplatform.Comment.Dto;

import lombok.Data;

@Data
public class CommentResponseDto {
    private Long id;
    private String authorId;
    private String content;
}
