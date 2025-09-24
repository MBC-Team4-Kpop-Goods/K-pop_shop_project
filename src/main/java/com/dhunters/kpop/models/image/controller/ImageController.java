package com.dhunters.kpop.models.image.controller;


import com.dhunters.kpop.common.enums.image.EntityType;
import com.dhunters.kpop.common.enums.image.RelationType;
import com.dhunters.kpop.models.image.dto.ImageResponse;
import com.dhunters.kpop.models.image.dto.ImageUploadReq;
import com.dhunters.kpop.models.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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


        try{
            ImageUploadReq request = ImageUploadReq.builder()
                    .memberId(memberId)
                    .entityType(entityType)
                    .entityId(entityId)
                    .relationType(relationType)
                    .altText(altText)
                    .sortOrder(sortOrder)
                    .build();

            ImageResponse response = imageService.uploadImage(file, request);

        } catch (IOException e) {

        }




        return null;
    }

}
