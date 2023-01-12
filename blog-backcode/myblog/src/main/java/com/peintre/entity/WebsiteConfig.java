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
 * @Description: (tb_website_config)表实体
 * @Author: Peintre
 * @Date: 2023-01-12 16:52:36
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_website_config")
public class WebsiteConfig implements Serializable{

    private static final long serialVersionUID = 1L;
       

    /**
     * $column.comment
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 配置信息
     */
    @TableField("config")
    private String config;

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


