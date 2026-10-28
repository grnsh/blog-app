package com.gouransh.blogplatform.Post;

import com.gouransh.blogplatform.Post.Dto.PostRequestDto;
import com.gouransh.blogplatform.Post.Dto.PostResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    PostResponseDto mapPostToPostResponseDto(Post post);

    List<PostResponseDto> mapPostToPostResponseDto(List<Post> posts);

    Post mapPostRequestDtoToPost(PostRequestDto postRequestDto);
}
