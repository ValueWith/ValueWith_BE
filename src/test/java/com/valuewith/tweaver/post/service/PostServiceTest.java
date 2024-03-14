package com.valuewith.tweaver.post.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.valuewith.tweaver.group.entity.TripGroup;
import com.valuewith.tweaver.post.dto.PostListResponseDto;
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
    Pageable mockPageable = PageRequest.of(0,12);

    when(postRepository.findPostByTitleAndTripGroupArea(title, area, mockPageable)).thenReturn(mockPostList);

    // When
    PostListResponseDto postListResponseDto = postService.getFilteredPostList(area, title, mockPageable);

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
    Pageable mockPageable = PageRequest.of(0,12);

    when(postRepository.findPostByTitleAndTripGroupArea(title, area, mockPageable)).thenReturn(new ArrayList<>());

    // When
    PostListResponseDto postListResponseDto = postService.getFilteredPostList(area, title, mockPageable);

    // Then
    assertNotNull(postListResponseDto);
    assertEquals(1, postListResponseDto.getCurrentPage());
    assertEquals(0, postListResponseDto.getTotalPages());
    assertEquals(0, postListResponseDto.getTotalElements());
    assertEquals(0, postListResponseDto.getPostList().size());
    assertTrue(postListResponseDto.isLast());
  }
}