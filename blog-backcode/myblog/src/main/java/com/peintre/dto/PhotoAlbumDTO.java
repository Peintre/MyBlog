package com.peintre.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Program: myblog
 * @Description: 相册信息
 * @Author: Peintre
 * @Date: 2022/12/27 22:06
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhotoAlbumDTO {

    /**
     * 相册id
     */
    private Integer id;

    /**
     * 相册封面
     */
    private String photoAlbumCover;

    /**
     * 相册名
     */
    private String photoAlbumName;

    /**
     * 相册描述
     */
    private String photoAlbumDesc;

}
