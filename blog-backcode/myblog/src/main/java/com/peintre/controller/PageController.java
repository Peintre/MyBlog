package com.peintre.controller;



import com.peintre.service.PageService;
import com.peintre.utils.Result;
import com.peintre.vo.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * @Program: MyBlog
 * @Description: (Page)表控制层
 * @Author: Peintre
 * @Date: 2022-12-22 10:44:25
 **/
@Api(tags = "页面模块")
@RestController
@RequestMapping("/page")
public class PageController{

    @Autowired
    private PageService pageService;

    @ApiOperation(value = "查询页面")
    @GetMapping("/getPages")
    public Result<List<PageVo>> listPages() {
        return Result.ok(pageService.listPages());
    }

    //@OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "添加页面")
    @PostMapping("/addPage")
    public Result<?> addPage(@Valid @RequestBody PageVo pageVo) {
        pageService.saveOrUpdatePage(pageVo);
        return Result.ok();
    }

    //@OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "修改页面")
    @PostMapping("/updatePage")
    public Result<?> updatePage(@Valid @RequestBody PageVo pageVo) {
        pageService.saveOrUpdatePage(pageVo);
        return Result.ok();
    }

    //@OptLog(optType = REMOVE)
    @ApiOperation(value = "删除页面")
    @ApiImplicitParam(name = "pageId", value = "页面id", required = true, dataType = "Integer")
    @DeleteMapping("/deletePage/{pageId}")
    public Result<?> deletePage(@PathVariable("pageId") Integer pageId) {
        pageService.deletePage(pageId);
        return Result.ok();
    }

}

