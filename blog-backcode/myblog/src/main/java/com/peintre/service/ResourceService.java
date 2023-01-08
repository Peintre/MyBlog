package com.peintre.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.peintre.dto.LabelOptionDTO;
import com.peintre.dto.ResourceDTO;
import com.peintre.entity.Resource;
import com.peintre.vo.ConditionVo;
import com.peintre.vo.ResourceVo;

import java.util.List;

/**
 * @Program: MyBlog
 * @Description: (tb_resource)表服务接口
 * @Author: Peintre
 * @Date: 2022-12-03 16:01:57
 **/
public interface ResourceService extends IService<Resource> {

    /**
     * 获取所有资源权限(返回树形式)
     *
     * @return 权限模块
     */
    List<LabelOptionDTO> listResourceOption();

    /**
     * 获取所有资源权限
     *
     * @return 资源权限列表
     */
    List<ResourceDTO> listResources(ConditionVo conditionVo);

    /**
     * 新增/修改资源权限
     *
     * @return resourceVo 资源对象
     */
    void saveOrUpdateResource(ResourceVo resourceVo);

    /***
     * 删除资源权限
     *
     @param resourceId 资源id
     */
    void deleteResource(Integer resourceId);
}

