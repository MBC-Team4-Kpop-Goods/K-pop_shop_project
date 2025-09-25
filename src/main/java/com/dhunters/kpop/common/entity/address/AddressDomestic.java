package com.dhunters.kpop.common.entity.address;


import com.dhunters.kpop.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;



@Entity
@Table(name = "address_domestic")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDomestic extends BaseEntity {



    /** 주소 고유 식별자 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    /** 회원 고유 식별자 */
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    /** 배송지명 (예: 집, 회사, 학교 등) */
    @Column(name = "address_name", nullable = false, length = 50)
    private String addressName;

    /** 수신자 이름 */
    @Column(name = "recipient_name", nullable = false, length = 50)
    private String recipientName;

    /** 수신자 연락처 */
    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    /** 우편번호 (5자리) */
    @Column(name = "zipcode", nullable = false, length = 5)
    private String zipcode;

    /** 기본 주소 (도로명 주소) - API 제공 */
    @Column(name = "address", nullable = false, length = 200)
    private String address;

    /** 상세 주소 (동/호수 등 사용자 입력) */
    @Column(name = "address_detail", length = 100)
    private String addressDetail;

    /** 기본 배송지 여부 */
    @Column(name = "is_default", nullable = false)
    private Boolean isDefault = false;

    /** 추가 정보 (건물명, 층수, 특이사항 등) JSON 형태로 저장 */
    @Column(name = "additional_info", columnDefinition = "json")
    private String additionalInfo;




}