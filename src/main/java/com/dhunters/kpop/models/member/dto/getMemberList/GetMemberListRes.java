package com.dhunters.kpop.models.member.dto.getMemberList;

import lombok.*;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetMemberListRes {

    private Long memberId;
    private String email;
    private String memberName;
    private String phoneNumber;
    private String status;

}
