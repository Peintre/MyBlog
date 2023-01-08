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
 * @Description: (tb_friend_link)表实体
 * @Author: Peintre
 * @Date: 2022-12-27 16:29:21
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_friend_link")
public class FriendLink implements Serializable{

    private static final long serialVersionUID = 1L;
       

    /**
     * $column.comment
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 链接名
     */
    @TableField("link_name")
    private String linkName;

    /**
     * 链接头像
     */
    @TableField("link_avatar")
    private String linkAvatar;

    /**
     * 链接地址
     */
    @TableField("link_address")
    private String linkAddress;

    /**
     * 链接介绍
     */
    @TableField("link_intro")
    private String linkIntro;

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


