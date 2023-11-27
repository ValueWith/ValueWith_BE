package com.valuewith.tweaver.auth.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.valuewith.tweaver.auth.dto.AuthDto;
import com.valuewith.tweaver.auth.dto.AuthDto.SignUpForm;
import com.valuewith.tweaver.auth.service.AuthService;
import com.valuewith.tweaver.commons.security.service.TokenService;
import com.valuewith.tweaver.defaultImage.service.ImageService;
import com.valuewith.tweaver.exception.GlobalExceptionHandler;
import com.valuewith.tweaver.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AuthController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc
class AuthControllerTest {

  @MockBean
  AuthService authService;
  @MockBean
  ImageService imageService;
  @MockBean
  GlobalExceptionHandler globalExceptionHandler;
  @MockBean
  TokenService tokenService;
  @MockBean
  MemberRepository memberRepository;

  @Autowired
  MockMvc mockMvc;

  ObjectMapper objectMapper;
  MockMultipartFile imgfile = new MockMultipartFile(
      "image", "test.jpeg", "image/jpeg", "image_content".getBytes());
  AuthDto.SignUpForm signUpForm = SignUpForm.builder()
      .email("c@a.a")
      .age(12)
      .gender("male")
      .nickname("a")
      .password("a")
      .build();

  @Test
  @WithMockUser
  @DisplayName("회원가입 성공-이미지 있음")
  void success_signup_test_with_image() throws Exception {
    /**
     * 회원가입 성공여부는 SignUpForm 필드가 모두 작성될 경우입니다.
     * 이미지가 있는 경우
     */
    mockMvc.perform(
            multipart("/auth/signup")
                .file(imgfile)
                .param("age", String.valueOf(signUpForm.getAge()))
                .param("email", signUpForm.getEmail())
                .param("gender", signUpForm.getGender())
                .param("nickname", signUpForm.getNickname())
                .param("password", signUpForm.getPassword())
                .with(csrf())
        )
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser
  @DisplayName("회원가입 성공-이미지 없음")
  void success_signup_test_without_image() throws Exception {
    /**
     * 회원가입 성공여부는 SignUpForm 필드가 모두 작성될 경우입니다.
     * 이미지가 있는 경우
     */
    mockMvc.perform(
            multipart("/auth/signup")
                .param("age", String.valueOf(signUpForm.getAge()))
                .param("email", signUpForm.getEmail())
                .param("gender", signUpForm.getGender())
                .param("nickname", signUpForm.getNickname())
                .param("password", signUpForm.getPassword())
                .with(csrf())
        )
        .andExpect(status().isOk());
  }
}