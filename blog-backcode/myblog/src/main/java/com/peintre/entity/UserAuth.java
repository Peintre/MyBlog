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
 * @Description: (tb_user_auth)表实体
 * @Author: Peintre
 * @Date: 2022-11-09 16:22:34
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_user_auth")
public class UserAuth implements Serializable{

    private static final long serialVersionUID = 1L;
       

    /**
     * $column.comment
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 用户信息id
     */
    @TableField("user_info_id")
    private Integer userInfoId;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 登录类型
     */
    @TableField("login_type")
    private Integer loginType;

    /**
     * 用户登录ip
     */
    @TableField("ip_address")
    private String ipAddress;

    /**
     * ip来源
     */
    @TableField("ip_source")
    private String ipSource;

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

    /**
     * 上次登录时间
     */
    @TableField(value="last_login_time")
    private LocalDateTime lastLoginTime;

}


