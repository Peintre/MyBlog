package com.peintre.controller;



import com.peintre.dto.PhotoBackDTO;
import com.peintre.dto.blog.PhotoDTO;
import com.peintre.enums.FilePathEnum;
import com.peintre.service.PhotoService;
import com.peintre.strategy.context.UploadStrategyContext;
import com.peintre.utils.Result;
import com.peintre.vo.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;


/**
 * @Program: MyBlog
 * @Description: (Photo)表控制层
 * @Author: Peintre
 * @Date: 2022-12-27 18:11:24
 **/
@RestController
@RequestMapping("/photo")
public class PhotoController{

    @Autowired
    private PhotoService photoService;

    @Autowired
    private UploadStrategyContext uploadStrategyContext;

    @ApiOperation(value = "(后台)条件查询照片")
    @GetMapping("/getPhotos")
    public Result<PageResultVo<PhotoBackDTO>> listPhotos(ConditionVo condition) {
        return Result.ok(photoService.listPhotos(condition));
    }

    @ApiOperation(value = "上传照片")
    @ApiImplicitParam(name = "file", value = "照片", required = true, dataType = "MultipartFile")
    @PostMapping("/uploadPhoto")
    public Result<String> savePhoto(MultipartFile file) {
        return Result.ok(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.PHOTO.getPath()));
    }

    //@OptLog(optType = SAVE)
    @ApiOperation(value = "添加照片到相册")
    @PostMapping("/addPhotosToAlbum")
    public Result<?> addPhotosToAlbum(@Valid @RequestBody PhotoListVo photoListVo) {
        photoService.addPhotosToAlbum(photoListVo);
        return Result.ok();
    }

    //@OptLog(optType = UPDATE)
    @ApiOperation(value = "修改照片信息")
    @PostMapping("/updatePhoto")
    public Result<?> updatePhoto(@Valid @RequestBody PhotoInfoVo photoInfoVo) {
        photoService.updatePhoto(photoInfoVo);
        return Result.ok();
    }

    //@OptLog(optType = REMOVE)
    @ApiOperation(value = "删除照片")
    @PostMapping("/deletePhotos")
    public Result<?> deletePhotos(@Valid @RequestBody IdsVo idsVo) {
        photoService.deletePhotos(idsVo);
        return Result.ok();
    }

    //@OptLog(optType = REMOVE)
    @ApiOperation(value = "恢复照片")
    @PostMapping("/recoverPhotos")
    public Result<?> recoverPhotos(@Valid @RequestBody IdsVo idsVo) {
        photoService.recoverPhotos(idsVo);
        return Result.ok();
    }

    //@OptLog(optType = UPDATE)
    @ApiOperation(value = "移动照片")
    @PostMapping("/movePhotos")
    public Result<?> updatePhotosAlbum(@Valid @RequestBody PhotoListVo photoListVo) {
        photoService.updatePhotosAlbum(photoListVo);
        return Result.ok();
    }

    //@OptLog(optType = REMOVE)
    @ApiOperation(value = "删除照片(物理)")
    @PostMapping("/deleteOkPhotos")
    public Result<?> deleteOkPhotos(@Valid @RequestBody IdsVo idsVo) {
        photoService.deleteOkPhotos(idsVo);
        return Result.ok();
    }

    @ApiOperation(value = "(前台)根据相册id查询照片")
    @GetMapping("/blog/getPhotos/{albumId}")
    public Result<PhotoDTO> listPhotosByAlbumId(@PathVariable("albumId") Integer albumId) {
        return Result.ok(photoService.listPhotosByAlbumId(albumId));
    }

}

