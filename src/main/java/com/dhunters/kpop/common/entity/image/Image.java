package com.dhunters.kpop.common.entity.image;


import com.dhunters.kpop.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "imageRelations") // 순환참조 방지
@EqualsAndHashCode(of = "imageId")
public class Image extends BaseEntity {

    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("imageId")
    private Long imageId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "original_filename")
    private String originalFilename;

    @Column(name = "stored_filename")
    private String storedFilename;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;

    @Column(name = "alt_text")
    private String altText;

    // base : C, U Date

    @OneToMany(mappedBy = "image", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ImageRelation> imageRelations = new ArrayList<>();

    @Builder
    public Image(Long memberId, String originalFilename, String storedFilename,
                 String filePath, Long fileSize, String mimeType, Integer width,
                 Integer height, String altText) {

        this.memberId = memberId;
        this.originalFilename = originalFilename;
        this.storedFilename = storedFilename;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.mimeType = mimeType;
        this.width = width;
        this.height = height;
        this.altText = altText;

    }
}
