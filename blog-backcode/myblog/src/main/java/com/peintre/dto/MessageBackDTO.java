package com.peintre.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Program: myblog
 * @Description: (后台)留言信息类
 * @Author: Peintre
 * @Date: 2022/12/21 17:26
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageBackDTO {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 用户ip
     */
    private String ipAddress;

    /**
     * 用户ip地址
     */
    private String ipSource;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 留言内容
     */
    private String messageContent;

    /**
     * 是否审核
     */
    private Integer isReview;

    /**
     * 留言时间
     */
    private LocalDateTime createTime;
}
