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
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{


    @Override
    public PostMemberRes postMember(PostMemberReq request) {

        ///~~~

        return null;
    }

    @Override
    public GetMemberListRes getMemberList(GetMemberListReq request) {
        return null;
    }

    @Override
    public GetMemberDetailRes getMemberDetail(GetMemberDetailReq request) {
        return null;
    }

    @Override
    public ModifyMemberRes modifyMember(ModifyMemberReq request) {
        return null;
    }

    @Override
    public DeleteMemberRes deleteMember(DeleteMemberReq request) {
        return null;
    }
}
