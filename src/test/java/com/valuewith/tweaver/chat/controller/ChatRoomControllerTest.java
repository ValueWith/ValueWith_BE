package com.valuewith.tweaver.chat.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.valuewith.tweaver.chat.service.ChatRoomService;
import com.valuewith.tweaver.commons.security.service.TokenService;
import com.valuewith.tweaver.constants.Provider;
import com.valuewith.tweaver.exception.GlobalExceptionHandler;
import com.valuewith.tweaver.member.entity.Member;
import com.valuewith.tweaver.member.repository.MemberRepository;
import com.valuewith.tweaver.message.repository.MessageRepository;
import com.valuewith.tweaver.message.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ChatRoomController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc
class ChatRoomControllerTest {

  @MockBean
  ChatRoomController chatRoomController;
  @MockBean
  GlobalExceptionHandler globalExceptionHandler;
  @MockBean
  ChatRoomService chatRoomService;
  @MockBean
  TokenService tokenService;
  @MockBean
  MemberRepository memberRepository;
  @MockBean
  MessageService messageService;
  @MockBean
  MessageRepository messageRepository;

  @Autowired
  MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    Member member = Member.builder()
        .memberId(1L)
        .email("a@a.a")
        .nickName("a")
        .age(11)
        .gender("male")
        .profileUrl("http://a")
        .provider(Provider.NORMAL)
        .build();

    memberRepository.save(member);
  }

  @Test
  @WithMockUser
  @DisplayName("채팅룸 불러오기 호출 성공: 인증된 사용자")
  void pass_charRoom_test() throws Exception {
    /*
    로그인 된 사용자가 호출하면 성공
     */
    mockMvc.perform(get("/chat/room")
            .accept(MediaType.APPLICATION_JSON)
            .with(csrf()))
        .andDo(print())
        .andExpect(status().isOk());

  }

  @Test
  @DisplayName("채팅룸 불러오기 호출 실패: 스프링 시큐리티 작동")
  void fail_charRoom_test() throws Exception {
    /*
    로그인을 안하면 스프링 시큐리티는 로그인 페이지로 redirection 시킨다.
    컨트롤러 내부 작동은 유닛 테스트를 진행해봐야 할 것 같습니다. - eod940
     */
    mockMvc.perform(get("/chat/room"))
        .andDo(print())
        .andExpect(status().is3xxRedirection());
  }
}