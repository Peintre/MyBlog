package com.peintre.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.peintre.dto.RoleDTO;
import com.peintre.dto.UserRoleDTO;
import com.peintre.entity.Role;
import com.peintre.vo.ConditionVo;
import com.peintre.vo.PageResultVo;
import com.peintre.vo.RoleMenuResourceVo;
import com.peintre.vo.RoleVo;

import java.util.List;

/**
 * @Program: MyBlog
 * @Description: (tb_role)表服务接口
 * @Author: Peintre
 * @Date: 2022-11-10 12:22:50
 **/
public interface RoleService extends IService<Role> {

    /**
     * 获取用户角色选项
     *
     * @return 角色
     */
    List<UserRoleDTO> listUserRoles();

    /**
     * 获取所有角色
     *
     * @return 角色列表(资源权限-菜单)
     */
    PageResultVo<RoleDTO> listRoles(ConditionVo conditionVO);

    /**
     * 修改角色(资源权限-菜单)信息
     *
     * @return void
     */
    void updateRoleMenuResource(RoleMenuResourceVo roleMenuResourceVo);

    /**
     * 删除角色基本信息
     *
     * @return void
     */
    void deleteRoles(List<Integer> roleIdList);

    /**
     * 新增角色基本信息
     *
     * @return void
     */
    void addRole(RoleVo roleVo);

    /**
     * 修改角色基本信息
     *
     * @return void
     */
    void updateRole(RoleVo roleVo);
}

