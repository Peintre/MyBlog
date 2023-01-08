package com.peintre.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @Date: 2022-12-22 10:44:25
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
    @TableId("id")
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
    @TableField(value="create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value="update_time")
    private LocalDateTime updateTime;

}


