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
 * @Description: (tb_user_info)表实体
 * @Author: Peintre
 * @Date: 2023-01-06 18:03:43
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_user_info")
public class UserInfo implements Serializable{

    private static final long serialVersionUID = 1L;
       

    /**
     * 用户ID
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 邮箱号
     */
    @TableField("email")
    private String email;

    /**
     * 用户昵称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 用户头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 用户简介
     */
    @TableField("intro")
    private String intro;

    /**
     * 个人网站
     */
    @TableField("web_site")
    private String webSite;

    /**
     * 是否禁用
     */
    @TableField("is_disable")
    private Integer isDisable;

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


