package com.dhunters.kpop.common.entity.address;


import com.dhunters.kpop.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "address_international")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressInternational extends BaseEntity {

    /** 해외 주소 고유 식별자 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_international_id")
    private Long addressInternationalId;

    /** 회원 고유 식별자 */
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    /** 배송지명 (예: Home, Office, Friend's House 등) */
    @Column(name = "address_name", nullable = false, length = 50)
    private String addressName;

    /** 수신자 이름 (풀네임 포함) */
    @Column(name = "recipient_name", nullable = false, length = 100)
    private String recipientName;

    /** 국가 코드 (ISO 3166-1, 예: USA, JPN, CHN) */
    @Column(name = "country_code", nullable = false, length = 3)
    private String countryCode;

    /** 국가명 (현지어 또는 영문) */
    @Column(name = "country_name", nullable = false, length = 50)
    private String countryName;

    /** 상세 주소 (도시, 주/도, 거리주소 포함) */
    @Column(name = "address_detail_inter", nullable = false, length = 100)
    private String addressDetailInter;

    /** 우편번호 또는 ZIP 코드 (국가별 형식 상이) */
    @Column(name = "zipcode", length = 20)
    private String zipcode;

    /** 수신자 연락처 (국가코드 포함 권장) */
    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    /** 기본 배송지 여부 */
    @Column(name = "is_default", nullable = false)
    private Boolean isDefault = false;

    /** 국가별 추가 정보 (주/도, 지역코드, 특별 배송 요청사항 등) JSON 형태로 저장 */

    @Column(name = "additional_info", columnDefinition = "json")
    private String additionalInfo;




}
