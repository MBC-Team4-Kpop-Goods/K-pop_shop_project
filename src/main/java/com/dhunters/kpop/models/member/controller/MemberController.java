package com.dhunters.kpop.models.member.controller;

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
import com.dhunters.kpop.models.member.repository.MemberRepository;
import com.dhunters.kpop.models.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private MemberRepository memberRepository;

    // C
    @PostMapping("/members")
    public ResponseEntity<PostMemberRes> create(@RequestBody PostMemberReq postMemberReq) {
        return ResponseEntity.ok(new PostMemberRes());
    }

    // R - List
    @GetMapping("/members")
    public GetMemberListRes list(GetMemberListReq getMemberListReq) {
        return new GetMemberListRes();
    }

    // R - Detail
    @GetMapping("/members/{id}")
    public GetMemberDetailRes detail(@PathVariable Long id, GetMemberDetailReq getMemberDetailReq) {
        return new GetMemberDetailRes();
    }

    // U
    @PutMapping("/members/{id}")
    public ModifyMemberRes update(@PathVariable Long id, @RequestBody ModifyMemberReq modifyMemberReq) {
        return new ModifyMemberRes();
    }

    // D
    @DeleteMapping("/members/{id}")
    public DeleteMemberRes delete(@PathVariable Long id, @RequestBody DeleteMemberReq deleteMemberReq) {
        return new DeleteMemberRes();
    }
}
