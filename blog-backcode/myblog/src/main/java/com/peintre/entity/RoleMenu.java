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
 * @Description: (tb_role_menu)表实体
 * @Author: Peintre
 * @Date: 2022-12-04 12:37:55
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_role_menu")
public class RoleMenu implements Serializable{

    private static final long serialVersionUID = 1L;
       

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 角色id
     */
    @TableField("role_id")
    private Integer roleId;

    /**
     * 菜单id
     */
    @TableField("menu_id")
    private Integer menuId;

}


