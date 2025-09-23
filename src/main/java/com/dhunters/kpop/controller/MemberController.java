package com.dhunters.kpop.controller;

import com.dhunters.kpop.core.api.ApiResult;
import com.dhunters.kpop.dto.member.MemberCreateRequest;
import com.dhunters.kpop.dto.member.MemberResponse;
import com.dhunters.kpop.common.entity.Member;
import com.dhunters.kpop.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<ApiResult<MemberResponse>> createMember(@Valid @RequestBody MemberCreateRequest request) {
        Member member = convertToEntity(request);
        Member savedMember = memberService.createMember(member);
        MemberResponse response = convertToResponse(savedMember);

        return ResponseEntity.ok(ApiResult.success(response));
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResult<MemberResponse>> getMember(@PathVariable Long memberId) {
        Member member = memberService.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        MemberResponse response = convertToResponse(member);
        return ResponseEntity.ok(ApiResult.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResult<Page<MemberResponse>>> getMembers(Pageable pageable) {
        Page<Member> members = memberService.findAllMembers(pageable);
        Page<MemberResponse> response = members.map(this::convertToResponse);

        return ResponseEntity.ok(ApiResult.success(response));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResult<MemberResponse>> getMemberByEmail(@PathVariable String email) {
        Member member = memberService.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        MemberResponse response = convertToResponse(member);
        return ResponseEntity.ok(ApiResult.success(response));
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<ApiResult<MemberResponse>> updateMember(
            @PathVariable Long memberId,
            @RequestBody MemberCreateRequest request) {

        Member updateInfo = convertToEntity(request);
        Member updatedMember = memberService.updateMember(memberId, updateInfo);
        MemberResponse response = convertToResponse(updatedMember);

        return ResponseEntity.ok(ApiResult.success(response));
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<ApiResult<Void>> deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.ok(ApiResult.success(null));
    }

    @GetMapping("/check/email/{email}")
    public ResponseEntity<ApiResult<Boolean>> checkEmailExists(@PathVariable String email) {
        boolean exists = memberService.existsByEmail(email);
        return ResponseEntity.ok(ApiResult.success(exists));
    }

    @GetMapping("/check/username/{memberName}")
    public ResponseEntity<ApiResult<Boolean>> checkUsernameExists(@PathVariable String memberName) {
        boolean exists = memberService.existsByMemberName(memberName);
        return ResponseEntity.ok(ApiResult.success(exists));
    }

    private Member convertToEntity(MemberCreateRequest request) {
        Member member = new Member();
        member.setEmail(request.getEmail());
        member.setPasswordHash(request.getPassword()); // 실제로는 암호화 필요
        member.setMemberName(request.getMemberName());
        member.setFullName(request.getFullName());
        member.setPhoneNumber(request.getPhoneNumber());
        member.setBirthDate(request.getBirthDate());
        member.setGrade(request.getGrade());
        member.setRole(request.getRole());
        member.setMarketingConsent(request.getMarketingConsent());
        return member;
    }

    private MemberResponse convertToResponse(Member member) {
        MemberResponse response = new MemberResponse();
        response.setMemberId(member.getMemberId());
        response.setEmail(member.getEmail());
        response.setMemberName(member.getMemberName());
        response.setFullName(member.getFullName());
        response.setPhoneNumber(member.getPhoneNumber());
        response.setBirthDate(member.getBirthDate());
        response.setGrade(member.getGrade());
        response.setRole(member.getRole());
        response.setTotalOrderAmount(member.getTotalOrderAmount());
        response.setMarketingConsent(member.getMarketingConsent());
        response.setCreatedAt(member.getCreatedAt());
        response.setUpdatedAt(member.getUpdatedAt());
        return response;
    }
}

