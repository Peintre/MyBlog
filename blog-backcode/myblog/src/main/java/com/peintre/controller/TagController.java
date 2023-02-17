package com.peintre.controller;



import com.peintre.dto.TagBackDTO;
import com.peintre.dto.TagDTO;
import com.peintre.service.TagService;
import com.peintre.utils.Result;
import com.peintre.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * @Program: MyBlog
 * @Description: (Tag)表控制层
 * @Author: Peintre
 * @Date: 2022-12-18 18:33:05
 **/
@Api(tags = "标签模块")
@RestController
@RequestMapping("/tag")
public class TagController{

    @Autowired
    private TagService tagService;

    @ApiOperation(value = "条件查询标签")
    @GetMapping("/getTag")
    public Result<List<TagDTO>> listTagsBySearch(ConditionVo condition) {
        return Result.ok(tagService.listTagsBySearch(condition));
    }

    @ApiOperation(value = "(后台)条件查询标签")
    @GetMapping("/getTags")
    public Result<PageResultVo<TagBackDTO>> listTagBackDTO(ConditionVo condition) {
        return Result.ok(tagService.listTagBackDTO(condition));
    }

    //@OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "添加标签")
    @PostMapping("/addTag")
    public Result<?> addTag(@Valid @RequestBody TagVo tagVo) {
        tagService.addOrUpdateTag(tagVo);
        return Result.ok();
    }

    //@OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "修改标签")
    @PostMapping("/upDateTag")
    public Result<?> upDateCategory(@Valid @RequestBody TagVo tagVo) {
        tagService.addOrUpdateTag(tagVo);
        return Result.ok();
    }

    //@OptLog(optType = REMOVE)
    @ApiOperation(value = "删除标签")
    @PostMapping("/deleteTags")
    public Result<?> deleteCategories(@Valid @RequestBody IdsVo idsVo) {
        tagService.deleteTag(idsVo);
        return Result.ok();
    }

    @ApiOperation(value = "(前台)查询标签")
    @GetMapping("/blog/getTags")
    public Result<PageResultVo<TagDTO>> listTags() {
        return Result.ok(tagService.listTags());
    }



}

