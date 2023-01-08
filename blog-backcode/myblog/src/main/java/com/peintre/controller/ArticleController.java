package com.peintre.controller;



import com.peintre.dto.ArticleBackDTO;
import com.peintre.dto.blog.ArchiveArticleDTO;
import com.peintre.dto.blog.ArticleBlogDTO;
import com.peintre.dto.blog.ArticleDTO;
import com.peintre.dto.blog.ArticlePreviewListDTO;
import com.peintre.enums.FilePathEnum;
import com.peintre.service.ArticleService;
import com.peintre.strategy.context.UploadStrategyContext;
import com.peintre.utils.Result;
import com.peintre.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

import static com.peintre.constant.CommonConst.FALSE;
import static com.peintre.constant.CommonConst.TRUE;


/**
 * @Program: MyBlog
 * @Description: (Article)表控制层
 * @Author: Peintre
 * @Date: 2022-12-18 16:34:08
 **/
@Api(tags = "文章模块")
@RestController
@RequestMapping("/article")
public class ArticleController{

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UploadStrategyContext uploadStrategyContext;


    @ApiOperation(value = "(后台)条件查询文章")
    @GetMapping("/getBackArticleList")
    public Result<PageResultVo<ArticleBackDTO>> listArticleBacks(ConditionVo conditionVo) {
        return Result.ok(articleService.listArticleBacks(conditionVo));
    }


    @ApiOperation(value = "导出文章")
    @ApiImplicitParam(name = "articleIdList", value = "文章id", required = true, dataType = "List<Integer>")
    @PostMapping("/exportArticles")
    public Result<List<String>> exportArticles(@RequestBody List<Integer> articleIdList) {
        return Result.ok(articleService.exportArticles(articleIdList));
    }

    //@OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "添加/修改文章")
    @PostMapping("/saveOrUpdateArticle")
    public Result<?> saveOrUpdateArticle(@Valid @RequestBody ArticleVo articleVo) {
        articleService.saveOrUpdateArticle(articleVo);
        return Result.ok();
    }

    @ApiOperation(value = "上传文章图片")
    @ApiImplicitParam(name = "file", value = "文章图片", required = true, dataType = "MultipartFile")
    @PostMapping("/images")
    public Result<String> saveArticleImages(MultipartFile file) {
        return Result.ok(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.ARTICLE.getPath()));
    }


    //@OptLog(optType = UPDATE)
    @ApiOperation(value = "删除文章(逻辑删除)")
    @PostMapping("/deleteArticles")
    public Result<?> deleteArticles(@Valid @RequestBody IdsVo idsVo) {
        articleService.updateArticlesStatus(idsVo,TRUE);
        return Result.ok();
    }

    @ApiOperation(value = "恢复文章(逻辑删除)")
    @PostMapping("/recoveryArticles")
    public Result<?> recoveryArticles(@Valid @RequestBody IdsVo idsVo) {
        articleService.updateArticlesStatus(idsVo,FALSE);
        return Result.ok();
    }

    @ApiOperation(value = "删除文章(物理删除)")
    @PostMapping("/deleteOkArticles")
    public Result<?> deleteOkArticles(@Valid @RequestBody IdsVo idsVo) {
        articleService.deleteArticles(idsVo);
        return Result.ok();
    }

    @ApiOperation(value = "(后台)根据id查询文章")
    @ApiImplicitParam(name = "articleId", value = "文章id", required = true, dataType = "Integer")
    @GetMapping("/getBackArticle/{articleId}")
    public Result<ArticleVo> getArticleBackById(@PathVariable("articleId") Integer articleId) {
        return Result.ok(articleService.getArticleBackById(articleId));
    }

    //@OptLog(optType = UPDATE)
    @ApiOperation(value = "修改文章置顶状态")
    @PostMapping("/articleTopStatus")
    public Result<?> updateArticleTop(@Valid @RequestBody ArticleTopVo articleTopVo) {
        articleService.updateArticleTop(articleTopVo);
        return Result.ok();
    }

    @ApiOperation(value = "(前台)查询文章")
    @GetMapping("/blog/getArticles")
    public Result<List<ArticleBlogDTO>> listArticles() {
        return Result.ok(articleService.listArticles());
    }

    @ApiOperation(value = "(前台)查询文章归档")
    @GetMapping("/blog/getAchieveArticles")
    public Result<PageResultVo<ArchiveArticleDTO>> listArchives() {
        return Result.ok(articleService.listArchives());
    }

    @ApiOperation(value = "(前台)根据id查询文章")
    @ApiImplicitParam(name = "articleId", value = "文章id", required = true, dataType = "Integer")
    @GetMapping("/blog/getArticleById/{articleId}")
    public Result<ArticleDTO> getArticleById(@PathVariable("articleId") Integer articleId) {
        return Result.ok(articleService.getArticleById(articleId));
    }

    @ApiOperation(value = "(前台)条件查询文章")
    @GetMapping("/blog/getArticleList")
    public Result<ArticlePreviewListDTO> listArticlesByCondition(ConditionVo condition) {
        return Result.ok(articleService.listArticlesByCondition(condition));
    }
}

