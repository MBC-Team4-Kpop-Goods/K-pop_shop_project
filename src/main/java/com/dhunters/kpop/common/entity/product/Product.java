package com.dhunters.kpop.common.entity.product;

import com.dhunters.kpop.common.entity.BaseEntity;
import com.dhunters.kpop.common.entity.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "product")
public class Product extends BaseEntity {

    @Id
    @SequenceGenerator(
            name = "product_seq",
            sequenceName = "product_seq_tbl",
            allocationSize = 1 // sequence 캐싱 처리, 배포시 50정도 -> 병목시 늘리기
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;
}
