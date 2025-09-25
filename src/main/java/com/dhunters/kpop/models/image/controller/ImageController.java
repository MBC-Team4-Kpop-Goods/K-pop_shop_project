package com.dhunters.kpop.models.image.controller;


import com.dhunters.kpop.common.enums.image.EntityType;
import com.dhunters.kpop.common.enums.image.RelationType;
import com.dhunters.kpop.models.image.dto.ImageReplaceReq;
import com.dhunters.kpop.models.image.dto.ImageResponse;
import com.dhunters.kpop.models.image.dto.ImageUpdateReq;
import com.dhunters.kpop.models.image.dto.ImageUploadReq;
import com.dhunters.kpop.models.image.service.ImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/images")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class ImageController {


    private final ImageService imageService;


    @PostMapping("/upload")
    public ResponseEntity<ImageResponse> uploadImage
            (@RequestParam("file") MultipartFile file,
             @RequestParam("memberId") Long memberId,
             @RequestParam("entityType") EntityType entityType,
             @RequestParam("entityId") Long entityId,
             @RequestParam("relationType")RelationType relationType,
             @RequestParam(value = "altText", required = false) String altText,
             @RequestParam(value = "sortOrder", defaultValue = "0") Integer sortOrder) {

        try {
            ImageUploadReq request = ImageUploadReq.builder()
                    .memberId(memberId)
                    .entityType(entityType)
                    .entityId(entityId)
                    .relationType(relationType)
                    .altText(altText)
                    .sortOrder(sortOrder)
                    .build();

            ImageResponse response = imageService.uploadImage(file, request);
            log.info("이미지 업로드 성공: {} by member {}", response.getImageId(), memberId);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IOException e) {
            log.error("이미지 업로드 중 IO 오류", e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        } catch (IllegalArgumentException e) {
            log.error("잘못된 파일 형식: {}", e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }



    @GetMapping("/entity/{entityType}/{entityId}")
    public ResponseEntity<List<ImageResponse>> getImagesByEntity(
            @PathVariable EntityType entityType,
            @PathVariable Long entityId) {

        List<ImageResponse> images = imageService.getImagesByEntity(entityType, entityId);
        log.debug("엔티티 이미지 조회 결과: {}:{} - {} images", entityType, entityId, images.size());

        return ResponseEntity.ok(images);
    }


    /**
     * 특정 엔티티의 특정 관계 타입 이미지들 조회
     */
    @GetMapping("/entity/{entityType}/{entityId}/relation/{relationType}")
    public ResponseEntity<List<ImageResponse>> getImagesByEntityAndRelationType(
            @PathVariable EntityType entityType,
            @PathVariable Long entityId,
            @PathVariable RelationType relationType) {

        List<ImageResponse> images = imageService.getImagesByEntityAndRelationType(
                entityType, entityId, relationType);

        return ResponseEntity.ok(images);
    }


    /**
     * 특정 엔티티의 메인 이미지 조회
     */
    @GetMapping("/entity/{entityType}/{entityId}/main")
    public ResponseEntity<ImageResponse> getMainImage(
            @PathVariable EntityType entityType,
            @PathVariable Long entityId) {

        ImageResponse image = imageService.getMainImage(entityType, entityId);

        return image != null ? ResponseEntity.ok(image) : ResponseEntity.notFound().build();
    }


    /**
     * 특정 회원의 모든 이미지 조회
     */
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<ImageResponse>> getImagesByMember(@PathVariable Long memberId) {
        List<ImageResponse> images = imageService.getImagesByMember(memberId);

        return ResponseEntity.ok(images);
    }


    /**
     * 특정 엔티티의 이미지 개수 조회
     */
    @GetMapping("/entity/{entityType}/{entityId}/count")
    public ResponseEntity<Long> countImagesByEntity(
            @PathVariable EntityType entityType,
            @PathVariable Long entityId) {

        long count = imageService.countImagesByEntity(entityType, entityId);

        return ResponseEntity.ok(count);
    }


    /**
     * 이미지 정보 업데이트
     */
    @PutMapping("/{imageId}")
    public ResponseEntity<ImageResponse> updateImage(
            @PathVariable Long imageId,
            @Valid @RequestBody ImageUpdateReq request) {

        try {
            ImageResponse response = imageService.updateImage(imageId, request);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("이미지 업데이트 실패: {}", imageId, e);

            return ResponseEntity.notFound().build();
        }
    }


    // 실제 이미지 변경 업데이트
    @PutMapping("/{imageId}/replace")
    public ResponseEntity<ImageResponse> replaceImage(
            @PathVariable Long imageId,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "altText", required = false) String altText,
            @RequestParam(value = "sortOrder", required = false) Integer sortOrder,
            @RequestParam(value = "isActive", required = false) Boolean isActive,
            @RequestParam(value = "entityType", required = false) EntityType entityType,
            @RequestParam(value = "entityId", required = false) Long entityId,
            @RequestParam(value = "relationType", required = false) RelationType relationType) {

        try {
            ImageReplaceReq request = ImageReplaceReq.builder()
                    .altText(altText)
                    .sortOrder(sortOrder)
                    .isActive(isActive)
                    .entityType(entityType)
                    .entityId(entityId)
                    .relationType(relationType)
                    .build();

            ImageResponse response = imageService.replaceImage(imageId, file, request);
            return ResponseEntity.ok(response);

        } catch (IOException e) {
            log.error("이미지 교체 중 IO 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    /**
     * 이미지 삭제 (소프트 삭제)
     */
    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long imageId) {
        try {
            imageService.deleteImage(imageId);

            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            log.error("이미지 삭제 실패: {}", imageId, e);

            return ResponseEntity.notFound().build();
        }
    }


    /**
     * 이미지 영구 삭제
     */
    @DeleteMapping("/{imageId}/permanent")
    public ResponseEntity<Void> permanentDeleteImage(@PathVariable Long imageId) {

        try {
            imageService.permanentDeleteImage(imageId);

            return ResponseEntity.noContent().build();

        } catch (IOException e) {
            log.error("이미지 영구 삭제 중 IO 오류: {}", imageId, e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        } catch (Exception e) {
            log.error("이미지 영구 삭제 실패: {}", imageId, e);

            return ResponseEntity.notFound().build();
        }
    }


    /**
     * 이미지 순서 재정렬
     */
    @PutMapping("/reorder")
    public ResponseEntity<Void> reorderImages(
            @RequestParam EntityType entityType,
            @RequestParam Long entityId,
            @RequestParam RelationType relationType,
            @RequestBody List<Long> imageIds) {

        imageService.reorderImages(entityType, entityId, relationType, imageIds);

        return ResponseEntity.ok().build();
    }


    /**
     * 이미지 존재 여부 확인
     */
    @GetMapping("/{imageId}/exists")
    public ResponseEntity<Boolean> existsImage(@PathVariable Long imageId) {

        boolean exists = imageService.existsById(imageId);

        return ResponseEntity.ok(exists);
    }


    // 저장 경로
    // ex) 바탕화면 > git > KPOP(프로젝트 파일 명) > uploads
    // 저장 경로 변경 위해서는 application.properties 및 FileConfig 수정 필요


    // POSTMAN 조회 방식 (BODY -> form-data)

    // POST 방식

    // key : value

    // file         (타입 file 설정)    : (이미지 파일 등록)
    // memberId  (이하 타입 text 설정)   : memberId (ex. 1,2,3 ...)
    // entityType                      : PRODUCT,BANNER 등 ENUM 확인
    // entityId                        : entityId (ex. 1,2,3 ...)
    // relationType                    : MAIN,BANNER,THUMBNAIL 등 ENUM 확인
    // altText                         : 대체 텍스트
    // sortOrder                       : default 값 0 시작



    // PUT 방식 (메타데이터만 수정할 때)     실제 이미지 변경 : post 방식과 유사하게 put 이용, 기존이미지 삭제 및 새 이미지 등록기능.

    //    http://localhost:81/api/v1/images/1
    //    Content-Type: application/json
    //
    //    {
    //        "altText": "수정된 이미지 설명",
    //            "sortOrder": 1,
    //            "isActive": true
    //    }



    // 다른 서비스에서 이미지 업로드 사용 예시

   /* @Service
    @RequiredArgsConstructor
    public class ProductServiceImpl implements ProductService {

        private final ImageService imageService;

        public void addProductImage(Long productId, MultipartFile file, Long memberId) throws IOException {
            ImageUploadReq request = ImageUploadReq.builder()
                    .memberId(memberId)
                    .entityType(EntityType.PRODUCT)
                    .entityId(productId)
                    .relationType(RelationType.MAIN)
                    .altText("상품 이미지")
                    .sortOrder(0)
                    .build();

            ImageResponse response = imageService.uploadImage(file, request);
            ...
      이하 업로드 response 활용, EntityType과 RelationType에 작성된 ENUM 이외에 이용하는 영역 발생 시 추가해서 사용 가능
      sortOrder 번호를 이용해서 분류하는 방식으로 작성되어 req/res에 모두 포함되어 있으나, 추후 분류관련해서 각자의 도메인의
      기준에 맞추기 위해서 sortOrder 관련 부분은 수정할 필요 있음.
        }
    }*/

}



