package com.dhunters.kpop.common.entity.product;

import com.dhunters.kpop.common.entity.BaseEntity;
import com.dhunters.kpop.common.entity.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "product_item")
public class ProductItem extends BaseEntity {

	@Id
	@SequenceGenerator(
			name = "product_item_seq",
			sequenceName = "product_item_seq_tbl",
			allocationSize = 1 // sequence 캐싱 처리, 배포시 50정도 -> 병목시 늘리기
	)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_item_seq")
	@Column(name = "product_item_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@Column(name = "product_item_name")
	private String productItemName;

	@Column(name = "product_item_code")
	private String productItemCode;

	@Column(name = "description")
	private String description;

	@Column(name = "status")
	private String status;

	@Column(name = "price")
	private BigDecimal price;

	@Column(name = "currency")
	private String currency;
}
