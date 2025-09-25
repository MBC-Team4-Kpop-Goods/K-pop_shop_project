package com.dhunters.kpop.common.entity.product;

import com.dhunters.kpop.common.entity.BaseEntity;
import com.dhunters.kpop.common.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "wish_list")
public class WishList extends BaseEntity {

	@Id
	@SequenceGenerator(
			name = "wish_list_seq",
			sequenceName = "wish_list_seq_tbl",
			allocationSize = 1 // sequence 캐싱 처리, 배포시 50정도 -> 병목시 늘리기
	)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wish_list_seq")
	@Column(name = "wish_list_id")
	private Long wishListId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;
}
