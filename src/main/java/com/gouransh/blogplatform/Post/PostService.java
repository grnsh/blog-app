package com.gouransh.blogplatform.Post;

import com.gouransh.blogplatform.Post.Dto.PostRequestDto;
import com.gouransh.blogplatform.Post.Dto.PostResponseDto;

import java.util.List;

public interface PostService {
    PostResponseDto create(PostRequestDto postRequestDto, String authorId);
    List<PostResponseDto> retrieve();
    PostResponseDto update(Long id, PostRequestDto postRequestDto, String authorId);
    void delete(Long id, String authorId);
}
