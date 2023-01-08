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
 * @Description: (tb_talk)表实体
 * @Author: Peintre
 * @Date: 2022-12-29 16:12:08
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_talk")
public class Talk implements Serializable{

    private static final long serialVersionUID = 1L;
       

    /**
     * 说说id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 说说内容
     */
    @TableField("content")
    private String content;

    /**
     * 图片
     */
    @TableField("images")
    private String images;

    /**
     * 是否置顶
     */
    @TableField("is_top")
    private Integer isTop;

    /**
     * 状态 1.公开 2.私密
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


