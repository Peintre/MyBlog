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
 * @Description: (tb_menu)表实体
 * @Author: Peintre
 * @Date: 2022-11-10 13:16:16
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_menu")
public class Menu implements Serializable{

    private static final long serialVersionUID = 1L;
       

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 菜单名
     */
    @TableField("name")
    private String name;

    /**
     * 菜单路径
     */
    @TableField("path")
    private String path;

    /**
     * 组件
     */
    @TableField("component")
    private String component;

    /**
     * 菜单icon
     */
    @TableField("icon")
    private String icon;

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
     * 排序
     */
    @TableField("order_num")
    private Integer orderNum;

    /**
     * 父id
     */
    @TableField("parent_id")
    private Integer parentId;

    /**
     * 是否隐藏  0否1是
     */
    @TableField("is_hidden")
    private Integer isHidden;

}


