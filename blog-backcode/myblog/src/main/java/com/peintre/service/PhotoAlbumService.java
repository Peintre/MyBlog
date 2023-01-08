package com.peintre.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.peintre.dto.PhotoAlbumBackDTO;
import com.peintre.dto.PhotoAlbumDTO;
import com.peintre.entity.PhotoAlbum;
import com.peintre.vo.ConditionVo;
import com.peintre.vo.PageResultVo;
import com.peintre.vo.PhotoAlbumVo;

import java.util.List;

/**
 * @Program: MyBlog
 * @Description: (tb_photo_album)表服务接口
 * @Author: Peintre
 * @Date: 2022-12-22 11:20:49
 **/
public interface PhotoAlbumService extends IService<PhotoAlbum> {

    /**
     * (后台)条件查询相册
     *
     * @param condition 条件
     * @return {@link PageResultVo<PhotoAlbumBackDTO>} 相册列表
     */
    PageResultVo<PhotoAlbumBackDTO> listPhotoAlbumBacks(ConditionVo condition);

    /**
     * 新增/修改相册
     *
     * @param photoAlbumVo 相册信息
     */
    void saveOrUpdatePhotoAlbum(PhotoAlbumVo photoAlbumVo);

    /**
     * 根据id删除相册
     *
     * @param albumId 相册id
     */
    void deletePhotoAlbumById(Integer albumId);

    /**
     * 根据id查询相册
     *
     * @param albumId 相册id
     */
    PhotoAlbumBackDTO getAlbumById(Integer albumId);

    /**
     * (后台)查询相册
     *
     * @return {@link List <PhotoAlbumDTO>} 相册列表信息
     */
    List <PhotoAlbumDTO> listPhotoAlbumBackInfos();

    /**
     * (前台)查询相册
     *
     * @return {@link List<PhotoAlbumDTO>}相册列表
     */
    List<PhotoAlbumDTO> listPhotoAlbums();
}

