package com.gouransh.blogplatform.Post;

import com.gouransh.blogplatform.Post.Dto.PostRequestDto;
import com.gouransh.blogplatform.Post.Dto.PostResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    PostResponseDto create(PostRequestDto postRequestDto, String authorId);
    List<PostResponseDto> retrieve(Pageable pageable);
    PostResponseDto update(Long id, PostRequestDto postRequestDto, String authorId);
    void delete(Long id, String authorId);
}
