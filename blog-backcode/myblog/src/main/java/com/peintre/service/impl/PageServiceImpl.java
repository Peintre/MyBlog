package com.peintre.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.peintre.dao.PageDao;
import com.peintre.entity.Page;
import com.peintre.service.PageService;
import com.peintre.service.RedisService;
import com.peintre.utils.BeanCopyUtils;
import com.peintre.vo.PageVo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.peintre.constant.RedisPrefixConst.PAGE_COVER;

/**
 * @Program: MyBlog
 * @Description: (tb_page)表服务实现类
 * @Author: Peintre
 * @Date: 2022-12-22 10:44:25
 **/
@Service("pageService")
public class PageServiceImpl extends ServiceImpl<PageDao, Page> implements PageService {
   
    @Autowired
    private PageDao pageDao;

    @Autowired
    private RedisService redisService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<PageVo> listPages() {
        List<PageVo> pageVOList;
        // 查找缓存信息，不存在则从mysql读取，更新缓存
        Object pageList = redisService.get(PAGE_COVER);
        if (Objects.nonNull(pageList)) {
            pageVOList = JSON.parseObject(pageList.toString(), List.class);
        } else {
            pageVOList = BeanCopyUtils.copyList(pageDao.selectList(null), PageVo.class);
            redisService.set(PAGE_COVER, JSON.toJSONString(pageVOList));
        }
        return pageVOList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdatePage(PageVo pageVo) {
        Page page = BeanCopyUtils.copyObject(pageVo, Page.class);
        this.saveOrUpdate(page);
        // 删除缓存
        redisService.del(PAGE_COVER);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deletePage(Integer pageId) {
        pageDao.deleteById(pageId);
        // 删除缓存
        redisService.del(PAGE_COVER);
    }
}

