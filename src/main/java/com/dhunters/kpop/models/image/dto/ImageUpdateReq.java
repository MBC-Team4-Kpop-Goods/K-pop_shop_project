package com.dhunters.kpop.models.image.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageUpdateReq {

    private String altText;

    private Integer sortOrder;

    private Boolean isActive;

}
