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
 * @Description: (tb_photo)表实体
 * @Author: Peintre
 * @Date: 2022-12-27 18:11:24
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_photo")
public class Photo implements Serializable{

    private static final long serialVersionUID = 1L;
       

    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 相册id
     */
    @TableField("album_id")
    private Integer albumId;

    /**
     * 照片名
     */
    @TableField("photo_name")
    private String photoName;

    /**
     * 照片描述
     */
    @TableField("photo_desc")
    private String photoDesc;

    /**
     * 照片地址
     */
    @TableField("photo_src")
    private String photoSrc;

    /**
     * 是否删除
     */
    @TableField("is_delete")
    private Integer isDelete;

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


