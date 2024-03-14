package com.valuewith.tweaver.post.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.valuewith.tweaver.commons.security.service.TokenService;
import com.valuewith.tweaver.group.service.TripGroupService;
import com.valuewith.tweaver.member.repository.MemberRepository;
import com.valuewith.tweaver.post.dto.PostForm;
import com.valuewith.tweaver.post.service.PostService;
import com.valuewith.tweaver.postImage.service.PostImageService;
import com.valuewith.tweaver.user.WithCustomMockUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(PostController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class PostControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  PostService postService;

  @MockBean
  TripGroupService tripGroupService;

  @MockBean
  PostImageService postImageService;

  @MockBean
  TokenService tokenService;

  @MockBean
  MemberRepository memberRepository;

  PostForm postForm = PostForm.builder()
      .tripGroupId(1L)
      .title("Test Title")
      .content("Test Content")
      .build();

  @Test
  @WithCustomMockUser
  @DisplayName("이미지 없는 후기작성 성공")
  public void createPostSuccess() throws Exception {
    /*
    이미지 없는 후기작성 성공 테스트
    1. 회원(CustomMockUser)은 tripGroup(id: 1L)을 만든적이 있다.
    2. 회원은 이미지 없이 tripGroup의 후기를 작성한다.
    3. 예상 결과: 성공 (201 Created)
     */

    // Given
    MockMultipartFile postFormFile = new MockMultipartFile("postForm", "", "application/json",
        objectMapper.writeValueAsBytes(postForm));

    // When
    ResultActions postResult = mockMvc.perform(
        MockMvcRequestBuilders.multipart("/post")
            .file(postFormFile)
            .with(csrf())
            .header("Authorization", "Bearer {ACCESS_TOKEN}")
            .content(objectMapper.writeValueAsString(postForm)));

    // Then
    postResult.andExpect(status().isCreated());  // 201 Ok
  }

  @Test
  @WithCustomMockUser
  @DisplayName("이미지 있는 후기작성 성공")
  public void createPostWithImageSuccess() throws Exception {
    /*
    이미지를 첨부한 후기작성 성공 테스트
    1. 회원(CustomMockUser)은 tripGroup(id: 1L)을 만든적이 있다.
    2. 회원은 이미지 2개를 첨부하고 tripGroup의 후기를 작성한다.
    3. 예상 결과: 성공
     */

    // Given
    MockMultipartFile postFormFile = new MockMultipartFile("postForm", "", "application/json",
        objectMapper.writeValueAsBytes(postForm));
    MockMultipartFile image1 = new MockMultipartFile(
        "image1", "i1.png", "image/jpeg",
        "image_content".getBytes());
    MockMultipartFile image2 = new MockMultipartFile(
        "image2", "i2.png", "image/jpeg",
        "image_content".getBytes());

    // When
    ResultActions postResult = mockMvc.perform(
        MockMvcRequestBuilders.multipart("/post")
            .file(image1)
            .file(image2)
            .file(postFormFile)
            .with(csrf())
            .header("Authorization", "Bearer {ACCESS_TOKEN}")
            .content(objectMapper.writeValueAsString(postForm)));

    // Then
    postResult.andExpect(status().isCreated());  // 200 Ok
  }

  @Test
  @WithAnonymousUser
  @DisplayName("인증되지 않은 상태에서 후기작성시 로그인 페이지로 이동")
  public void createPostRedirect() throws Exception {
    /*
    후기 작성시 로그인 페이지로 이동 테스트
    1. 로그인 하지 않고 요청을 보낼경우 (헤더에도 Authorization 제거)
    2. 스프링 시큐리티에서 로그인 페이지로 리다이렉트 시킨다. (실제 api 동작은 401 반환)
    3. 결과: 302
     */

    // Given
    MockMultipartFile postFormFile = new MockMultipartFile("postForm", "", "application/json",
        objectMapper.writeValueAsBytes(postForm));

    // When
    ResultActions postResult = mockMvc.perform(
        MockMvcRequestBuilders.multipart("/post")
            .file(postFormFile)
            .with(csrf())
            .content(objectMapper.writeValueAsString(postForm)));

    // Then
    postResult.andExpect(status().isFound());  // 302 Found
  }
}