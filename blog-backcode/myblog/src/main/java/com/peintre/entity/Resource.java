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
 * @Description: (tb_resource)表实体
 * @Author: Peintre
 * @Date: 2022-12-03 16:02:51
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_resource")
public class Resource implements Serializable{

    private static final long serialVersionUID = 1L;
       

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 资源名
     */
    @TableField("resource_name")
    private String resourceName;

    /**
     * 权限路径
     */
    @TableField("url")
    private String url;

    /**
     * 请求方式
     */
    @TableField("request_method")
    private String requestMethod;

    /**
     * 父权限id
     */
    @TableField("parent_id")
    private Integer parentId;

    /**
     * 是否匿名访问 0否 1是
     */
    @TableField("is_anonymous")
    private Integer isAnonymous;

    /**
     * 创建时间
     */
    @TableField(value="create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(value="update_time",fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}


