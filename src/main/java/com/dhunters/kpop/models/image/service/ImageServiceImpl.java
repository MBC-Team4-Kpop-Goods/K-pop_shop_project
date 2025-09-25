package com.dhunters.kpop.models.image.service;


import com.dhunters.kpop.common.entity.image.Image;
import com.dhunters.kpop.common.entity.image.ImageRelation;
import com.dhunters.kpop.common.enums.image.EntityType;
import com.dhunters.kpop.common.enums.image.RelationType;
import com.dhunters.kpop.core.util.FileUtil;
import com.dhunters.kpop.core.exception.ImageExceptions;
import com.dhunters.kpop.models.image.dto.ImageReplaceReq;
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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ImageRelationRepository imageRelationRepository;

    @Value("${app.upload.dir:./uploads}")
    private String uploadDir;

    @Value("${app.upload.url:/uploads}")
    private String uploadUrl;

    @Override
    public ImageResponse uploadImage(MultipartFile file, ImageUploadReq request) throws IOException {
        log.info("이미지 업로드 시작: {}", file.getOriginalFilename());

        // 파일 유효성 검사
        FileUtil.validateImageFile(file);

        // 저장할 파일명 생성
        String storedFilename = FileUtil.generateStoredFileName(file.getOriginalFilename());

        // 날짜별 디렉토리 생성
        String dateDir = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        Path uploadPath = Paths.get(uploadDir, dateDir).toAbsolutePath().normalize();
        FileUtil.createDirectoryIfNotExists(uploadPath);

        // 파일 저장
        Path filePath = uploadPath.resolve(storedFilename);
        try {
            Files.copy(file.getInputStream(), filePath);
        } catch (IOException e) {
            throw new ImageExceptions.UploadFailed("파일 저장 중 오류가 발생했습니다: " + storedFilename, e);
        }

        // 이미지 차원 정보 가져오기
        FileUtil.ImageDimension dimension = FileUtil.getImageDimensions(file);

        // 이미지 엔티티 생성
        Image image = Image.builder()
                .memberId(request.getMemberId())
                .originalFilename(file.getOriginalFilename())
                .storedFilename(storedFilename)
                .filePath(dateDir + "/" + storedFilename)
                .fileSize(file.getSize())
                .mimeType(file.getContentType())
                .width(dimension.width)
                .height(dimension.height)
                .altText(request.getAltText() != null ? request.getAltText() : file.getOriginalFilename())
                .build();

        image = imageRepository.save(image);

        // 이미지 관계 생성
        ImageRelation relation = ImageRelation.builder()
                .image(image)
                .entityType(request.getEntityType())
                .entityId(request.getEntityId())
                .relationType(request.getRelationType())
                .sortOrder(request.getSortOrder())
                .isActive(true)
                .build();

        imageRelationRepository.save(relation);

        log.info("이미지 업로드 완료: ID={}, 파일명={}", image.getImageId(), storedFilename);

        return convertToResponse(image, relation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ImageResponse> getImagesByEntity(EntityType entityType, Long entityId) {
        List<ImageRelation> relations = imageRelationRepository
                .findByEntityTypeAndEntityIdAndIsActiveTrueOrderBySortOrder(entityType, entityId);

        return relations.stream()
                .map(relation -> convertToResponse(relation.getImage(), relation))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ImageResponse> getImagesByEntityAndRelationType(EntityType entityType, Long entityId, RelationType relationType) {
        List<ImageRelation> relations = imageRelationRepository
                .findByEntityTypeAndEntityIdAndRelationTypeAndIsActiveTrueOrderBySortOrder(
                        entityType, entityId, relationType);

        return relations.stream()
                .map(relation -> convertToResponse(relation.getImage(), relation))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ImageResponse getMainImage(EntityType entityType, Long entityId) {
        return imageRelationRepository
                .findByEntityTypeAndEntityIdAndRelationTypeAndIsActiveTrue(
                        entityType, entityId, RelationType.MAIN)
                .map(relation -> convertToResponse(relation.getImage(), relation))
                .orElse(null);
    }

    @Override
    public ImageResponse updateImage(Long imageId, ImageUpdateReq request) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ImageExceptions.NotFound("이미지를 찾을 수 없습니다: " + imageId));

        // 이미지 기본 정보 업데이트
        if (request.getAltText() != null) {
            image.setAltText(request.getAltText());
        }

        image = imageRepository.save(image);

        // 관련 관계 업데이트
        List<ImageRelation> relations = imageRelationRepository.findByImageIdAndIsActiveTrue(imageId);

        for (ImageRelation relation : relations) {
            if (request.getSortOrder() != null) {
                relation.setSortOrder(request.getSortOrder());
            }
            if (request.getIsActive() != null) {
                relation.setIsActive(request.getIsActive());
            }
        }

        imageRelationRepository.saveAll(relations);

        // 첫 번째 활성 관계를 기준으로 응답 생성
        ImageRelation firstRelation = relations.stream()
                .filter(ImageRelation::getIsActive)
                .findFirst()
                .orElse(relations.get(0));

        return convertToResponse(image, firstRelation);
    }

    @Override
    public ImageResponse replaceImage(Long imageId, MultipartFile file, ImageReplaceReq request) throws IOException {

        log.info("이미지 교체 시작: ID={}", imageId);

        // 1. 기존 이미지 조회
        Image existingImage = imageRepository.findById(imageId)
                .orElseThrow(() -> new ImageExceptions.NotFound("이미지를 찾을 수 없습니다: " + imageId));

        // 2. 파일이 제공된 경우 -> 파일 교체
        if (file != null && !file.isEmpty()) {
            // 파일 유효성 검사
            FileUtil.validateImageFile(file);

            // 기존 파일 백업
            String oldFilePath = existingImage.getFilePath();

            // 새 파일 저장
            String storedFilename = FileUtil.generateStoredFileName(file.getOriginalFilename());
            String dateDir = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            Path uploadPath = Paths.get(uploadDir, dateDir).toAbsolutePath().normalize();
            FileUtil.createDirectoryIfNotExists(uploadPath); // 폴더 없으면 생성

            Path newFilePath = uploadPath.resolve(storedFilename);
            try {
                Files.copy(file.getInputStream(), newFilePath);
            } catch (IOException e) {
                throw new ImageExceptions.UploadFailed("새 파일 저장 중 오류: " + storedFilename, e);
            }

            // 이미지 차원 정보 업데이트
            FileUtil.ImageDimension dimension = FileUtil.getImageDimensions(file);

            // 이미지 엔티티 업데이트
            existingImage.setOriginalFilename(file.getOriginalFilename());
            existingImage.setStoredFilename(storedFilename);
            existingImage.setFilePath(dateDir + "/" + storedFilename);
            existingImage.setFileSize(file.getSize());
            existingImage.setMimeType(file.getContentType());
            existingImage.setWidth(dimension.width);
            existingImage.setHeight(dimension.height);

            // 기존 파일 삭제 (옵션)
            try {
                Path oldFile = Paths.get(uploadDir, oldFilePath).toAbsolutePath().normalize();
                if (Files.exists(oldFile)) {
                    Files.delete(oldFile);
                    log.info("기존 파일 삭제 완료: {}", oldFile);
                }
            } catch (IOException e) {
                log.warn("기존 파일 삭제 실패 (무시됨): {}", oldFilePath, e);
            }
        }

        // 3. 메타데이터 업데이트
        if (request.getAltText() != null) {
            existingImage.setAltText(request.getAltText());
        }

        // 4. 관계 정보 업데이트
        List<ImageRelation> relations = imageRelationRepository.findByImageIdAndIsActiveTrue(imageId);

        for (ImageRelation relation : relations) {
            if (request.getSortOrder() != null) {
                relation.setSortOrder(request.getSortOrder());
            }
            if (request.getIsActive() != null) {
                relation.setIsActive(request.getIsActive());
            }
            if (request.getEntityType() != null) {
                relation.setEntityType(request.getEntityType());
            }
            if (request.getEntityId() != null) {
                relation.setEntityId(request.getEntityId());
            }
            if (request.getRelationType() != null) {
                relation.setRelationType(request.getRelationType());
            }
        }

        // 5. 저장
        existingImage = imageRepository.save(existingImage);
        imageRelationRepository.saveAll(relations);

        log.info("이미지 교체 완료: ID={}", imageId);

        // 6. 응답 생성
        ImageRelation firstRelation = relations.stream()
                .filter(ImageRelation::getIsActive)
                .findFirst()
                .orElse(relations.get(0));

        return convertToResponse(existingImage, firstRelation);
    }


    @Override
    public void deleteImage(Long imageId) {
        List<ImageRelation> relations = imageRelationRepository.findByImageId(imageId);

        if (relations.isEmpty()) {
            throw new ImageExceptions.NotFound("이미지를 찾을 수 없습니다: " + imageId);
        }

        // 소프트 삭제 - Relation 상의 관계만 비활성화
        relations.forEach(relation -> relation.setIsActive(false));
        imageRelationRepository.saveAll(relations);

        log.info("이미지 소프트 삭제 완료: ID={}", imageId);
    }

    @Override
    public void permanentDeleteImage(Long imageId) throws IOException {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ImageExceptions.NotFound("이미지를 찾을 수 없습니다: " + imageId));

        // 파일 시스템에서 파일 삭제
        try {
            Path filePath = Paths.get(uploadDir, image.getFilePath()).toAbsolutePath().normalize();
            if (Files.exists(filePath)) {
                Files.delete(filePath);
                log.info("파일 삭제 완료: {}", filePath);
            }
        } catch (IOException e) {
            log.error("파일 삭제 실패: {}", image.getFilePath(), e);
            throw new ImageExceptions.ProcessingFailed("파일 삭제 중 오류가 발생했습니다", e);
        }

        // DB에서 완전 삭제
        imageRepository.delete(image);

        log.info("이미지 영구 삭제 완료: ID={}", imageId);
    }

    @Override
    public void reorderImages(EntityType entityType, Long entityId, RelationType relationType, List<Long> imageIds) {
        List<ImageRelation> relations = imageRelationRepository
                .findForSortOrderUpdate(entityType, entityId, relationType);

        for (int i = 0; i < imageIds.size(); i++) {
            Long imageId = imageIds.get(i);
            ImageRelation relation = relations.stream()
                    .filter(r -> r.getImage().getImageId().equals(imageId))
                    .findFirst()
                    .orElseThrow(() -> new ImageExceptions.NotFound(
                            "해당 엔티티에 속한 이미지를 찾을 수 없습니다: " + imageId));

            relation.setSortOrder(i);
        }

        imageRelationRepository.saveAll(relations);

        log.info("이미지 순서 재정렬 완료: {}:{}, 개수={}", entityType, entityId, imageIds.size());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ImageResponse> getImagesByMember(Long memberId) {
        List<Image> images = imageRepository.findByMemberId(memberId);

        return images.stream()
                .map(image -> {
                    // 첫 번째 활성 관계를 찾아서 응답 생성
                    ImageRelation firstRelation = image.getImageRelations().stream()
                            .filter(ImageRelation::getIsActive)
                            .findFirst()
                            .orElse(null);

                    return convertToResponse(image, firstRelation);
                })
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

    private ImageResponse convertToResponse(Image image, ImageRelation relation) {
        String fileUrl = buildFileUrl(image.getFilePath());

        return ImageResponse.builder()
                .imageId(image.getImageId())
                .memberId(image.getMemberId())
                .originalFilename(image.getOriginalFilename())
                .storedFilename(image.getStoredFilename())
                .filePath(image.getFilePath())
                .fileUrl(fileUrl)
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

    private String buildFileUrl(String filePath) {
        return uploadUrl + "/" + filePath;
    }
}