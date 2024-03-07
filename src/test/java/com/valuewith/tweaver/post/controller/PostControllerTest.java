package com.valuewith.tweaver.post.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.valuewith.tweaver.commons.PrincipalDetails;
import com.valuewith.tweaver.commons.security.service.TokenService;
import com.valuewith.tweaver.constants.ErrorCode;
import com.valuewith.tweaver.exception.CustomException;
import com.valuewith.tweaver.group.entity.TripGroup;
import com.valuewith.tweaver.group.service.TripGroupService;
import com.valuewith.tweaver.member.entity.Member;
import com.valuewith.tweaver.member.repository.MemberRepository;
import com.valuewith.tweaver.post.dto.PostForm;
import com.valuewith.tweaver.post.service.PostService;
import com.valuewith.tweaver.user.WithCustomMockUser;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
  TokenService tokenService;

  @MockBean
  MemberRepository memberRepository;

  @Test
  @WithCustomMockUser
  @DisplayName("후기작성 성공")
  public void create_post_success() throws Exception {
    // Given
    Member member = Member.builder().email("loopy@test.com").build();
    PostForm postForm = PostForm.builder()
        .tripGroupId(1L).title("Test Title").content("Test Content").build();
    PrincipalDetails principalDetails = new PrincipalDetails(member);

    /*
    1. loopy@test.com 회원(이하 loopy)은 tripGroup(id: 1L)을 만든적이 있다.
    2. loopy는 tripGroup의 후기를 작성한다.
    3. 결과: 성공
     */
    given(postService.createPost(principalDetails, postForm)).willReturn("ok");

    // When
    ResultActions postResult = mockMvc.perform(
        MockMvcRequestBuilders.post("/post")
            .contentType(MediaType.APPLICATION_JSON)
            .with(csrf())
            .header("Authorization", "Bearer {ACCESS_TOKEN}")
            .content(objectMapper.writeValueAsString(postForm)));

    // Then
    postResult.andExpect(status().isOk());
  }

  @Test
  @DisplayName("후기작성시 로그인 페이지로 이동")
  public void create_post_redirect() throws Exception {
    // Given
    PostForm postForm = PostForm.builder()
        .tripGroupId(1L).title("Test Title").content("Test Content").build();
    PrincipalDetails principalDetails = new PrincipalDetails();
    /*
    1. 로그인 하지 않고 요청을 보낼경우
    2. 401 에러가 발생한다.
    3. 결과: 성공
     */
    given(postService.createPost(principalDetails, postForm)).willThrow(new CustomException(
        ErrorCode.INVALID_USER_DETAILS));

    // When
    ResultActions postResult = mockMvc.perform(
        MockMvcRequestBuilders.post("/post")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(postForm)));

    // Then
    postResult.andExpect(status().is4xxClientError());
  }
}