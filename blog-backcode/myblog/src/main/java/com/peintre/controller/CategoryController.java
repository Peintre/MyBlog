package com.peintre.controller;



import com.peintre.dto.CategoryBackDTO;
import com.peintre.dto.CategoryOptionDTO;
import com.peintre.dto.blog.CategoryDTO;
import com.peintre.service.CategoryService;
import com.peintre.utils.Result;
import com.peintre.vo.CategoryVo;
import com.peintre.vo.ConditionVo;
import com.peintre.vo.IdsVo;
import com.peintre.vo.PageResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * @Program: MyBlog
 * @Description: (Category)表控制层
 * @Author: Peintre
 * @Date: 2022-12-18 17:15:21
 **/
@Api(tags = "分类模块")
@RestController
@RequestMapping("/category")
public class CategoryController{

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "条件查询分类")
    @GetMapping("/getCategory")
    public Result<List<CategoryOptionDTO>> listCategoriesBySearch(ConditionVo condition) {
        return Result.ok(categoryService.listCategoriesBySearch(condition));
    }

    @ApiOperation(value = "(后台)条件查询分类")
    @GetMapping("/getCategories")
    public Result<PageResultVo<CategoryBackDTO>> listBackCategories(ConditionVo condition) {
        return Result.ok(categoryService.listBackCategories(condition));
    }

    //@OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "添加分类")
    @PostMapping("/addCategory")
    public Result<?> addCategory(@Valid @RequestBody CategoryVo categoryVo) {
        categoryService.addOrUpdateCategory(categoryVo);
        return Result.ok();
    }

    //@OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "修改分类")
    @PostMapping("/upDateCategory")
    public Result<?> upDateCategory(@Valid @RequestBody CategoryVo categoryVo) {
        categoryService.addOrUpdateCategory(categoryVo);
        return Result.ok();
    }

    //@OptLog(optType = REMOVE)
    @ApiOperation(value = "删除分类")
    @PostMapping("/deleteCategories")
    public Result<?> deleteCategories(@Valid @RequestBody IdsVo idsVo) {
        categoryService.deleteCategory(idsVo);
        return Result.ok();
    }

    @ApiOperation(value = "(前台)查询分类")
    @GetMapping("/blog/getCategories")
    public Result<PageResultVo<CategoryDTO>> listCategories() {
        return Result.ok(categoryService.listCategories());
    }

}

