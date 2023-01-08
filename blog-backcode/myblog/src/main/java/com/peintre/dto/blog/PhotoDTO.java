package com.peintre.dto.blog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Program: myblog
 * @Description: 照片信息
 * @Author: Peintre
 * @Date: 2023/1/4 13:34
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhotoDTO {
    /**
     * 相册封面
     */
    private String photoAlbumCover;

    /**
     * 相册名
     */
    private String photoAlbumName;

    /**
     * 照片列表
     */
    private List<String> photoList;
}
