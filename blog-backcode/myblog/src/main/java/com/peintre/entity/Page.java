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
 * @Description: (tb_page)表实体
 * @Author: Peintre
 * @Date: 2023-01-12 16:51:57
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_page")
public class Page implements Serializable{

    private static final long serialVersionUID = 1L;
       

    /**
     * 页面id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 页面名
     */
    @TableField("page_name")
    private String pageName;

    /**
     * 页面标签
     */
    @TableField("page_label")
    private String pageLabel;

    /**
     * 页面封面
     */
    @TableField("page_cover")
    private String pageCover;

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


