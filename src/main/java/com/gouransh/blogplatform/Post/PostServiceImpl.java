package com.gouransh.blogplatform.Post;

import com.gouransh.blogplatform.GlobalException.BadArgumentException;
import com.gouransh.blogplatform.GlobalException.ResourceNotFoundException;
import com.gouransh.blogplatform.GlobalException.UnauthorizedAccessException;
import com.gouransh.blogplatform.Post.Dto.PostRequestDto;
import com.gouransh.blogplatform.Post.Dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("id", "title", "createdAt");
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
    public Slice<PostResponseDto> retrieve(Pageable pageable) {
        pageable.getSort().forEach(
                order -> {
                    if(!ALLOWED_SORT_FIELDS.contains(order.getProperty())){
                        throw new BadArgumentException("unsupported sort field.");
                    }
                } );
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
