package com.dhunters.kpop.common.entity;


import com.dhunters.kpop.common.enums.Grade;
import com.dhunters.kpop.common.enums.Role;
import com.dhunters.kpop.common.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("memberId")
    private Long memberId;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "member_name", length = 50)
    private String memberName;

    @Column(name = "full_name", length = 100)
    private String fullName;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "birth_date")
    private Date birthDate;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "total_order_amount")
    private Long totalOrderAmount;

    @Column(name = "marketing_consent")
    private Boolean marketingConsent;

    
}
