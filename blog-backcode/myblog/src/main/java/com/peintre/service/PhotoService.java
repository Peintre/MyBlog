package com.peintre.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.peintre.dto.PhotoAlbumDTO;
import com.peintre.dto.PhotoBackDTO;
import com.peintre.dto.blog.PhotoDTO;
import com.peintre.entity.Photo;
import com.peintre.vo.*;

import java.util.List;

/**
 * @Program: MyBlog
 * @Description: (tb_photo)表服务接口
 * @Author: Peintre
 * @Date: 2022-12-27 18:11:24
 **/
public interface PhotoService extends IService<Photo> {

    /**
     * (后台)条件查询照片
     *
     * @param condition 条件
     * @return {@link <PhotoBackDTO>} 照片列表
     */
    PageResultVo<PhotoBackDTO> listPhotos(ConditionVo condition);

    /**
     * 添加照片到相册
     *
     * @param photoListVo 照片list
     */
    void addPhotosToAlbum(PhotoListVo photoListVo);

    /**
     * 修改照片信息
     *
     * @param photoInfoVo 照片信息
     */
    void updatePhoto(PhotoInfoVo photoInfoVo);

    /**
     * 删除照片
     *
     * @param idsVo 照片id集合
     */
    void deletePhotos(IdsVo idsVo);

    /**
     * 恢复照片
     *
     * @param idsVo 照片id集合
     */
    void recoverPhotos(IdsVo idsVo);

    /**
     * 移动照片
     *
     * @param photoListVo 照片信息
     */
    void updatePhotosAlbum(PhotoListVo photoListVo);

    /**
     * 删除照片(物理)
     *
     * @param idsVo 照片id集合
     */
    void deleteOkPhotos(IdsVo idsVo);

    /**
     * (前台)根据相册id查询照片
     *
     * @param albumId 相册id
     * @return {@link List<PhotoDTO>} 照片列表
     */
    PhotoDTO listPhotosByAlbumId(Integer albumId);
}

