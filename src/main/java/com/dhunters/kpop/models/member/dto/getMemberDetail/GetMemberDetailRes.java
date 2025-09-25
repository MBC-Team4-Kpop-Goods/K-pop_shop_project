package com.dhunters.kpop.models.member.dto.getMemberDetail;

import com.dhunters.kpop.common.enums.Grade;
import com.dhunters.kpop.common.enums.Role;
import com.dhunters.kpop.common.enums.Status;
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

    private String fullName;

    private String phoneNumber;

    private String birthDate;         // yyyy-MM-dd 문자열

    private Grade grade;

    private Role role;

    private Long totalOrderAmount;

    private Boolean marketingConsent;

    private Status status;

}