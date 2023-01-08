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
 * @Description: (tb_role)表实体
 * @Author: Peintre
 * @Date: 2022-11-10 12:22:50
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_role")
public class Role implements Serializable{

    private static final long serialVersionUID = 1L;
       

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 角色名
     */
    @TableField("role_name")
    private String roleName;

    /**
     * 角色描述
     */
    @TableField("role_label")
    private String roleLabel;

    /**
     * 是否禁用  0否 1是
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


