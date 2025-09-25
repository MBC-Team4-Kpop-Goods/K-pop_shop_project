package com.dhunters.kpop.common.entity.cart;

import com.dhunters.kpop.common.entity.BaseEntity;
import com.dhunters.kpop.common.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "cart")
public class Cart extends BaseEntity {

	@Id
	@SequenceGenerator(
			name = "cart_seq",
			sequenceName = "cart_seq_tbl",
			allocationSize = 1 // sequence 캐싱 처리, 배포시 50정도 -> 병목시 늘리기
	)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_seq")
	@Column(name = "cart_id")
	private Long cartId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
}
