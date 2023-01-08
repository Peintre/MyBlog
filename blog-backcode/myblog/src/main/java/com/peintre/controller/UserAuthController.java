package com.peintre.controller;



import com.peintre.dto.UserBackDTO;
import com.peintre.service.UserAuthService;
import com.peintre.utils.Result;
import com.peintre.vo.ConditionVo;
import com.peintre.vo.PageResultVo;
import com.peintre.vo.PasswordVO;
import com.peintre.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * @Program: MyBlog
 * @Description: (UserAuth)表控制层
 * @Author: Peintre
 * @Date: 2022-11-09 16:22:36
 **/
@Api(tags = "用户账号信息模块")
@RestController
@RequestMapping("/userAuth")
public class UserAuthController{

    @Autowired
    private UserAuthService userAuthService;

    @ApiOperation(value = "修改管理员密码")
    @PutMapping("/updateAdminPwd")
    public Result<?> updateAdminPassword(@Valid @RequestBody PasswordVO passwordVO) {
        userAuthService.updateAdminPassword(passwordVO);
        return Result.ok();
    }

    @ApiOperation(value = "查询后台用户列表")
    @GetMapping("/getUserList")
    public Result<PageResultVo<UserBackDTO>> getUserList(ConditionVo condition) {
        return Result.ok(userAuthService.getUserList(condition));
    }

    //@AccessLimit(seconds = 60, maxCount = 1)
    @ApiOperation(value = "发送邮箱验证码")
    @GetMapping("/sendCode")
    public Result<?> sendCode(String username,Integer state) {
        userAuthService.sendCode(username,state);
        return Result.ok();
    }

    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public Result<?> register(@Valid @RequestBody UserVo user) {
        userAuthService.register(user);
        return Result.ok();
    }

    @ApiOperation(value = "用户修改密码")
    @PostMapping("/forget")
    public Result<?> updatePassword(@Valid @RequestBody UserVo user) {
        userAuthService.updatePassword(user);
        return Result.ok();
    }
}

