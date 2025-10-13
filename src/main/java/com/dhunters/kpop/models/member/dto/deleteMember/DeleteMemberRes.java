package com.dhunters.kpop.models.member.dto.deleteMember;

import com.dhunters.kpop.models.member.dto.getMemberDetail.GetMemberDetailRes;
import lombok.*;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeleteMemberRes {

    private GetMemberDetailRes getMemberDetailRes;

}
