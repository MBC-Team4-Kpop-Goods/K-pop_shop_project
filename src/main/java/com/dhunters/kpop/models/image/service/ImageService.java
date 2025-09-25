package com.dhunters.kpop.models.image.service;

import com.dhunters.kpop.common.enums.image.EntityType;
import com.dhunters.kpop.common.enums.image.RelationType;
import com.dhunters.kpop.models.image.dto.ImageReplaceReq;
import com.dhunters.kpop.models.image.dto.ImageResponse;
import com.dhunters.kpop.models.image.dto.ImageUpdateReq;
import com.dhunters.kpop.models.image.dto.ImageUploadReq;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {

//     * 이미지 업로드 및 관계 설정
//     * @param file 업로드할 파일
//     * @param request 이미지 업로드 요청 정보
//     * @return 업로드된 이미지 정보
//     * @throws IOException 파일 처리 중 오류 발생

    ImageResponse uploadImage(MultipartFile file, ImageUploadReq request) throws IOException;


//     * 특정 엔티티의 모든 이미지 조회
//     * @param entityType 엔티티 타입
//     * @param entityId 엔티티 ID
//     * @return 이미지 목록


    List<ImageResponse> getImagesByEntity(EntityType entityType, Long entityId);

//     특정 엔티티의 특정 관계 타입 이미지들 조회
//     @param entityType 엔티티 타입
//     @param entityId 엔티티 ID
//     @param relationType 관계 타입
//     @return 이미지 목록


    List<ImageResponse> getImagesByEntityAndRelationType(EntityType entityType, Long entityId, RelationType relationType);

//     * 특정 엔티티의 메인 이미지 조회
//     * @param entityType 엔티티 타입
//     * @param entityId 엔티티 ID
//     * @return 메인 이미지 정보 (없으면 null)

    ImageResponse getMainImage(EntityType entityType, Long entityId);

//     * 이미지 정보 업데이트
//     * @param imageId 이미지 ID
//     * @param request 업데이트 요청 정보
//     * @return 업데이트된 이미지 정보


    ImageResponse updateImage(Long imageId, ImageUpdateReq request);

//     * 이미지 삭제 (소프트 삭제 - 관계만 비활성화)
//     * @param imageId 이미지 ID


    ImageResponse replaceImage(Long imageId, MultipartFile file, ImageReplaceReq request) throws IOException;

//     * 이미지 변경 (실제 이미지 파일 변경)

    void deleteImage(Long imageId);

//     * 이미지 영구 삭제 (파일 시스템에서도 완전 삭제)
//     * @param imageId 이미지 ID
//     * @throws IOException 파일 삭제 중 오류 발생

    void permanentDeleteImage(Long imageId) throws IOException;

//     * 이미지 순서 재정렬
//     * @param entityType 엔티티 타입
//     * @param entityId 엔티티 ID
//     * @param relationType 관계 타입
//     * @param imageIds 새로운 순서의 이미지 ID 목록


    void reorderImages(EntityType entityType, Long entityId, RelationType relationType, List<Long> imageIds);

//     * 특정 회원의 이미지 목록 조회
//     * @param memberId 회원 ID
//     * @return 이미지 목록

    List<ImageResponse> getImagesByMember(Long memberId);

//     * 이미지 존재 여부 확인
//     * @param imageId 이미지 ID
//     * @return 존재 여부


    boolean existsById(Long imageId);

//     * 특정 엔티티의 이미지 개수 조회
//     * @param entityType 엔티티 타입
//     * @param entityId 엔티티 ID
//     * @return 이미지 개수

    long countImagesByEntity(EntityType entityType, Long entityId);




}
