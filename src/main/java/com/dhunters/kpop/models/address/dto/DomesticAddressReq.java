package com.dhunters.kpop.models.address.dto;

import com.dhunters.kpop.common.entity.address.AddressDomestic;
import lombok.*;


@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DomesticAddressReq {

    /** 배송지명 */
    private String addressName;

    /** 수신자명 */
    private String recipientName;

    /** 연락처 */
    private String phone;

    /** 우편번호 */
    private String zipcode;

    /** 기본 주소 (도로명 주소) */
    private String address;

    /** 상세 주소 */
    private String addressDetail;

    /** 기본 배송지 여부 */
    private Boolean isDefault = false;

    /** 추가 정보 (JSON 문자열) */
    private String additionalInfo;

    /**
     * DTO를 Entity로 변환
     * @param memberId 회원 ID
     * @return AddressDomestic 엔티티
     */
    public AddressDomestic toEntity(Long memberId) {
        return AddressDomestic.builder()
                .memberId(memberId)
                .addressName(this.addressName)
                .recipientName(this.recipientName)
                .phone(this.phone)
                .zipcode(this.zipcode)
                .address(this.address)
                .addressDetail(this.addressDetail)
                .isDefault(this.isDefault)
                .additionalInfo(this.additionalInfo)
                .build();
    }

}
