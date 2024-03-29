package com.valuewith.tweaver.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NO_FILE_UPLOAD("추가된 파일이 없습니다.", HttpStatus.BAD_REQUEST),
    INVALID_FILE_MEDIA_TYPE("JPG, JPEG, PNG 형식만 가능합니다.", HttpStatus.UNSUPPORTED_MEDIA_TYPE),
    S3_IMAGE_NOT_FOUND("이미지 저장소에서 파일을 찾을 수 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    URL_IS_EMPTY("제공된 URL이 없습니다.", HttpStatus.BAD_REQUEST),
    LOCATION_NAME_NOT_FOUNT("지역 이름은 필수입니다.", HttpStatus.BAD_REQUEST),
    INVALID_PROFILE_MODIFIED_MEMBER("프로필 수정 권한이 없습니다.", HttpStatus.NON_AUTHORITATIVE_INFORMATION),
    NOT_MATCH_PASSWORD("입력한 비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    // 401
    INVALID_CODE("만료된 코드 입니다.", HttpStatus.UNAUTHORIZED),
    INCORRECT_CODE("인증코드가 다릅니다.", HttpStatus.UNAUTHORIZED),
    INVALID_JWT_M("[Malformed] 잘못된 인증 정보입니다.", HttpStatus.UNAUTHORIZED),
    INVALID_JWT_S("[Signature] 잘못된 접근 입니다.", HttpStatus.UNAUTHORIZED),
    INVALID_JWT_E("[Expired] 만료된 접근 입니다.", HttpStatus.UNAUTHORIZED),
    INVALID_JWT_U("[Unsupported] 잘못된 접근 입니다.", HttpStatus.UNAUTHORIZED),
    INVALID_JWT_I("[Illegal] 잘못된 접근 입니다.", HttpStatus.UNAUTHORIZED),
    INVALID_USER_DETAILS("[200] 잘못된 접근입니다.", HttpStatus.UNAUTHORIZED),
    NOT_A_MEMBER("해당 그룹원이 아닙니다.", HttpStatus.UNAUTHORIZED),
    UNVALIDATED_REDIRECT_URI("인증에 실패하였습니다.\n(Unauthorized uri)", HttpStatus.UNAUTHORIZED),
    POST_WRITER_NOT_MATCH("해당 글에 접근 권한이 없습니다.", HttpStatus.UNAUTHORIZED),
    INVALID_CUSTOM_ID("[001] 잘못된 인증 정보입니다.", HttpStatus.UNAUTHORIZED),
    INVALID_CUSTOM_USERNAME("[002] 잘못된 인증 정보입니다.", HttpStatus.UNAUTHORIZED),
    INVALID_CUSTOM_NAME("[003] 잘못된 인증 정보입니다.", HttpStatus.UNAUTHORIZED),
    NO_PRINCIPAL("[100] 잘못된 인증 정보입니다.", HttpStatus.UNAUTHORIZED),
    // 404
    CHAT_ROOM_NOT_FOUND("채팅방을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    CHAT_ROOM_NOT_FOUND_FOR_DELETE("삭제하려는 채팅방을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    MEMBER_NOT_FOUND("회원을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    GROUP_NOT_FOUND("그룹을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    GROUP_NOT_FOUND_FOR_DELETE("삭제하려는 그룹을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    POST_NOT_FOUND_FOR_UPDATE("수정하려는 글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    POST_NOT_FOUND_FOR_DELETE("삭제하려는 글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    // 409
    DUPLICATE_EMAIL("중복된 이메일입니다.", HttpStatus.CONFLICT),

    // 422
    MEMBER_COUNT_CANNOT_BE_NEGATIVE("그룹 멤버는 0명 이하가 될 수 없습니다", HttpStatus.UNPROCESSABLE_ENTITY),
    MEMBER_COUNT_MAX("그룹이 이미 최대 인원에 도달했습니다. 더 이상 신청할 수 없습니다.", HttpStatus.UNPROCESSABLE_ENTITY),

    // 500
    IMAGE_SAVE_ERROR("이미지 저장 과정에서 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    // 502
    FAILURE_SAVE_IMAGE("이미지를 확인해주세요.", HttpStatus.BAD_GATEWAY),
    FAILURE_DELETE_IMAGE("이미지 삭제에 실패하였습니다.", HttpStatus.BAD_GATEWAY),
    FAILURE_SENDING_EMAIL("이메일 전송에 실패하였습니다.", HttpStatus.BAD_GATEWAY),
    FAILURE_GETTING_PROFILE_IMG("프로필 이미지가 없습니다.", HttpStatus.BAD_GATEWAY)
    ;



    private final String description;
    private final HttpStatus httpStatus;

}
