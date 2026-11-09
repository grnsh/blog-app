package com.gouransh.blogplatform.Post;

import com.gouransh.blogplatform.GlobalException.ResourceNotFoundException;
import com.gouransh.blogplatform.GlobalException.UnauthorizedAccessException;
import com.gouransh.blogplatform.Post.Dto.PostRequestDto;
import com.gouransh.blogplatform.Post.Dto.PostResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

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
    public Slice<PostResponseDto> retrieve(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort;
        if(sortDirection.equalsIgnoreCase("DESC"))
            sort = Sort.by(sortBy).descending();
        else
            sort = Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);

        return postRepository.findAllBy(pageable)
                .map(postMapper::mapPostToPostResponseDto);
    }

    @Transactional
    public PostResponseDto update(Long id, PostRequestDto postRequestDto, String authorId) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post not found!"));

        if (!Objects.equals(post.getAuthorId(), authorId))
            throw new UnauthorizedAccessException("you're not allowed to change this post.");

        Post newPost = postMapper.mapPostRequestDtoToPost(postRequestDto);
        newPost.setId(post.getId());
        newPost.setCreatedAt(post.getCreatedAt());
        newPost.setAuthorId(post.getAuthorId());

        return postMapper.mapPostToPostResponseDto(postRepository.save(newPost));
    }

    @Transactional
    public void delete(Long id, String authorId) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post not found!"));

        if (!Objects.equals(post.getAuthorId(), authorId))
            throw new UnauthorizedAccessException("you're not allowed to delete this post.");

        postRepository.delete(post);
    }
}
