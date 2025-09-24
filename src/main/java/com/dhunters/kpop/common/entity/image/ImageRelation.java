package com.dhunters.kpop.common.entity.image;


import com.dhunters.kpop.common.entity.BaseEntity;
import com.dhunters.kpop.common.enums.image.EntityType;
import com.dhunters.kpop.common.enums.image.RelationType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "images_relations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "image") // 순환참조 방지
@EqualsAndHashCode(of = "relationId")
public class ImageRelation extends BaseEntity {

    @Id
    @Column(name = "relation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("relationId")
    private Long relationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", nullable = false)
    private Image image;

    @Column(name = "image_id", insertable = false, updatable = false)
    private Long imageId;

    @Enumerated(EnumType.STRING)
    @Column(name = "entity_type", nullable = false)
    private EntityType entityType;

    @Column(name = "entity_id" , nullable = false)
    private Long entityId;

    @Enumerated(EnumType.STRING)
    @Column(name = "relation_type", nullable = false)
    private RelationType relationType;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder = 0;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;


    public ImageRelation(Image image, EntityType entityType, Long entityId,
                         RelationType relationType, Integer sortOrder) {

        this.image = image;
        this.imageId = image.getImageId();
        this.entityType = entityType;
        this.entityId = entityId;
        this.relationType = relationType;
        this.sortOrder = sortOrder;
        this.isActive = true;

    }

    public void setImage(Image image) {

        this.image = image;
        this.imageId = image != null ? image.getImageId() : null ;

    }

}
