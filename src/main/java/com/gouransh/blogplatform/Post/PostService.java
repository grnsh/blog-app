package com.gouransh.blogplatform.Post;

import com.gouransh.blogplatform.GlobalException.ResourceNotFoundException;
import com.gouransh.blogplatform.GlobalException.UnauthorizedAccessException;
import com.gouransh.blogplatform.Post.Dto.PostRequestDto;
import com.gouransh.blogplatform.Post.Dto.PostResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Transactional
    public PostResponseDto create(PostRequestDto postRequestDto, String authorId) {
        Post post = postMapper.mapPostRequestDtoToPost(postRequestDto);
        post.setAuthorId(authorId);
        postRepository.save(post);
        return postMapper.mapPostToPostResponseDto(post);
    }

    @Transactional
    public List<PostResponseDto> retrieve() {
        return postMapper.mapPostToPostResponseDto(postRepository.findAll());
    }

    @Transactional
    public PostResponseDto update(
            Long id,
            PostRequestDto postRequestDto,
            String authorId) throws UnauthorizedAccessException {

        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post not found!"));

        if (!Objects.equals(post.getAuthorId(), authorId))
            throw new UnauthorizedAccessException("you are not allowed to change this post.");

        Post newPost = postMapper.mapPostRequestDtoToPost(postRequestDto);
        newPost.setId(post.getId());
        newPost.setCreatedAt(post.getCreatedAt());
        newPost.setAuthorId(post.getAuthorId());

        return postMapper.mapPostToPostResponseDto(postRepository.save(newPost));
    }

    @Transactional
    public void delete(Long id, String authorId) throws UnauthorizedAccessException {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post not found!"));

        if (!Objects.equals(post.getAuthorId(), authorId))
            throw new UnauthorizedAccessException("you are not allowed to delete this post.");

        postRepository.delete(post);
    }
}
