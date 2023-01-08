package com.peintre.controller;



import com.peintre.dto.MessageBackDTO;
import com.peintre.service.MessageService;
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
 * @Description: (Message)表控制层
 * @Author: Peintre
 * @Date: 2022-12-21 17:07:22
 **/
@Api(tags = "留言模块")
@RestController
@RequestMapping("/message")
public class MessageController{

    @Autowired
    private MessageService messageService;


    @ApiOperation(value = "(后台)查询留言")
    @GetMapping("/getMessages")
    public Result<PageResultVo<MessageBackDTO>> listMessageBackDTO(ConditionVo condition) {
        return Result.ok(messageService.listMessageBackDTO(condition));
    }


}

