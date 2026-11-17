package com.gouransh.blogplatform.Comment;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Slice<Comment> findByPostId(@Param("postId") Long postId, Pageable pageable);
}