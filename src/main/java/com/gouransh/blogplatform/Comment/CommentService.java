package com.gouransh.blogplatform.Comment;

import com.gouransh.blogplatform.Comment.Dto.CommentRequestDto;
import com.gouransh.blogplatform.Comment.Dto.CommentResponseDto;
import com.gouransh.blogplatform.GlobalException.ResourceNotFoundException;
import com.gouransh.blogplatform.GlobalException.UnauthorizedAccessException;
import com.gouransh.blogplatform.Post.Post;
import com.gouransh.blogplatform.Post.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;

    @Transactional
    public CommentResponseDto create(Long postId, CommentRequestDto commentRequestDto, String authorId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post not found!"));

        Comment comment = commentMapper.MapCommentRequestDtoToComment(commentRequestDto);
        comment.setPost(post);
        comment.setAuthorId(authorId);
        return commentMapper.MapCommentToCommentResponseDto(commentRepository.save(comment));
    }

    @Transactional
    public List<CommentResponseDto> retrieve(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("Post not found!");
        }
        return commentMapper.MapCommentToCommentResponseDto(commentRepository.findByPostId(postId));
    }

    @Transactional
    public CommentResponseDto update(
            Long postId, Long id,
            CommentRequestDto commentRequestDto, String authorId) {

        if (!postRepository.existsById(postId))
            throw new ResourceNotFoundException("Post not found!");

        Comment oldComment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("comment not found!"));

        if (!Objects.equals(authorId, oldComment.getAuthorId()))
            throw new UnauthorizedAccessException("you're not allowed to modify this comment.");

        Comment comment = commentMapper.MapCommentRequestDtoToComment(commentRequestDto);
        comment.setId(oldComment.getId());
        comment.setAuthorId(oldComment.getAuthorId());
        comment.setPost(oldComment.getPost());
        return commentMapper.MapCommentToCommentResponseDto(commentRepository.save(comment));
    }

    @Transactional
    public void delete(Long postId, Long id, String authorId) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("comment not found!"));

        if (!comment.getPost().getId().equals(postId))
            throw new ResourceNotFoundException("Comment not found!");

        if (!Objects.equals(authorId, comment.getAuthorId()))
            throw new UnauthorizedAccessException("you're not allowed to delete this comment.");

        commentRepository.delete(comment);
    }
}
