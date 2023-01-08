package com.peintre.controller;



import com.peintre.dto.FriendLinkBackDTO;
import com.peintre.service.FriendLinkService;
import com.peintre.utils.Result;
import com.peintre.vo.ConditionVo;
import com.peintre.vo.FriendLinkVo;
import com.peintre.vo.IdsVo;
import com.peintre.vo.PageResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * @Program: MyBlog
 * @Description: (FriendLink)表控制层
 * @Author: Peintre
 * @Date: 2022-12-22 11:04:28
 **/
@Api(tags = "友链模块")
@RestController
@RequestMapping("/friendLink")
public class FriendLinkController{

    @Autowired
    private FriendLinkService friendLinkService;

    @ApiOperation(value = "(后台)查看友链")
    @GetMapping("/getLinks")
    public Result<PageResultVo<FriendLinkBackDTO>> listFriendLinkDTO(ConditionVo condition) {
        return Result.ok(friendLinkService.listFriendLinkDTO(condition));
    }

    //@OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "添加友链")
    @PostMapping("/addLink")
    public Result<?> addLink(@Valid @RequestBody FriendLinkVo friendLinkVo) {
        friendLinkService.saveOrUpdateFriendLink(friendLinkVo);
        return Result.ok();
    }

    //@OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "修改友链")
    @PostMapping("/updateLink")
    public Result<?> updateLink(@Valid @RequestBody FriendLinkVo friendLinkVo) {
        friendLinkService.saveOrUpdateFriendLink(friendLinkVo);
        return Result.ok();
    }

    //@OptLog(optType = REMOVE)
    @ApiOperation(value = "删除友链")
    @PostMapping("/deleteLinks")
    public Result<?> deleteFriendLink(@Valid @RequestBody IdsVo idsVo) {
        friendLinkService.deleteFriendLink(idsVo);
        return Result.ok();
    }


}

