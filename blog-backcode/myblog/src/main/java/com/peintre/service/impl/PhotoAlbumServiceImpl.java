package com.peintre.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.peintre.config.BizException;
import com.peintre.dao.PhotoAlbumDao;
import com.peintre.dao.PhotoDao;
import com.peintre.dto.PhotoAlbumBackDTO;
import com.peintre.dto.PhotoAlbumDTO;
import com.peintre.entity.Photo;
import com.peintre.entity.PhotoAlbum;
import com.peintre.service.PhotoAlbumService;
import com.peintre.utils.BeanCopyUtils;
import com.peintre.utils.PageUtils;
import com.peintre.vo.ConditionVo;
import com.peintre.vo.PageResultVo;
import com.peintre.vo.PhotoAlbumVo;
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
 * @Description: (tb_photo_album)表服务实现类
 * @Author: Peintre
 * @Date: 2022-12-22 11:20:49
 **/
@Service("photoAlbumService")
public class PhotoAlbumServiceImpl extends ServiceImpl<PhotoAlbumDao, PhotoAlbum> implements PhotoAlbumService {

    @Autowired
    private PhotoAlbumDao photoAlbumDao;

    @Autowired
    private PhotoDao photoDao;

    @Override
    public PageResultVo<PhotoAlbumBackDTO> listPhotoAlbumBacks(ConditionVo condition) {
        // 查询相册数量
        Integer count = photoAlbumDao.selectCount(new LambdaQueryWrapper<PhotoAlbum>()
                .like(StringUtils.isNotBlank(condition.getKeywords()), PhotoAlbum::getAlbumName, condition.getKeywords())
                .eq(PhotoAlbum::getIsDelete, FALSE));
        if (count == 0) {
            return new PageResultVo<>();
        }
        // 查询相册信息
        List<PhotoAlbumBackDTO> photoAlbumBackList = photoAlbumDao.listPhotoAlbumBacks(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        return new PageResultVo<>(photoAlbumBackList, count);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdatePhotoAlbum(PhotoAlbumVo photoAlbumVo) {
        // 查询相册名是否存在
        PhotoAlbum album = photoAlbumDao.selectOne(new LambdaQueryWrapper<PhotoAlbum>()
                .select(PhotoAlbum::getId)
                .eq(PhotoAlbum::getAlbumName, photoAlbumVo.getAlbumName()));
        if (Objects.nonNull(album) && !album.getId().equals(photoAlbumVo.getId())) {
            throw new BizException("相册名已存在");
        }
        PhotoAlbum photoAlbum = BeanCopyUtils.copyObject(photoAlbumVo, PhotoAlbum.class);
        this.saveOrUpdate(photoAlbum);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deletePhotoAlbumById(Integer albumId) {
        // 查询照片数量
        Integer count = photoDao.selectCount(new LambdaQueryWrapper<Photo>()
                .eq(Photo::getAlbumId, albumId));
        if (count > 0) {
            // 若相册下存在照片则逻辑删除相册和照片
            photoAlbumDao.updateById(PhotoAlbum.builder()
                    .id(albumId)
                    .isDelete(TRUE)
                    .build());
            photoDao.update(new Photo(), new LambdaUpdateWrapper<Photo>()
                    .set(Photo::getIsDelete, TRUE)
                    .eq(Photo::getAlbumId, albumId));
        } else {
            // 若相册下不存在照片则直接删除
            photoAlbumDao.deleteById(albumId);
        }
    }

    @Override
    public List<PhotoAlbumDTO> listPhotoAlbumBackInfos() {
        List<PhotoAlbum> photoAlbumList = photoAlbumDao.selectList(new LambdaQueryWrapper<PhotoAlbum>()
                .eq(PhotoAlbum::getIsDelete, FALSE));
        return photoAlbumList.stream().map(item -> PhotoAlbumDTO.builder()
                .id(item.getId())
                .photoAlbumCover(item.getAlbumCover())
                .photoAlbumName(item.getAlbumName())
                .build()
        ).collect(Collectors.toList());
    }

    @Override
    public PhotoAlbumBackDTO getAlbumById(Integer albumId) {
        // 查询照片数量
        Integer count = photoDao.selectCount(new LambdaQueryWrapper<Photo>()
                .eq(Photo::getAlbumId, albumId));
        //查询相册信息
        PhotoAlbum photoAlbum = photoAlbumDao.selectById(albumId);
        return PhotoAlbumBackDTO.builder()
                .id(albumId)
                .status(photoAlbum.getStatus())
                .albumCover(photoAlbum.getAlbumCover())
                .albumDesc(photoAlbum.getAlbumDesc())
                .albumName(photoAlbum.getAlbumName())
                .photoCount(count)
                .build();

    }

    @Override
    public List<PhotoAlbumDTO> listPhotoAlbums() {
        // 查询相册列表
        List<PhotoAlbum> photoAlbumList = photoAlbumDao.selectList(new LambdaQueryWrapper<PhotoAlbum>()
                .eq(PhotoAlbum::getStatus, PUBLIC.getStatus())
                .eq(PhotoAlbum::getIsDelete, FALSE)
                .orderByDesc(PhotoAlbum::getId));
        return photoAlbumList.stream().map(item ->PhotoAlbumDTO.builder()
                        .id(item.getId())
                        .photoAlbumName(item.getAlbumName())
                        .photoAlbumCover(item.getAlbumCover())
                        .photoAlbumDesc(item.getAlbumDesc())
                        .build()
        ).collect(Collectors.toList());
    }
}

