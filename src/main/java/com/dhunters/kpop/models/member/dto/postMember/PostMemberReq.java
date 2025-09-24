package com.dhunters.kpop.models.member.dto.postMember;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostMemberReq {

    @Email
    @NotBlank
    private String email;

    /** 평문 비밀번호: Service에서 BCrypt로 해시하여 Member.passwordHash에 저장 */
    @NotBlank @Size(min = 6, max = 100)
    private String password;

    @NotBlank
    private String memberName;

    private String fullName;
    private String phoneNumber;

    /** 권장 포맷: yyyy-MM-dd (엔티티는 Date/LocalDate 중 프로젝트 정책대로 파싱) */
    private String birthDate;

    /** 선택: 마케팅 동의 여부(미전달시 null) */
    private Boolean marketingConsent;

}
