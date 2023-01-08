package com.peintre.controller;



import com.peintre.dto.PhotoAlbumBackDTO;
import com.peintre.dto.PhotoAlbumDTO;
import com.peintre.enums.FilePathEnum;
import com.peintre.service.PhotoAlbumService;
import com.peintre.strategy.context.UploadStrategyContext;
import com.peintre.utils.Result;
import com.peintre.vo.ConditionVo;
import com.peintre.vo.PageResultVo;
import com.peintre.vo.PhotoAlbumVo;
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
 * @Description: (PhotoAlbum)表控制层
 * @Author: Peintre
 * @Date: 2022-12-22 11:20:49
 **/
@Api(tags = "相册模块")
@RestController
@RequestMapping("/photoAlbum")
public class PhotoAlbumController{

    @Autowired
    private PhotoAlbumService photoAlbumService;

    @Autowired
    private UploadStrategyContext uploadStrategyContext;

    @ApiOperation(value = "(后台)条件查询相册")
    @GetMapping("/getAlbums")
    public Result<PageResultVo<PhotoAlbumBackDTO>> listPhotoAlbumBacks(ConditionVo condition) {
        return Result.ok(photoAlbumService.listPhotoAlbumBacks(condition));
    }

    @ApiOperation(value = "(后台)查询相册")
    @GetMapping("/getAlbumsAny")
    public Result<List<PhotoAlbumDTO>> listPhotoAlbumBackInfos() {
        return Result.ok(photoAlbumService.listPhotoAlbumBackInfos());
    }

    @ApiOperation(value = "根据Id查询相册")
    @GetMapping("/getAlbumById/{albumId}")
    public Result<PhotoAlbumBackDTO> getAlbumById(@PathVariable("albumId") Integer albumId) {
        return Result.ok(photoAlbumService.getAlbumById(albumId));
    }

    @ApiOperation(value = "上传相册封面")
    @ApiImplicitParam(name = "file", value = "相册封面", required = true, dataType = "MultipartFile")
    @PostMapping("/uploadAlbumCover")
    public Result<String> savePhotoAlbumCover(MultipartFile file) {
        return Result.ok(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.PHOTO.getPath()));
    }

    //@OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "新增相册")
    @PostMapping("/addAlbum")
    public Result<?> addAlbum(@Valid @RequestBody PhotoAlbumVo photoAlbumVo) {
        photoAlbumService.saveOrUpdatePhotoAlbum(photoAlbumVo);
        return Result.ok();
    }

    //@OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "修改相册")
    @PostMapping("/updateAlbum")
    public Result<?> updateAlbum(@Valid @RequestBody PhotoAlbumVo photoAlbumVo) {
        photoAlbumService.saveOrUpdatePhotoAlbum(photoAlbumVo);
        return Result.ok();
    }

    //@OptLog(optType = REMOVE)
    @ApiOperation(value = "根据id删除相册")
    @ApiImplicitParam(name = "albumId", value = "相册id", required = true, dataType = "Integer")
    @DeleteMapping("/deleteAlbum/{albumId}")
    public Result<?> deletePhotoAlbumById(@PathVariable("albumId") Integer albumId) {
        photoAlbumService.deletePhotoAlbumById(albumId);
        return Result.ok();
    }

    @ApiOperation(value = "(前台)查询相册")
    @GetMapping("/blog/getAlbums")
    public Result<List<PhotoAlbumDTO>> listPhotoAlbums() {
        return Result.ok(photoAlbumService.listPhotoAlbums());
    }

}

