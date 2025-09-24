package com.dhunters.kpop.models.image.repository;

import com.dhunters.kpop.common.entity.image.ImageRelation;
import com.dhunters.kpop.common.enums.image.EntityType;
import com.dhunters.kpop.common.enums.image.RelationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ImageRelationRepository extends JpaRepository<ImageRelation, Long> {

    List<ImageRelation> findByEntityTypeAndEntityIdAndIsActiveTrueOrderBySortOrder(
            EntityType entityType, Long entityId);

    List<ImageRelation> findByEntityTypeAndEntityIdAndRelationTypeAndIsActiveTrueOrderBySortOrder(
            EntityType entityType, Long entityId, RelationType relationType);

    List<ImageRelation> findByImageId(Long imageId);

    List<ImageRelation> findByImageIdAndIsActiveTrue(Long imageId);

    Optional<ImageRelation> findByEntityTypeAndEntityIdAndRelationTypeAndIsActiveTrue(
            EntityType entityType, Long entityId, RelationType relationType);

    List<ImageRelation> findByEntityTypeAndIsActiveTrue(EntityType entityType);

    List<ImageRelation> findByRelationTypeAndIsActiveTrue(RelationType relationType);

    boolean existsByImageIdAndIsActiveTrue(Long imageId);


    @Query("SELECT COUNT(ir) FROM ImageRelation ir WHERE ir.entityType = :entityType " +
            "AND ir.entityId = :entityId AND ir.isActive = true")
    Long countByEntityTypeAndEntityIdAndIsActiveTrue(@Param("entityType") EntityType entityType,
                                                     @Param("entityId") Long entityId);


    @Query("SELECT ir FROM ImageRelation ir WHERE ir.entityType = :entityType " +
            "AND ir.entityId = :entityId AND ir.relationType = :relationType " +
            "AND ir.isActive = true ORDER BY ir.sortOrder")
    List<ImageRelation> findForSortOrderUpdate(@Param("entityType") EntityType entityType,
                                               @Param("entityId") Long entityId,
                                               @Param("relationType") RelationType relationType);



}
