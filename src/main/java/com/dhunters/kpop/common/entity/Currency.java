package com.dhunters.kpop.common.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "currency_rate")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Currency extends BaseEntity {

    @Id//PK, 1,2,3,자동증가
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 통화 코드 (USD, CNY, JPY 로 나오게 ) [cur_unit]
    @Column(name = "currency_code", nullable = false, length = 10)
    private String currencyCode;

    // 1외화= 원으로 환율 저장 precision = 전체 자릿수 , scale = 소숫점 [deal_bas_r]
    @Column(name = "rate_to_krw", nullable = false, precision = 10, scale = 4)
    private BigDecimal rateToKrw;

    // 통화 단위 (USD=1, CNH=1, JPY=100)
    @Column(name = "currency_unit", nullable = false)
    private Integer currencyUnit;

    // 통화명 (미국 달러, 위안화, 일본 엔)  [cur_nm]
    @Column(name = "currency_name", length = 50)
    private String currencyName;

    // API에서 제공하는 환율 기준 날짜 20250909
    @Column(name = "api_date", nullable = false, length = 8)
    private String apiDate;




}