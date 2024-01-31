package com.valuewith.tweaver.auth.service;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.valuewith.tweaver.auth.dto.AuthDto;
import com.valuewith.tweaver.constants.ImageType;
import com.valuewith.tweaver.defaultImage.entity.DefaultImage;
import com.valuewith.tweaver.defaultImage.entity.DefaultImage.DefaultImageBuilder;
import com.valuewith.tweaver.defaultImage.repository.DefaultImageRepository;
import com.valuewith.tweaver.defaultImage.service.ImageService;
import com.valuewith.tweaver.member.entity.Member;
import com.valuewith.tweaver.member.repository.MemberRepository;
import java.time.LocalDateTime;
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

  @DisplayName("프사 없는 회원가입 성공")
  @Test
  void signUpWithoutPicture() {
    // Given
    AuthDto.SignUpForm signUpForm = createSignUpForm();
    DefaultImage defaultImage = createDefaultImage();
    String password = signUpForm.getPassword();

    // When
    // DefaultImage로 설정하기로 한 이미지는 DB에 이미 있으므로 이미지를 리턴해준다.
    when(defaultImageRepository.findByImageName("멤버")).thenReturn(defaultImage);
    authService.signUp(signUpForm, null);

    // Then
    verify(passwordEncoder, times(1)).encode(password);
    verify(defaultImageRepository, times(1)).findByImageName("멤버");
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

  private DefaultImage createDefaultImage() {
    return DefaultImage.builder()
        .defaultImageId(1L)
        .defaultImageUrl("http://test.test")
        .imageName("test")
        .createdDateTime(LocalDateTime.now()).build();
  }
}