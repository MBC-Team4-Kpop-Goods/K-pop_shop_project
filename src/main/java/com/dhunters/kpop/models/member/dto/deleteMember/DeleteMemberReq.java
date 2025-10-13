package com.dhunters.kpop.models.member.dto.deleteMember;

import lombok.*;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeleteMemberReq {

    private String email;

    private String passwordHash;

}
