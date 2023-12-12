# ValueWith_BE
spring을 활용한 여행 도우미 프로젝트입니다.

## 디렉토리 구조

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
