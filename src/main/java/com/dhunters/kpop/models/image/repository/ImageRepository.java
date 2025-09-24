package com.dhunters.kpop.models.image.repository;

import com.dhunters.kpop.common.entity.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByMemberId(Long memberId);

    Optional<Image> findByStoredFilename(String storedFilename);

    Optional<Image> findByFilePath(String filePath);

    List<Image> findByMimeTypeStartingWith(String mimeTypePrefix);

    @Query("SELECT i FROM Image i WHERE i.createdAt >= :startDate AND i.createdAt <= :endDate")
    List<Image> findByCreatedAtBetween(@Param("startDate") java.time.LocalDate startDate,
                                       @Param("endDate") java.time.LocalDate endDate);

    @Query("SELECT DISTINCT i FROM Image i JOIN i.imageRelations ir WHERE ir.isActive = true")
    List<Image> findImagesWithActiveRelations();

    List<Image> findByFileSizeBetween(Long minSize, Long maxSize);


}
