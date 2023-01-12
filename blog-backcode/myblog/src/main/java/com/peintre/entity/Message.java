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
 * @Description: (tb_message)表实体
 * @Author: Peintre
 * @Date: 2023-01-12 16:51:47
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_message")
public class Message implements Serializable{

    private static final long serialVersionUID = 1L;
       

    /**
     * 主键id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 昵称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 留言内容
     */
    @TableField("message_content")
    private String messageContent;

    /**
     * 用户ip
     */
    @TableField("ip_address")
    private String ipAddress;

    /**
     * 用户地址
     */
    @TableField("ip_source")
    private String ipSource;

    /**
     * 弹幕速度
     */
    @TableField("time")
    private Integer time;

    /**
     * 是否审核
     */
    @TableField("is_review")
    private Integer isReview;

    /**
     * 发布时间
     */
    @TableField(value="create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(value="update_time",fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}


