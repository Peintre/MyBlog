package com.peintre.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.peintre.dto.CommentBackDTO;
import com.peintre.vo.ConditionVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.peintre.entity.Comment;

import java.util.List;


/**
 * @Program: MyBlog
 * @Description: (tb_comment)表数据访问层
 * @Author: Peintre
 * @Date: 2022-12-21 16:37:52
 **/
@Repository
public interface CommentDao extends BaseMapper<Comment> {

    /**
     * 查询评论数量
     *
     * @param condition 条件
     * @return 评论数量
     */
    Integer countCommentDTO(@Param("condition") ConditionVo condition);

    /**
     * (后台)查询评论
     *
     * @param current   页码
     * @param size      大小
     * @param condition 条件
     * @return 评论集合
     */
    List<CommentBackDTO> listCommentBackDTO(@Param("current") Long current, @Param("size") Long size, @Param("condition") ConditionVo condition);

}

