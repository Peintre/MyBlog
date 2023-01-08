package com.peintre.controller;



import com.peintre.dto.LabelOptionDTO;
import com.peintre.dto.MenuDTO;
import com.peintre.dto.UserMenuDTO;
import com.peintre.service.MenuService;
import com.peintre.utils.Result;
import com.peintre.vo.ConditionVo;
import com.peintre.vo.MenuVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * @Program: MyBlog
 * @Description: (Menu)表控制层
 * @Author: Peintre
 * @Date: 2022-11-10 13:15:05
 **/
@Api(tags = "菜单模块")
@RestController
@RequestMapping("/menu")
public class MenuController{

    @Autowired
    private MenuService menuService;


    @ApiOperation(value = "查看当前用户菜单")
    @GetMapping("/getUserMenus")
    public Result<List<UserMenuDTO>> listUserMenus() {
        return Result.ok(menuService.listUserMenus());
    }

    @ApiOperation(value = "查看所有菜单信息")
    @GetMapping("/getAllMenus")
    public Result<List<LabelOptionDTO>> listMenuOptions() {
        return Result.ok(menuService.listMenuOptions());
    }

    @ApiOperation(value = "根据条件查询菜单")
    @GetMapping("/getMenusList")
    public Result<List<MenuDTO>> listMenus(ConditionVo conditionVO) {
        return Result.ok(menuService.listMenus(conditionVO));
    }

    @ApiOperation(value = "新增/修改菜单")
    @PostMapping("/saveOrUpdateMenu")
    public Result<?> saveOrUpdateMenu(@Valid @RequestBody MenuVo menuVo) {
        menuService.saveOrUpdateMenu(menuVo);
        return Result.ok();
    }

    @ApiOperation(value = "删除菜单")
    @DeleteMapping("/deleteMenu/{menuId}")
    public Result<?> deleteMenu(@PathVariable("menuId") Integer menuId){
        menuService.deleteMenu(menuId);
        return Result.ok();
    }
}

