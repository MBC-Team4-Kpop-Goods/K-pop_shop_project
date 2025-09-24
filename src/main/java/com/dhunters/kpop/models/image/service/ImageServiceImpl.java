package com.dhunters.kpop.models.image.service;


import com.dhunters.kpop.common.entity.image.Image;
import com.dhunters.kpop.common.entity.image.ImageRelation;
import com.dhunters.kpop.common.enums.image.EntityType;
import com.dhunters.kpop.common.enums.image.RelationType;
import com.dhunters.kpop.models.image.dto.ImageResponse;
import com.dhunters.kpop.models.image.dto.ImageUpdateReq;
import com.dhunters.kpop.models.image.dto.ImageUploadReq;
import com.dhunters.kpop.models.image.repository.ImageRelationRepository;
import com.dhunters.kpop.models.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 파일 시스템 기반 이미지 서비스 구현체
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FileImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ImageRelationRepository imageRelationRepository;

    @Value("${image.upload.path:/uploads/images}")
    private String uploadPath;

    @Value("${image.base.url:http://localhost:8080}")
    private String baseUrl;

    @Value("${image.max.file.size:10485760}") // 10MB
    private long maxFileSize;

    @Value("${image.allowed.extensions:jpg,jpeg,png,gif,webp}")
    private String[] allowedExtensions;

    @Override
    public ImageResponse uploadImage(MultipartFile file, ImageUploadReq request) throws IOException {
        log.info("이미지 업로드 시작: {} for entity {}:{}",
                file.getOriginalFilename(), request.getEntityType(), request.getEntityId());

        // 파일 검증
        validateFile(file);

        // 파일 저장
        String storedFilename = generateStoredFilename(file.getOriginalFilename());
        String filePath = saveFile(file, storedFilename);

        // 이미지 정보 추출
        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        Integer width = bufferedImage != null ? bufferedImage.getWidth() : null;
        Integer height = bufferedImage != null ? bufferedImage.getHeight() : null;

        // Image 엔티티 생성 및 저장
        Image image = Image.builder()
                .memberId(request.getMemberId())
                .originalFilename(file.getOriginalFilename())
                .storedFilename(storedFilename)
                .filePath(filePath)
                .fileSize(file.getSize())
                .mimeType(file.getContentType())
                .width(width)
                .height(height)
                .altText(request.getAltText() != null ? request.getAltText() : "")
                .build();

        Image savedImage = imageRepository.save(image);

        // ImageRelation 생성 및 저장
        ImageRelation relation = ImageRelation.builder()
                .image(savedImage)
                .entityType(request.getEntityType())
                .entityId(request.getEntityId())
                .relationType(request.getRelationType())
                .sortOrder(request.getSortOrder())
                .build();

        imageRelationRepository.save(relation);

        log.info("이미지 업로드 완료: {} (size: {} bytes)", savedImage.getImageId(), file.getSize());
        return convertToImageResponse(savedImage, relation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ImageResponse> getImagesByEntity(EntityType entityType, Long entityId) {
        log.debug("엔티티 이미지 조회: {}:{}", entityType, entityId);

        List<ImageRelation> relations = imageRelationRepository
                .findByEntityTypeAndEntityIdAndIsActiveTrueOrderBySortOrder(entityType, entityId);

        return relations.stream()
                .map(relation -> convertToImageResponse(relation.getImage(), relation))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ImageResponse> getImagesByEntityAndRelationType(EntityType entityType,
                                                                Long entityId,
                                                                RelationType relationType) {
        log.debug("엔티티 관계타입별 이미지 조회: {}:{} - {}", entityType, entityId, relationType);

        List<ImageRelation> relations = imageRelationRepository
                .findByEntityTypeAndEntityIdAndRelationTypeAndIsActiveTrueOrderBySortOrder(
                        entityType, entityId, relationType);

        return relations.stream()
                .map(relation -> convertToImageResponse(relation.getImage(), relation))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ImageResponse getMainImage(EntityType entityType, Long entityId) {
        log.debug("메인 이미지 조회: {}:{}", entityType, entityId);

        ImageRelation relation = imageRelationRepository
                .findByEntityTypeAndEntityIdAndRelationTypeAndIsActiveTrue(
                        entityType, entityId, RelationType.MAIN)
                .orElse(null);

        return relation != null ? convertToImageResponse(relation.getImage(), relation) : null;
    }

    @Override
    public ImageResponse updateImage(Long imageId, ImageUpdateReq request) {
        log.info("이미지 정보 업데이트: {}", imageId);

        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ImageNotFoundException("이미지를 찾을 수 없습니다: " + imageId));

        // 이미지 정보 업데이트
        if (request.getAltText() != null) {
            image.setAltText(request.getAltText());
        }

        Image updatedImage = imageRepository.save(image);

        // 관계 정보 업데이트
        List<ImageRelation> relations = imageRelationRepository.findByImageIdAndIsActiveTrue(imageId);
        if (!relations.isEmpty()) {
            ImageRelation relation = relations.get(0); // 첫 번째 활성 관계

            if (request.getSortOrder() != null) {
                relation.setSortOrder(request.getSortOrder());
            }
            if (request.getIsActive() != null) {
                relation.setIsActive(request.getIsActive());
            }

            imageRelationRepository.save(relation);
            return convertToImageResponse(updatedImage, relation);
        }

        return convertToImageResponse(updatedImage, null);
    }

    @Override
    public void deleteImage(Long imageId) {
        log.info("이미지 소프트 삭제: {}", imageId);

        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ImageNotFoundException("이미지를 찾을 수 없습니다: " + imageId));

        // 관련된 모든 관계를 비활성화
        List<ImageRelation> relations = imageRelationRepository.findByImageId(imageId);
        relations.forEach(relation -> relation.setIsActive(false));
        imageRelationRepository.saveAll(relations);

        log.info("이미지 소프트 삭제 완료: {}", imageId);
    }

    @Override
    public void permanentDeleteImage(Long imageId) throws IOException {
        log.info("이미지 영구 삭제: {}", imageId);

        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ImageNotFoundException("이미지를 찾을 수 없습니다: " + imageId));

        // 관련 관계들 삭제
        List<ImageRelation> relations = imageRelationRepository.findByImageId(imageId);
        imageRelationRepository.deleteAll(relations);

        // 파일 시스템에서 파일 삭제
        deleteFileFromSystem(image.getFilePath());

        // 데이터베이스에서 이미지 삭제
        imageRepository.delete(image);

        log.info("이미지 영구 삭제 완료: {}", imageId);
    }

    @Override
    public void reorderImages(EntityType entityType, Long entityId,
                              RelationType relationType, List<Long> imageIds) {
        log.info("이미지 순서 재정렬: {} {} {} - {} images", entityType, entityId, relationType, imageIds.size());

        List<ImageRelation> relations = imageRelationRepository
                .findForSortOrderUpdate(entityType, entityId, relationType);

        for (int i = 0; i < imageIds.size(); i++) {
            Long imageId = imageIds.get(i);
            final int sortOrder = i;
            relations.stream()
                    .filter(relation -> relation.getImageId().equals(imageId))
                    .findFirst()
                    .ifPresent(relation -> relation.setSortOrder(sortOrder));
        }

        imageRelationRepository.saveAll(relations);
        log.info("이미지 순서 재정렬 완료");
    }

    @Override
    @Transactional(readOnly = true)
    public List<ImageResponse> getImagesByMember(Long memberId) {
        log.debug("회원 이미지 조회: {}", memberId);

        List<Image> images = imageRepository.findByMemberId(memberId);
        return images.stream()
                .map(image -> convertToImageResponse(image, null))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long imageId) {
        return imageRepository.existsById(imageId);
    }

    @Override
    @Transactional(readOnly = true)
    public long countImagesByEntity(EntityType entityType, Long entityId) {
        return imageRelationRepository.countByEntityTypeAndEntityIdAndIsActiveTrue(entityType, entityId);
    }

    // Private helper methods

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어있습니다.");
        }

        if (file.getSize() > maxFileSize) {
            throw new IllegalArgumentException("파일 크기가 너무 큽니다. 최대 크기: " + maxFileSize + " bytes");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("이미지 파일만 업로드 가능합니다.");
        }

        // 파일 확장자 검증
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && originalFilename.contains(".")) {
            String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
            boolean isAllowed = false;
            for (String allowedExt : allowedExtensions) {
                if (allowedExt.equalsIgnoreCase(extension)) {
                    isAllowed = true;
                    break;
                }
            }
            if (!isAllowed) {
                throw new IllegalArgumentException("지원하지 않는 파일 형식입니다: " + extension);
            }
        }
    }

    private String generateStoredFilename(String originalFilename) {
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String uuid = UUID.randomUUID().toString().substring(0, 8);

        return timestamp + "_" + uuid + extension;
    }

    private String saveFile(MultipartFile file, String storedFilename) throws IOException {
        // 연도/월 폴더 구조 생성
        String yearMonth = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM"));
        Path uploadDir = Paths.get(uploadPath, yearMonth);

        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        Path filePath = uploadDir.resolve(storedFilename);
        Files.copy(file.getInputStream(), filePath);

        return filePath.toString();
    }

    private void deleteFileFromSystem(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            Files.delete(path);
            log.info("파일 시스템에서 파일 삭제 완료: {}", filePath);
        }
    }

    private String generateFileUrl(String filePath) {
        // 파일 경로를 URL로 변환
        String relativePath = filePath.replace(uploadPath, "").replace("\\", "/");
        if (!relativePath.startsWith("/")) {
            relativePath = "/" + relativePath;
        }
        return baseUrl + "/images" + relativePath;
    }

    private ImageResponse convertToImageResponse(Image image, ImageRelation relation) {
        return ImageResponse.builder()
                .imageId(image.getImageId())
                .memberId(image.getMemberId())
                .originalFilename(image.getOriginalFilename())
                .storedFilename(image.getStoredFilename())
                .filePath(image.getFilePath())
                .fileUrl(generateFileUrl(image.getFilePath()))
                .fileSize(image.getFileSize())
                .mimeType(image.getMimeType())
                .width(image.getWidth())
                .height(image.getHeight())
                .altText(image.getAltText())
                .createdAt(image.getCreatedAt())
                .modifiedAt(image.getModifiedAt())
                .entityType(relation != null ? relation.getEntityType() : null)
                .entityId(relation != null ? relation.getEntityId() : null)
                .relationType(relation != null ? relation.getRelationType() : null)
                .sortOrder(relation != null ? relation.getSortOrder() : null)
                .isActive(relation != null ? relation.getIsActive() : null)
                .build();
    }
}
