package com.peintre.controller;



import com.peintre.dto.RoleDTO;
import com.peintre.dto.UserRoleDTO;
import com.peintre.service.RoleService;
import com.peintre.utils.Result;
import com.peintre.vo.ConditionVo;
import com.peintre.vo.PageResultVo;
import com.peintre.vo.RoleMenuResourceVo;
import com.peintre.vo.RoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * @Program: MyBlog
 * @Description: (Role)表控制层
 * @Author: Peintre
 * @Date: 2022-11-10 12:22:50
 **/
@RestController
@Api(tags = "角色模块")
@RequestMapping("/role")
public class RoleController{

    @Autowired
    private RoleService roleService;


    @ApiOperation(value = "根据条件查询角色")
    @GetMapping("/getAllRoles")
    public Result<PageResultVo<RoleDTO>> listRoles(ConditionVo conditionVO) {
        return Result.ok(roleService.listRoles(conditionVO));
    }


    @ApiOperation(value = "查询用户角色信息")
    @GetMapping("/getUserRoles")
    public Result<List<UserRoleDTO>> listUserRoles() {
        return Result.ok(roleService.listUserRoles());
    }


    @ApiOperation(value = "修改角色信息")
    @PostMapping("/updateRoleMenuResource")
    public Result<?> updateRoleMenuResource(@RequestBody @Valid RoleMenuResourceVo roleMenuResourceVo) {
        roleService.updateRoleMenuResource(roleMenuResourceVo);
        return Result.ok();
    }

    @ApiOperation(value = "删除角色基本信息")
    @DeleteMapping("/deleteRoles")
    public Result<?> deleteRoles(@RequestBody List<Integer> roleIdList) {
        roleService.deleteRoles(roleIdList);
        return Result.ok();
    }

    @ApiOperation(value = "新增角色基本信息")
    @PostMapping("/addRole")
    public Result<?> addRole(@RequestBody @Valid RoleVo roleVo) {
        roleService.addRole(roleVo);
        return Result.ok();
    }

    @ApiOperation(value = "修改角色基本信息")
    @PostMapping("/updateRole")
    public Result<?> updateRole(@RequestBody @Valid RoleVo roleVo) {
        roleService.updateRole(roleVo);
        return Result.ok();
    }

}

