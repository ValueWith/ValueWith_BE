# ValueWith_BE
사용자들이 쉽게 여행을 떠날 수 있도록 도와주는 당일치기 여행 스케줄링 + 일행 모집 커뮤니티 플랫폼입니다.
- 전체 구조: https://github.com/ValueWith
- API 문서(swagger): https://tweaver.site/swagger-ui.html
- 기능 문서는 [WIKI](https://github.com/ValueWith/ValueWith_BE/wiki/%08%ED%8A%B8%EC%9C%84%EB%B2%84-%EA%B5%AC%ED%98%84-%EA%B8%B0%EB%8A%A5-(UseCase))에서 확인하실 수 있습니다.

## 디렉토리 구조
<details>
<summary>디렉토리 구조 접기/펼치기</summary>
<div markdown="1">

```shell
.
├── Dockerfile
├── build.gradle
├── docker-compose.yml
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── pull_request_template.md
├── settings.gradle
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── valuewith
    │   │           └── tweaver
    │   │               ├── TweaverApplication.java
    │   │               ├── alert
    │   │               │   ├── controller
    │   │               │   │   └── AlertController.java
    │   │               │   ├── dto
    │   │               │   │   ├── AlertRequestDto.java
    │   │               │   │   └── AlertResponseDto.java
    │   │               │   ├── entity
    │   │               │   │   └── Alert.java
    │   │               │   ├── repository
    │   │               │   │   ├── AlertDsl.java
    │   │               │   │   ├── AlertDslImpl.java
    │   │               │   │   ├── AlertRepository.java
    │   │               │   │   └── EmitterRepository.java
    │   │               │   └── service
    │   │               │       └── AlertService.java
    │   │               ├── auditing
    │   │               │   └── BaseEntity.java
    │   │               ├── auth
    │   │               │   ├── client
    │   │               │   │   ├── MailgunClient.java
    │   │               │   │   └── mailgun
    │   │               │   │       └── SendEmailForm.java
    │   │               │   ├── controller
    │   │               │   │   └── AuthController.java
    │   │               │   ├── dto
    │   │               │   │   ├── AuthDto.java
    │   │               │   │   ├── LoginMemberIdDto.java
    │   │               │   │   └── OAuthAttributes.java
    │   │               │   ├── handler
    │   │               │   │   ├── OAuth2FailureHandler.java
    │   │               │   │   ├── OAuth2SuccessHandler.java
    │   │               │   │   ├── SigninFailureHandler.java
    │   │               │   │   └── SigninSuccessHandler.java
    │   │               │   ├── info
    │   │               │   │   ├── KakaoOAuth2UserInfo.java
    │   │               │   │   └── OAuth2UserInfo.java
    │   │               │   ├── repository
    │   │               │   │   └── HttpCookieOAuth2AuthorizationRequestRepository.java
    │   │               │   └── service
    │   │               │       ├── AuthService.java
    │   │               │       ├── CustomMemberDetailService.java
    │   │               │       ├── EmailService.java
    │   │               │       └── OAuthUserCustomService.java
    │   │               ├── chat
    │   │               │   ├── controller
    │   │               │   │   └── ChatRoomController.java
    │   │               │   ├── dto
    │   │               │   │   └── ChatRoomDto.java
    │   │               │   ├── entity
    │   │               │   │   └── ChatRoom.java
    │   │               │   ├── repository
    │   │               │   │   └── ChatRoomRepository.java
    │   │               │   └── service
    │   │               │       ├── ChatMemberService.java
    │   │               │       └── ChatRoomService.java
    │   │               ├── commons
    │   │               │   ├── PrincipalDetails.java
    │   │               │   ├── redis
    │   │               │   │   └── RedisUtilService.java
    │   │               │   └── security
    │   │               │       ├── CustomJsonAuthenticationFilter.java
    │   │               │       ├── JwtAuthenticationFilter.java
    │   │               │       └── service
    │   │               │           ├── CookieService.java
    │   │               │           ├── PrincipalService.java
    │   │               │           └── TokenService.java
    │   │               ├── component
    │   │               │   └── AlertListener.java
    │   │               ├── config
    │   │               │   ├── AppProperties.java
    │   │               │   ├── AsyncConfig.java
    │   │               │   ├── AuthConfig.java
    │   │               │   ├── FeignConfig.java
    │   │               │   ├── QuerydslConfiguration.java
    │   │               │   ├── RedisConfig.java
    │   │               │   ├── S3Config.java
    │   │               │   ├── SecurityConfig.java
    │   │               │   ├── SwaggerConfig.java
    │   │               │   └── WebSocketConfig.java
    │   │               ├── constants
    │   │               │   ├── AlertContent.java
    │   │               │   ├── ApprovedStatus.java
    │   │               │   ├── ErrorCode.java
    │   │               │   ├── GroupStatus.java
    │   │               │   ├── ImageType.java
    │   │               │   ├── MemberRole.java
    │   │               │   ├── Provider.java
    │   │               │   └── RedirectUrlType.java
    │   │               ├── defaultImage
    │   │               │   ├── controller
    │   │               │   │   └── LocationImageController.java
    │   │               │   ├── dto
    │   │               │   │   └── DefaultImageResponseDto.java
    │   │               │   ├── entity
    │   │               │   │   └── DefaultImage.java
    │   │               │   ├── exception
    │   │               │   │   ├── InvalidFileMediaTypeException.java
    │   │               │   │   ├── LocationNameEmptyException.java
    │   │               │   │   ├── NoFileProvidedException.java
    │   │               │   │   ├── S3ImageNotFoundException.java
    │   │               │   │   └── UrlEmptyException.java
    │   │               │   ├── repository
    │   │               │   │   └── DefaultImageRepository.java
    │   │               │   └── service
    │   │               │       └── ImageService.java
    │   │               ├── exception
    │   │               │   ├── CustomException.java
    │   │               │   ├── GlobalExceptionHandler.java
    │   │               │   ├── SocialLoginFailureException.java
    │   │               │   └── dto
    │   │               │       └── ErrorResponseDto.java
    │   │               ├── group
    │   │               │   ├── controller
    │   │               │   │   ├── TripGroupController.java
    │   │               │   │   └── TripGroupListController.java
    │   │               │   ├── dto
    │   │               │   │   ├── TripGroupDetailResponseDto.java
    │   │               │   │   ├── TripGroupListResponseDto.java
    │   │               │   │   ├── TripGroupRequestDto.java
    │   │               │   │   ├── TripGroupResponseDto.java
    │   │               │   │   ├── TripGroupStatusListDto.java
    │   │               │   │   └── TripGroupStatusResponseDto.java
    │   │               │   ├── entity
    │   │               │   │   └── TripGroup.java
    │   │               │   ├── repository
    │   │               │   │   ├── TripGroupRepository.java
    │   │               │   │   ├── TripGroupRepositoryCustom.java
    │   │               │   │   └── TripGroupRepositoryCustomImpl.java
    │   │               │   └── service
    │   │               │       ├── TripGroupListService.java
    │   │               │       └── TripGroupService.java
    │   │               ├── groupMember
    │   │               │   ├── controller
    │   │               │   │   ├── GroupMemberApplicationController.java
    │   │               │   │   └── GroupMemberListController.java
    │   │               │   ├── dto
    │   │               │   │   ├── GroupMemberDetailResponseDto.java
    │   │               │   │   ├── GroupMemberDto.java
    │   │               │   │   └── GroupMemberListDto.java
    │   │               │   ├── entity
    │   │               │   │   └── GroupMember.java
    │   │               │   ├── repository
    │   │               │   │   ├── GroupMemberRepository.java
    │   │               │   │   ├── GroupMemberRepositoryCustom.java
    │   │               │   │   └── GroupMemberRepositoryCustomImpl.java
    │   │               │   └── service
    │   │               │       ├── GroupMemberApplicationService.java
    │   │               │       ├── GroupMemberListService.java
    │   │               │       └── GroupMemberService.java
    │   │               ├── member
    │   │               │   ├── controller
    │   │               │   │   └── MemberController.java
    │   │               │   ├── dto
    │   │               │   │   ├── MemberDto.java
    │   │               │   │   ├── MemberRequestDto.java
    │   │               │   │   └── MemberResponseDto.java
    │   │               │   ├── entity
    │   │               │   │   └── Member.java
    │   │               │   ├── repository
    │   │               │   │   └── MemberRepository.java
    │   │               │   └── service
    │   │               │       └── MemberService.java
    │   │               ├── message
    │   │               │   ├── controller
    │   │               │   │   └── MessageController.java
    │   │               │   ├── dto
    │   │               │   │   └── MessageDto.java
    │   │               │   ├── entity
    │   │               │   │   └── Message.java
    │   │               │   ├── repository
    │   │               │   │   ├── MessageDsl.java
    │   │               │   │   ├── MessageDslImpl.java
    │   │               │   │   └── MessageRepository.java
    │   │               │   └── service
    │   │               │       └── MessageService.java
    │   │               ├── place
    │   │               │   ├── controller
    │   │               │   │   └── RecommendRouteController.java
    │   │               │   ├── dto
    │   │               │   │   ├── PlaceDetailResponseDto.java
    │   │               │   │   ├── PlaceDto.java
    │   │               │   │   └── RecommendRouteDto.java
    │   │               │   ├── entity
    │   │               │   │   └── Place.java
    │   │               │   ├── repository
    │   │               │   │   ├── PlaceRepository.java
    │   │               │   │   ├── PlaceRepositoryCustom.java
    │   │               │   │   └── PlaceRepositoryCustomImpl.java
    │   │               │   └── service
    │   │               │       ├── PlaceDistanceService.java
    │   │               │       ├── PlaceService.java
    │   │               │       └── RecommendRouteService.java
    │   │               └── scheduler
    │   │                   └── TripGroupCloseScheduler.java
    │   └── resources
    │       ├── application-secret.properties
    │       └── application.yml
    └── test
        └── java
            └── com
                └── valuewith
                    └── tweaver
                        ├── TweaverApplicationTests.java
                        ├── auth
                        │   ├── controller
                        │   │   └── AuthControllerTest.java
                        │   └── service
                        │       ├── AuthServiceTest.java
                        │       └── EmailServiceTest.java
                        ├── chat
                        │   └── controller
                        │       └── ChatRoomControllerTest.java
                        ├── controller
                        ├── group
                        │   └── service
                        │       └── TripGroupServiceTests.java
                        ├── groupMember
                        │   └── service
                        │       └── GroupMemberApplicationServiceTest.java
                        └── image
                            └── service
                                └── ImageServiceTest.java


```

</div>
</details>

## 사용 기술
![spring boot](https://img.shields.io/badge/spring%20boot-6DB33F?style=for-the-badge&logo=spring%20boot&logoColor=white)
![spring security](https://img.shields.io/badge/spring%20security-6DB33F?style=for-the-badge&logo=spring%20security&logoColor=white)
![spring jpa](https://img.shields.io/badge/spring%20jpa-6DB33F?style=for-the-badge&logo=spring%20jpa&logoColor=white)
<br />
![stomp](https://img.shields.io/badge/stomp-000000?style=for-the-badge&&logoColor=white)
![web socket](https://img.shields.io/badge/web%20socket-F56640?style=for-the-badge&&logoColor=white)
![kakao mobility](https://img.shields.io/badge/kakao%20mobility-FFCD00?style=for-the-badge&logo=kakao&logoColor=black)
![kakao login](https://img.shields.io/badge/kakao%20login-FFCD00?style=for-the-badge&logo=kakao&logoColor=black)
<br />
![mysql](https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![query dsl](https://img.shields.io/badge/query%20dsl-007DB8?style=for-the-badge&logoColor=white)
![jwt](https://img.shields.io/badge/jwt-FE2E9A?style=for-the-badge&logoColor=white)
<br />
![redis](https://img.shields.io/badge/redis-DC382D?style=for-the-badge&logo=redis&logoColor=white)
![gradle](https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)
![junit](https://img.shields.io/badge/junit-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![postman](https://img.shields.io/badge/postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)

## ERD
![같이가치_2차_ERD_라이트](https://github.com/ValueWith/ValueWith_BE/assets/51254234/cf4573c2-92ec-4335-839a-52b198a67814)

## WIKI
- [Home](https://github.com/ValueWith/ValueWith_BE/wiki)
- [트위버 구현 기능 (UseCase)](https://github.com/ValueWith/ValueWith_BE/wiki/%08%ED%8A%B8%EC%9C%84%EB%B2%84-%EA%B5%AC%ED%98%84-%EA%B8%B0%EB%8A%A5-(UseCase))
- [트위버 규칙 & 컨벤션](https://github.com/ValueWith/ValueWith_BE/wiki/%ED%8A%B8%EC%9C%84%EB%B2%84-%EA%B7%9C%EC%B9%99-&-%EC%BB%A8%EB%B2%A4%EC%85%98)
- [트위버 백엔드 API 명세서 (Notion 링크)](https://github.com/ValueWith/ValueWith_BE/wiki/%ED%8A%B8%EC%9C%84%EB%B2%84-%EB%B0%B1%EC%97%94%EB%93%9C-API-%EB%AA%85%EC%84%B8%EC%84%9C-(Notion-%EB%A7%81%ED%81%AC))
