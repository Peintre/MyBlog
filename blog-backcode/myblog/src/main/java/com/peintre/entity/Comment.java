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
 * @Description: (tb_comment)表实体
 * @Author: Peintre
 * @Date: 2022-12-21 16:37:51
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_comment")
public class Comment implements Serializable{

    private static final long serialVersionUID = 1L;
       

    /**
     * 主键
     */
    @TableId("id")
    private Integer id;

    /**
     * 评论用户Id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 评论主题id
     */
    @TableField("topic_id")
    private Integer topicId;

    /**
     * 评论内容
     */
    @TableField("comment_content")
    private String commentContent;

    /**
     * 回复用户id
     */
    @TableField("reply_user_id")
    private Integer replyUserId;

    /**
     * 父评论id
     */
    @TableField("parent_id")
    private Integer parentId;

    /**
     * 评论类型 1.文章 2.友链 3.说说
     */
    @TableField("type")
    private Integer type;

    /**
     * 是否删除  0否 1是
     */
    @TableField("is_delete")
    private Integer isDelete;

    /**
     * 是否审核
     */
    @TableField("is_review")
    private Integer isReview;

    /**
     * 评论时间
     */
    @TableField(value="create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value="update_time")
    private LocalDateTime updateTime;

}


