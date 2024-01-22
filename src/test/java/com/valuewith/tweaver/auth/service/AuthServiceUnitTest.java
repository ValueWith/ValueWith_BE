package com.valuewith.tweaver.auth.service;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.valuewith.tweaver.auth.dto.AuthDto;
import com.valuewith.tweaver.constants.ImageType;
import com.valuewith.tweaver.defaultImage.repository.DefaultImageRepository;
import com.valuewith.tweaver.defaultImage.service.ImageService;
import com.valuewith.tweaver.member.entity.Member;
import com.valuewith.tweaver.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class AuthServiceUnitTest {

  @Mock
  private MemberRepository memberRepository;

  @Mock
  private DefaultImageRepository defaultImageRepository;

  @Mock
  private BCryptPasswordEncoder passwordEncoder;

  @Mock // Mock ImageService
  private ImageService imageService;

  @InjectMocks
  private AuthService authService;

  @DisplayName("프사 있는 회원가입 성공")
  @Test
  void signUpWithPicture() {
    // Given
    AuthDto.SignUpForm signUpForm = createSignUpForm();
    MockMultipartFile file = new MockMultipartFile("file", "file",
        "image/jpg", "file".getBytes());
    String password = signUpForm.getPassword();

    // When
    authService.signUp(signUpForm, file);

    // Then
    verify(passwordEncoder, times(1)).encode(password);
    verify(imageService, times(1)).uploadImageAndGetUrl(file, ImageType.MEMBER);
    verify(memberRepository, times(1)).save(any(Member.class));
  }

  private AuthDto.SignUpForm createSignUpForm() {
    AuthDto.SignUpForm form = new AuthDto.SignUpForm();
    form.setEmail("test@tweaver.com");
    form.setNickname("test");
    form.setPassword("1234test!");
    form.setGender("male");
    form.setAge(20);
    return form;
  }
}