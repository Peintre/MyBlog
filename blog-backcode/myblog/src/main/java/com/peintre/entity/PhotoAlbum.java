package com.peintre.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Program: MyBlog
 * @Description: (tb_photo_album)表实体
 * @Author: Peintre
 * @Date: 2022-12-27 18:20:35
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_photo_album")
public class PhotoAlbum implements Serializable{

    private static final long serialVersionUID = 1L;
       

    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 相册名
     */
    @TableField("album_name")
    private String albumName;

    /**
     * 相册描述
     */
    @TableField("album_desc")
    private String albumDesc;

    /**
     * 相册封面
     */
    @TableField("album_cover")
    private String albumCover;

    /**
     * 是否删除
     */
    @TableField("is_delete")
    private Integer isDelete;

    /**
     * 状态值 1公开 2私密
     */
    @TableField("status")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(value="create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value="update_time",fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}


