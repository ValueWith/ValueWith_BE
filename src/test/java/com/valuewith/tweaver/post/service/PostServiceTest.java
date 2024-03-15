package com.valuewith.tweaver.post.service;

import static com.valuewith.tweaver.constants.ErrorCode.POST_NOT_FOUND_FOR_UPDATE;
import static com.valuewith.tweaver.constants.ErrorCode.POST_WRITER_NOT_MATCH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import com.valuewith.tweaver.exception.CustomException;
import com.valuewith.tweaver.group.entity.TripGroup;
import com.valuewith.tweaver.member.entity.Member;
import com.valuewith.tweaver.post.dto.PostListResponseDto;
import com.valuewith.tweaver.post.dto.PostUpdateForm;
import com.valuewith.tweaver.post.entity.Post;
import com.valuewith.tweaver.post.repository.PostRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Test suit for PostService class and getFilteredPostList method.
 */
@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

  @Mock
  PostRepository postRepository;

  @InjectMocks
  PostService postService;

  List<Post> mockPostList;

  @BeforeEach
  void setup() {
    mockPostList = IntStream.range(0, 4)
        .mapToObj(i -> Post.builder()
            .postId((long) i)
            .title("title" + i)
            .content("content" + i)
            .tripGroup(TripGroup.builder().tripArea("busan").build())
            .createdDateTime(LocalDateTime.now())
            .build())
        .collect(Collectors.toList());
  }

  @Test
  @DisplayName("postList 기본값 조회 성공")
  void getPostListWithDefault() {

    // Given
    String area = "all";
    String title = "";
    Pageable mockPageable = PageRequest.of(0, 12);

    when(postRepository.findPostByTitleAndTripGroupArea(title, area, mockPageable)).thenReturn(
        mockPostList);

    // When
    PostListResponseDto postListResponseDto = postService.getFilteredPostList(area, title,
        mockPageable);

    // Then
    assertNotNull(postListResponseDto);
    assertEquals(1, postListResponseDto.getCurrentPage());
    assertEquals(1, postListResponseDto.getTotalPages());
    assertEquals(4, postListResponseDto.getTotalElements());
    assertEquals(4, postListResponseDto.getPostList().size());
    assertTrue(postListResponseDto.isLast());
  }

  @Test
  @DisplayName("postList 없는 지역 조회 성공")
  void getPostListWithNoArea() {

    // Given
    String area = "seoul";  // 4개 후기 모두 busan 후기다.
    String title = "";
    Pageable mockPageable = PageRequest.of(0, 12);

    when(postRepository.findPostByTitleAndTripGroupArea(title, area, mockPageable)).thenReturn(
        new ArrayList<>());

    // When
    PostListResponseDto postListResponseDto = postService.getFilteredPostList(area, title,
        mockPageable);

    // Then
    assertNotNull(postListResponseDto);
    assertEquals(1, postListResponseDto.getCurrentPage());
    assertEquals(0, postListResponseDto.getTotalPages());
    assertEquals(0, postListResponseDto.getTotalElements());
    assertEquals(0, postListResponseDto.getPostList().size());
    assertTrue(postListResponseDto.isLast());
  }

  @Test
  void updatePost() throws Exception {

    // Given
    PostUpdateForm postUpdateForm = PostUpdateForm.builder()
        .title("New title")
        .content("New content")
        .build();

    Post postForUpdate = Post.builder()
        .title("Old title")
        .content("Old content")
        .member(Member.builder().memberId(1L).build())
        .build();

    Long james = 1L;  // James 는 JamesPost를 썼다고 가정
    Long jamesPost = 1L;

    given(postRepository.findById(jamesPost))
        .willReturn(java.util.Optional.ofNullable(postForUpdate));

    // When
    postService.updatePostList(postUpdateForm, james, jamesPost);

    // Then
    assertThat(postForUpdate.getTitle()).isEqualTo(postUpdateForm.getTitle());
    assertThat(postForUpdate.getContent()).isEqualTo(postUpdateForm.getContent());  // 성공
  }

  @Test
  @DisplayName("post 작성자가 아니라서 401에러 발생")
  void updatePostFailThrowsMember401() throws Exception {

    // Given
    PostUpdateForm postUpdateForm = PostUpdateForm.builder()
        .title("New title")
        .content("New content")
        .build();

    Post postForUpdate = Post.builder()
        .title("Old title")
        .content("Old content")
        .member(Member.builder().memberId(2L).build())
        .build();

    Long james = 1L;
    Long alicePost = 2L;

    given(postRepository.findById(alicePost))
        .willReturn(java.util.Optional.ofNullable(postForUpdate));

    // When
    try {
      postService.updatePostList(new PostUpdateForm(), james, alicePost);
      fail("CustomException 발생하지 않음");

      // Then
    } catch (CustomException e) {  // james가 쓰지 않은 글이 조회되었지만 글 작성자가 다르므로 401 발생
      assertThatThrownBy(() -> {
        postService.updatePostList(postUpdateForm, james, alicePost);
      }).isInstanceOf(CustomException.class).hasMessage(POST_WRITER_NOT_MATCH.getDescription());

    }
  }

  @Test
  @DisplayName("post를 찾을 수 없어 404에러 발생")
  void updatePostFailThrowsPost404() throws Exception {

    // Given
    PostUpdateForm postUpdateForm = PostUpdateForm.builder()
        .title("New title")
        .content("New content")
        .build();

    Long james = 1L;
    Long jamesPost = 1L;

    given(postRepository.findById(jamesPost))
        .willReturn(java.util.Optional.empty());  // James는 post를 쓴적이 없다.

    // When
    // Then
    assertThatThrownBy(() -> {
      postService.updatePostList(postUpdateForm, james, jamesPost);
    }).isInstanceOf(CustomException.class).hasMessage(POST_NOT_FOUND_FOR_UPDATE.getDescription());
  }
}