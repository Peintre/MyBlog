package com.peintre.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.peintre.dto.CommentBackDTO;
import com.peintre.entity.Comment;
import com.peintre.vo.ConditionVo;
import com.peintre.vo.PageResultVo;

/**
 * @Program: MyBlog
 * @Description: (tb_comment)表服务接口
 * @Author: Peintre
 * @Date: 2022-12-21 16:37:52
 **/
public interface CommentService extends IService<Comment> {

    /**
     * (后台)查询评论
     *
     * @param condition 条件
     * @return 评论列表
     */
    PageResultVo<CommentBackDTO> listCommentBackDTO(ConditionVo condition);
}

