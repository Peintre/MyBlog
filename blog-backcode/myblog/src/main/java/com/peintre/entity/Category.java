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
 * @Description: (tb_category)表实体
 * @Author: Peintre
 * @Date: 2022-12-27 12:20:29
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_category")
public class Category implements Serializable{

    private static final long serialVersionUID = 1L;
       

    /**
     * 分类ID
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 分类名
     */
    @TableField("category_name")
    private String categoryName;

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


