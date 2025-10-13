package com.dhunters.kpop.models.member.dto.postMember;

import lombok.*;

import java.util.Date;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostMemberReq {

    private String email;

    private String passwordHash;

    private String memberName;

    private String fullName;

    private String phoneNumber;

    private Date birthDate;

}
