package com.peintre.controller;



import com.peintre.dto.TalkBackDTO;
import com.peintre.dto.blog.TalkDTO;
import com.peintre.enums.FilePathEnum;
import com.peintre.service.TalkService;
import com.peintre.strategy.context.UploadStrategyContext;
import com.peintre.utils.Result;
import com.peintre.vo.ConditionVo;
import com.peintre.vo.IdsVo;
import com.peintre.vo.PageResultVo;
import com.peintre.vo.TalkVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;


/**
 * @Program: MyBlog
 * @Description: (Talk)表控制层
 * @Author: Peintre
 * @Date: 2022-12-22 11:36:49
 **/
@Api(tags = "说说模块")
@RestController
@RequestMapping("/talk")
public class TalkController{

    @Autowired
    private TalkService talkService;

    @Autowired
    private UploadStrategyContext uploadStrategyContext;

    @ApiOperation(value = "(后台)查看说说")
    @GetMapping("/getTalks")
    public Result<PageResultVo<TalkBackDTO>> listBackTalks(ConditionVo conditionVO) {
        return Result.ok(talkService.listBackTalks(conditionVO));
    }

    @ApiOperation(value = "(后台)根据id查看说说")
    @ApiImplicitParam(name = "talkId", value = "说说id", required = true, dataType = "Integer")
    @GetMapping("/getTalk/{talkId}")
    public Result<TalkBackDTO> getBackTalkById(@PathVariable("talkId") Integer talkId) {
        return Result.ok(talkService.getBackTalkById(talkId));
    }

    @ApiOperation(value = "上传说说图片")
    @ApiImplicitParam(name = "file", value = "说说图片", required = true, dataType = "MultipartFile")
    @PostMapping("/uploadImages")
    public Result<String> saveTalkImages(MultipartFile file) {
        return Result.ok(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.TALK.getPath()));
    }

    @ApiOperation(value = "添加说说")
    @PostMapping("/addTalk")
    public Result<?> addTalk(@Valid @RequestBody TalkVo talkVo) {
        talkService.saveOrUpdateTalk(talkVo);
        return Result.ok();
    }

    @ApiOperation(value = "修改说说")
    @PostMapping("/updateTalk")
    public Result<?> updateTalk(@Valid @RequestBody TalkVo talkVo) {
        talkService.saveOrUpdateTalk(talkVo);
        return Result.ok();
    }

    @ApiOperation(value = "删除说说")
    @PostMapping("/deleteTalks")
    public Result<?> deleteTalks(@Valid @RequestBody IdsVo idsVo) {
        talkService.deleteTalks(idsVo);
        return Result.ok();
    }

    @ApiOperation(value = "(前台)查询首页说说")
    @GetMapping("/blog/getTalks")
    public Result<List<String>> listHomeTalks() {
        return Result.ok(talkService.listHomeTalks());
    }

    @ApiOperation(value = "(前台)查询说说")
    @GetMapping("/blog/getTalkList")
    public Result<PageResultVo<TalkDTO>> listTalks() {
        return Result.ok(talkService.listTalks());
    }

    @ApiOperation(value = "(前台)根据id查看说说")
    @ApiImplicitParam(name = "talkId", value = "说说id", required = true, dataType = "Integer")
    @GetMapping("/blog/getTalk/{talkId}")
    public Result<TalkDTO> getTalkById(@PathVariable("talkId") Integer talkId) {
        return Result.ok(talkService.getTalkById(talkId));
    }

    @ApiOperation(value = "(前台)点赞说说")
    @ApiImplicitParam(name = "talkId", value = "说说id", required = true, dataType = "Integer")
    @PostMapping("/blog/talkLike/{talkId}")
    public Result<?> saveTalkLike(@PathVariable("talkId") Integer talkId) {
        talkService.saveTalkLike(talkId);
        return Result.ok();
    }
}

