package com.dhunters.kpop.models.member.dto.modifyMember;

import com.dhunters.kpop.models.member.dto.getMemberDetail.GetMemberDetailRes;
import lombok.*;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModifyMemberRes {

    private GetMemberDetailRes getMemberDetailRes;

}
