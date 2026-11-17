package com.gouransh.blogplatform.Post;

import com.gouransh.blogplatform.Post.Dto.PostRequestDto;
import com.gouransh.blogplatform.Post.Dto.PostResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    PostResponseDto mapPostToPostResponseDto(Post post);

    Post mapPostRequestDtoToPost(PostRequestDto postRequestDto);
}
