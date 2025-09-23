package com.dhunters.kpop.common.entity.cart;

import com.dhunters.kpop.common.entity.BaseEntity;
import com.dhunters.kpop.common.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = {"member", "cartItems"})
@Table(
        name = "cart",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_cart_member", columnNames = {"member_id"}),
                @UniqueConstraint(name = "uq_cart_session", columnNames = {"session_id"})
        },
        indexes = { @Index(name = "ix_cart_expires_at", columnList = "expires_at") }
)
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "fk_cart_member"))
    private Member member;  // 회원 장바구니가 아니면 null

    @Column(name = "session_id", length = 128)
    private String sessionId; // 게스트 장바구니가 아니면 null

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartItem> cartItems;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expires_at")
    private Date expiresAt; // 게스트용 만료시각(회원은 보통 null)

    // created_at, updated_at은 BaseEntity 필드 사용!
}
