package com.peintre.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.peintre.dto.ResourceRoleDTO;
import com.peintre.dto.RoleDTO;
import com.peintre.vo.ConditionVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.peintre.entity.Role;

import java.util.List;


/**
 * @Program: MyBlog
 * @Description: (tb_role)表数据访问层
 * @Author: Peintre
 * @Date: 2022-11-10 12:22:50
 **/
@Repository
public interface RoleDao extends BaseMapper<Role> {

    /**
     * 根据用户id获取角色列表
     *
     * @param userInfoId 用户id
     * @return 角色标签
     */
    List<String> listRolesByUserInfoId(Integer userInfoId);

    /**
     * 查询角色列表
     *
     * @param current     页码
     * @param size        条数
     * @param conditionVo 条件
     * @return 角色列表
     */
    List<RoleDTO> listRoles(@Param("current") Long current, @Param("size") Long size, @Param("conditionVO") ConditionVo conditionVo);

    /**
     * 查询所有资源权限角色列表
     *
     * @return 资源权限角色列表
     */
    List<ResourceRoleDTO> listResourceRoles();
}

