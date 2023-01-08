package com.peintre.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.peintre.dto.PhotoAlbumBackDTO;
import com.peintre.vo.ConditionVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.peintre.entity.PhotoAlbum;

import java.util.List;


/**
 * @Program: MyBlog
 * @Description: (tb_photo_album)表数据访问层
 * @Author: Peintre
 * @Date: 2022-12-22 11:20:49
 **/
@Repository
public interface PhotoAlbumDao extends BaseMapper<PhotoAlbum> {

    /**
     * (后台)查看相册信息
     *
     * @param current   页码
     * @param size      大小
     * @param condition 条件
     * @return {@link List < PhotoAlbumBackDTO >} 相册列表
     */
    List<PhotoAlbumBackDTO> listPhotoAlbumBacks(@Param("current") Long current, @Param("size") Long size, @Param("condition") ConditionVo condition);

}

