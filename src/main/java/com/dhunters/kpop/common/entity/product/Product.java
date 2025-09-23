package com.dhunters.kpop.common.entity.product;

import com.dhunters.kpop.common.entity.BaseEntity;
import com.dhunters.kpop.common.entity.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@Table(name = "product")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id")
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name="product_code", nullable = false, length = 50)
    private String productCode;

    @Column(name="product_name", nullable = false, length = 200)
    private String productName;

    @Column(name="product_name_en", length = 200)
    private String productNameEn;

    @Column(name="brand", length = 100)
    private String brand;

    @Column(name="description", columnDefinition = "TEXT")
    private String description;

    @Column(name="description_en", columnDefinition = "TEXT")
    private String descriptionEn;

    @Column(name="product_status", nullable = false, length = 20)
    private String productStatus;

    @Column(name="price", nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    @Column(name="sale_price", precision = 15, scale = 2)
    private BigDecimal salePrice;

    @Column(name="currency", nullable = false, length = 10)
    private String currency = "KRW";

    @Column(name="avg_rating", precision = 3, scale = 2)
    private BigDecimal avgRating = BigDecimal.ZERO;

    @Column(name="review_count")
    private Integer reviewCount = 0;

    @Column(name="sales_count")
    private Integer salesCount = 0;

    @Column(name="view_count")
    private Integer viewCount = 0;

    @Column(name="weight_gram")
    private Integer weightGram;

    @Column(name="is_featured", nullable = false)
    private Boolean isFeatured = false;

    @Column(name="option_type", length = 50)
    private String optionType;

    @Column(name="option_name", length = 100)
    private String optionName;

    @Column(name="option_name_en", length = 100)
    private String optionNameEn;

    // ❌ created_at / updated_at 은 BaseEntity에서 상속받아 사용 (여기서 선언하지 마세요)

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="deleted_at")
    private Date deletedAt;     // 이건 Product에만 필요하면 유지
}
