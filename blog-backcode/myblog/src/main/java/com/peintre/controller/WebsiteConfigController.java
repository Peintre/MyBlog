package com.peintre.controller;



import com.peintre.dto.blog.WebConfigDTO;
import com.peintre.enums.FilePathEnum;
import com.peintre.service.BlogInfoService;
import com.peintre.service.WebsiteConfigService;
import com.peintre.strategy.context.UploadStrategyContext;
import com.peintre.utils.Result;
import com.peintre.vo.BlogInfoVo;
import com.peintre.vo.WebsiteConfigVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;


/**
 * @Program: MyBlog
 * @Description: (WebsiteConfig)表控制层
 * @Author: Peintre
 * @Date: 2022-12-22 10:30:35
 **/
@Api(tags = "博客信息模块")
@RestController
@RequestMapping("/websiteConfig")
public class WebsiteConfigController{

    @Autowired
    private WebsiteConfigService websiteConfigService;

    @Autowired
    private BlogInfoService blogInfoService;

    @Autowired
    private UploadStrategyContext uploadStrategyContext;


    @ApiOperation(value = "获取网站配置")
    @GetMapping("/getConfig")
    public Result<WebsiteConfigVo> getWebsiteConfig() {
        return Result.ok(blogInfoService.getWebsiteConfig());
    }

    @ApiOperation(value = "博客信息图片")
    @ApiImplicitParam(name = "file", value = "图片", required = true, dataType = "MultipartFile")
    @PostMapping("/image")
    public Result<String> savePhotoAlbumCover(MultipartFile file) {
        return Result.ok(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.CONFIG.getPath()));
    }

    @ApiOperation(value = "查看关于我信息")
    @GetMapping("/about")
    public Result<String> getAbout() {
        return Result.ok(blogInfoService.getAbout());
    }

    //@OptLog(optType = UPDATE)
    @ApiOperation(value = "修改关于我信息")
    @PostMapping("/updateAbout")
    public Result<?> updateAbout(@Valid @RequestBody BlogInfoVo blogInfoVo) {
        blogInfoService.updateAbout(blogInfoVo);
        return Result.ok();
    }

    @ApiOperation(value = "更新网站配置")
    @PostMapping("/updateWebsiteConfig")
    public Result<?> updateWebsiteConfig(@Valid @RequestBody WebsiteConfigVo websiteConfigVo) {
        websiteConfigService.updateWebsiteConfig(websiteConfigVo);
        return Result.ok();
    }

    @ApiOperation(value = "(前台)获取网站配置")
    @GetMapping("/getWebConfig")
    public Result<WebConfigDTO> getWebsiteConfigBlog() {
        return Result.ok(blogInfoService.getWebsiteConfigBlog());
    }



}

