package com.dhunters.kpop.common.entity.product;

import com.dhunters.kpop.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "product_variant")
public class ProductVariant extends BaseEntity {

	@Id
	@SequenceGenerator(
			name = "product_variant_seq",
			sequenceName = "product_variant_seq_tbl",
			allocationSize = 1 // sequence 캐싱 처리, 배포시 50정도 -> 병목시 늘리기
	)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_variant_seq")
	@Column(name = "variant_id")
	private Long variantId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@Column(name = "variant_name")
	private String variantName;

	@Column(name = "price")
	private BigDecimal price;

	@Column(name = "stock_quantity")
	private int stockQuantity;

	@Column(name = "state")
	private String state;
}
