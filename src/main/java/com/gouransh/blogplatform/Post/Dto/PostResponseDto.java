package com.gouransh.blogplatform.Post.Dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PostResponseDto {
    private Long id;
    private String authorId;
    private String title;
    private String content;
    private Timestamp createdAt;
}

