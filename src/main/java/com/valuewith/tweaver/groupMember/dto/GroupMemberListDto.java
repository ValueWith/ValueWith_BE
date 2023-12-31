package com.valuewith.tweaver.groupMember.dto;

import com.valuewith.tweaver.groupMember.entity.GroupMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GroupMemberListDto {

    private Long groupMemberId;
    private Long memberId;
    private String groupMemberNickname;
    private Integer groupMemberAge;
    private String groupMemberGender;
    private String groupMemberProfileUrl;
    private String groupMemberStatus;

    public static GroupMemberListDto from(GroupMember groupMember) {
        return GroupMemberListDto.builder()
                .groupMemberId(groupMember.getGroupMemberId())
                .memberId(groupMember.getMember().getMemberId())
                .groupMemberNickname(groupMember.getMember().getNickName())
                .groupMemberAge(groupMember.getMember().getAge())
                .groupMemberGender(groupMember.getMember().getGender())
                .groupMemberProfileUrl(groupMember.getMember().getProfileUrl())
                .groupMemberStatus(groupMember.getApprovedStatus().getDescription())
                .build();
    }
}
