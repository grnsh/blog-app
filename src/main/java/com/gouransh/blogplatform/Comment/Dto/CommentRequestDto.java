package com.gouransh.blogplatform.Comment.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentRequestDto {
    @NotBlank(message = "empty comment not allowed.")
    private String content;
}
