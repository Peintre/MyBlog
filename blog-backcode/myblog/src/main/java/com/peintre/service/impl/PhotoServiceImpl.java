package com.peintre.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.peintre.config.BizException;
import com.peintre.dao.PhotoAlbumDao;
import com.peintre.dao.PhotoDao;
import com.peintre.dto.PhotoAlbumDTO;
import com.peintre.dto.PhotoBackDTO;
import com.peintre.dto.blog.PhotoDTO;
import com.peintre.entity.Photo;
import com.peintre.entity.PhotoAlbum;
import com.peintre.service.PhotoAlbumService;
import com.peintre.service.PhotoService;
import com.peintre.utils.BeanCopyUtils;
import com.peintre.utils.PageUtils;
import com.peintre.vo.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.peintre.constant.CommonConst.FALSE;
import static com.peintre.constant.CommonConst.TRUE;
import static com.peintre.enums.ArticleStatusEnum.PUBLIC;

/**
 * @Program: MyBlog
 * @Description: (tb_photo)表服务实现类
 * @Author: Peintre
 * @Date: 2022-12-27 18:11:24
 **/
@Service("photoService")
public class PhotoServiceImpl extends ServiceImpl<PhotoDao, Photo> implements PhotoService {

    @Autowired
    private PhotoDao photoDao;

    @Autowired
    private PhotoAlbumService photoAlbumService;

    @Override
    public PageResultVo<PhotoBackDTO> listPhotos(ConditionVo condition) {
        // 查询照片列表
        Page<Photo> page = new Page<>(PageUtils.getCurrent(), PageUtils.getSize());
        Page<Photo> photoPage = photoDao.selectPage(page, new LambdaQueryWrapper<Photo>()
                .eq(Objects.nonNull(condition.getAlbumId()), Photo::getAlbumId, condition.getAlbumId())
                .eq(Photo::getIsDelete, condition.getIsDelete())
                .orderByDesc(Photo::getId)
                .orderByDesc(Photo::getUpdateTime));
        List<PhotoBackDTO> photoList = BeanCopyUtils.copyList(photoPage.getRecords(), PhotoBackDTO.class);
        return new PageResultVo<>(photoList, (int) photoPage.getTotal());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addPhotosToAlbum(PhotoListVo photoListVo) {
        List<Photo> photoList = photoListVo.getPhotoUrlList().stream().map(item -> Photo.builder()
                .albumId(photoListVo.getAlbumId())
                .photoName(IdWorker.getIdStr())
                .photoSrc(item)
                .build())
                .collect(Collectors.toList());
        this.saveBatch(photoList);
    }

    @Override
    public void updatePhoto(PhotoInfoVo photoInfoVo) {
        Photo photo = BeanCopyUtils.copyObject(photoInfoVo, Photo.class);
        photoDao.updateById(photo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deletePhotos(IdsVo idsVo) {
        List<Photo> listPhotos = idsVo.getIdList().stream().map(item ->
                Photo.builder().id(item).isDelete(TRUE).build()
        ).collect(Collectors.toList());
        this.updateBatchById(listPhotos);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void recoverPhotos(IdsVo idsVo) {
        //恢复相片
        List<Photo> listPhotos = idsVo.getIdList().stream().map(item ->
                Photo.builder().id(item).isDelete(FALSE).build()
        ).collect(Collectors.toList());
        this.updateBatchById(listPhotos);
        // 若恢复照片所在的相册已删除，恢复相册
        List<PhotoAlbum> photoAlbumList = photoDao.selectList(new LambdaQueryWrapper<Photo>()
                .select(Photo::getAlbumId)
                .in(Photo::getId, idsVo.getIdList())
                .groupBy(Photo::getAlbumId))
                .stream()
                .map(item -> PhotoAlbum.builder()
                        .id(item.getAlbumId())
                        .isDelete(FALSE)
                        .build())
                .collect(Collectors.toList());
        photoAlbumService.updateBatchById(photoAlbumList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updatePhotosAlbum(PhotoListVo photoListVo) {
        List<Photo> photoList = photoListVo.getPhotoIdList().stream().map(item -> Photo.builder()
                .id(item)
                .albumId(photoListVo.getAlbumId())
                .build())
                .collect(Collectors.toList());
        this.updateBatchById(photoList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteOkPhotos(IdsVo idsVo) {
        photoDao.deleteBatchIds(idsVo.getIdList());
    }

    @Override
    public PhotoDTO listPhotosByAlbumId(Integer albumId) {
        // 查询相册信息
        PhotoAlbum photoAlbum = photoAlbumService.getOne(new LambdaQueryWrapper<PhotoAlbum>()
                .eq(PhotoAlbum::getId, albumId)
                .eq(PhotoAlbum::getIsDelete, FALSE)
                .eq(PhotoAlbum::getStatus, PUBLIC.getStatus()));
        if (Objects.isNull(photoAlbum)) {
            throw new BizException("相册不存在");
        }
        // 查询照片列表
        Page<Photo> page = new Page<>(PageUtils.getCurrent(), PageUtils.getSize());
        List<String> photoList = photoDao.selectPage(page, new LambdaQueryWrapper<Photo>()
                .select(Photo::getPhotoSrc)
                .eq(Photo::getAlbumId, albumId)
                .eq(Photo::getIsDelete, FALSE)
                .orderByDesc(Photo::getId))
                .getRecords()
                .stream()
                .map(Photo::getPhotoSrc)
                .collect(Collectors.toList());
        return PhotoDTO.builder()
                .photoAlbumCover(photoAlbum.getAlbumCover())
                .photoAlbumName(photoAlbum.getAlbumName())
                .photoList(photoList)
                .build();
    }
}

