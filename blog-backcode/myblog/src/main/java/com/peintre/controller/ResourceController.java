package com.peintre.controller;



import com.peintre.dto.LabelOptionDTO;
import com.peintre.dto.ResourceDTO;
import com.peintre.service.ResourceService;
import com.peintre.utils.Result;
import com.peintre.vo.ConditionVo;
import com.peintre.vo.ResourceVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * @Program: MyBlog
 * @Description: (Resource)表控制层
 * @Author: Peintre
 * @Date: 2022-12-03 15:59:21
 **/
@Api(tags = "资源模块")
@RestController
@RequestMapping("/resource")
public class ResourceController{

    @Autowired
    private ResourceService resourceService;


    @ApiOperation(value = "查看所有资源权限")
    @GetMapping("/getAllResources")
    public Result<List<LabelOptionDTO>> listResourceOption() {
        return Result.ok(resourceService.listResourceOption());
    }

    @ApiOperation(value = "查看资源权限列表")
    @GetMapping("/getAllResourcesList")
    public Result<List<ResourceDTO>> listResources(ConditionVo conditionVo) {
        return Result.ok(resourceService.listResources(conditionVo));
    }

    @ApiOperation(value = "新增/修改资源权限")
    @PostMapping("/saveOrUpdateResource")
    public Result<?> saveOrUpdateResource(@RequestBody @Valid ResourceVo resourceVo) {
        resourceService.saveOrUpdateResource(resourceVo);
        return Result.ok();
    }

    @ApiOperation(value = "删除资源权限")
    @DeleteMapping("/deleteResource/{resourceId}")
    public Result<?> deleteResource(@PathVariable("resourceId") Integer resourceId) {
        resourceService.deleteResource(resourceId);
        return Result.ok();
    }


}

