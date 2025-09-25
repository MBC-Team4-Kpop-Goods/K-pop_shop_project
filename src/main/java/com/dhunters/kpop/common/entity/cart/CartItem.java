package com.dhunters.kpop.common.entity.cart;

import com.dhunters.kpop.common.entity.BaseEntity;
import com.dhunters.kpop.common.entity.product.Product;
import com.dhunters.kpop.common.entity.product.ProductItem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "cart_item")
public class CartItem extends BaseEntity {

	@Id
	@SequenceGenerator(
			name = "cart_item_seq",
			sequenceName = "cart_item_seq_tbl",
			allocationSize = 1 // sequence 캐싱 처리, 배포시 50정도 -> 병목시 늘리기
	)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_item_seq")
	@Column(name = "cart_item_id")
	private Long cartItemId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cart_id")
	private Cart cart;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_item_id")
	private ProductItem productItem;

	@Column(name = "quantity")
	private int quantity;
}
