package com.peintre.controller;



import com.peintre.dto.CommentBackDTO;
import com.peintre.service.CommentService;
import com.peintre.utils.Result;
import com.peintre.vo.ConditionVo;
import com.peintre.vo.PageResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Program: MyBlog
 * @Description: (Comment)表控制层
 * @Author: Peintre
 * @Date: 2022-12-21 16:37:52
 **/
@Api(tags = "评论模块")
@RestController
@RequestMapping("/comment")
public class CommentController{

    @Autowired
    private CommentService commentService;


    @ApiOperation(value = "后台查询评论")
    @GetMapping("/getComments")
    public Result<PageResultVo<CommentBackDTO>> listCommentBackDTO(ConditionVo condition) {
        return Result.ok(commentService.listCommentBackDTO(condition));
    }


}

