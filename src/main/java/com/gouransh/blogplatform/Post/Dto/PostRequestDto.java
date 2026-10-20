package com.gouransh.blogplatform.Post.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostRequestDto {
    @NotBlank(message = "title is mandatory.")
    @Size(min = 5, message = "title must be > than 5 characters.")
    private String title;

    @NotBlank(message = "content is mandatory.")
    @Size(min = 20, message = "content must be > than 20 characters.")
    private String content;
}
