package com.peintre.controller;



import com.peintre.dto.UserOnlineDTO;
import com.peintre.service.UserInfoService;
import com.peintre.utils.Result;
import com.peintre.vo.ConditionVo;
import com.peintre.vo.PageResultVo;
import com.peintre.vo.UserInfoVO;
import com.peintre.vo.UserRoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;


/**
 * @Program: MyBlog
 * @Description: (UserInfo)表控制层
 * @Author: Peintre
 * @Date: 2022-11-10 12:21:18
 **/
@Api(tags = "用户基本信息" +
        "模块")
@RestController
@RequestMapping("/userInfo")
public class UserInfoController{

    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation(value = "更新用户头像")
    @ApiImplicitParam(name = "file", value = "用户头像", required = true, dataType = "MultipartFile")
    @PostMapping("/avatar")
    public Result<String> updateUserAvatar(MultipartFile file) {
        return Result.ok(userInfoService.updateUserAvatar(file));
    }

    @ApiOperation(value = "更新用户信息")
    @PutMapping("/updateUserInfo")
    public Result<?> updateUserInfo(@Valid @RequestBody UserInfoVO userInfoVO) {
        userInfoService.updateUserInfo(userInfoVO);
        return Result.ok();
    }

    //@OptLog(optType = UPDATE)
    @ApiOperation(value = "修改用户角色")
    @PutMapping("/updateUserRole")
    public Result<?> updateUserRole(@Valid @RequestBody UserRoleVo userRoleVo) {
        userInfoService.updateUserRole(userRoleVo);
        return Result.ok();
    }

    @ApiOperation(value = "查看在线用户")
    @GetMapping("/listOnlineUsers")
    public Result<PageResultVo<UserOnlineDTO>> listOnlineUsers(ConditionVo conditionVo) {
        return Result.ok(userInfoService.listOnlineUsers(conditionVo));
    }

}

