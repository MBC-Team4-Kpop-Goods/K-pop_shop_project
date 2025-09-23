package com.dhunters.kpop.common.entity;

import com.dhunters.kpop.common.entity.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = {"parent", "children", "products"})
@Table(name = "category")
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    private Long categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Category> children;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;

    @Column(name="category_name", nullable = false, length = 200)
    private String categoryName;

    @Column(name="category_name_en", length = 200)
    private String categoryNameEn;

    @Column(name="slug", nullable = false, unique = true, length = 200)
    private String slug;

    @Column(name="path", length = 500)
    private String path;

    @Column(name="sort_order", nullable = false)
    private Integer sortOrder = 0;

    @Column(name="is_active", nullable = false)
    private Boolean isActive = true;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="deleted_at")
    private Date deletedAt;
}
