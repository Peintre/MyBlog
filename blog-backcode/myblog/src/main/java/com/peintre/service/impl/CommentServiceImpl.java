package com.peintre.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.peintre.dao.CommentDao;
import com.peintre.dto.CommentBackDTO;
import com.peintre.entity.Comment;
import com.peintre.service.CommentService;
import com.peintre.utils.PageUtils;
import com.peintre.vo.ConditionVo;
import com.peintre.vo.PageResultVo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Program: MyBlog
 * @Description: (tb_comment)表服务实现类
 * @Author: Peintre
 * @Date: 2022-12-21 16:37:52
 **/
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements CommentService {
   
    @Autowired
    private CommentDao commentDao;

    @Override
    public PageResultVo<CommentBackDTO> listCommentBackDTO(ConditionVo condition) {
        // 统计后台评论量
        Integer count = commentDao.countCommentDTO(condition);
        if (count == 0) {
            return new PageResultVo<>();
        }
        // 查询后台评论集合
        List<CommentBackDTO> commentBackDTOList = commentDao.listCommentBackDTO(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        return new PageResultVo<>(commentBackDTOList, count);
    }
}

