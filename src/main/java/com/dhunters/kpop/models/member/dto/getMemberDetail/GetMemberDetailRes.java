package com.dhunters.kpop.models.member.dto.getMemberDetail;

import lombok.*;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetMemberDetailRes {

    private Long memberId;

    private String email;

    private String memberName;

    private String phoneNumber;

    private String status;

}