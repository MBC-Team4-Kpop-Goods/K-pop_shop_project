package com.dhunters.kpop.models.member.dto.modifyMember;

import lombok.*;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModifyMemberReq {

    private String passwordHash;

    private String memberName;

    private String hponeNumber;

}
