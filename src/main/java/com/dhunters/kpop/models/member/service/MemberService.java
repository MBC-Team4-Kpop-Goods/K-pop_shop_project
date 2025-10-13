package com.dhunters.kpop.models.member.service;

import com.dhunters.kpop.models.member.dto.deleteMember.DeleteMemberReq;
import com.dhunters.kpop.models.member.dto.deleteMember.DeleteMemberRes;
import com.dhunters.kpop.models.member.dto.getMemberDetail.GetMemberDetailReq;
import com.dhunters.kpop.models.member.dto.getMemberDetail.GetMemberDetailRes;
import com.dhunters.kpop.models.member.dto.getMemberList.GetMemberListReq;
import com.dhunters.kpop.models.member.dto.getMemberList.GetMemberListRes;
import com.dhunters.kpop.models.member.dto.modifyMember.ModifyMemberReq;
import com.dhunters.kpop.models.member.dto.modifyMember.ModifyMemberRes;
import com.dhunters.kpop.models.member.dto.postMember.PostMemberReq;
import com.dhunters.kpop.models.member.dto.postMember.PostMemberRes;

public interface MemberService {

    PostMemberRes postMember(PostMemberReq request);

    GetMemberListRes getMemberList(GetMemberListReq request);

    GetMemberDetailRes getMemberDetail(GetMemberDetailReq request);

    ModifyMemberRes modifyMember(ModifyMemberReq request);

    DeleteMemberRes deleteMember(DeleteMemberReq request);

}
