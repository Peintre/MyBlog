package com.peintre.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.peintre.config.BizException;
import com.peintre.dao.RoleDao;
import com.peintre.dao.UserRoleDao;
import com.peintre.dto.RoleDTO;
import com.peintre.dto.UserRoleDTO;
import com.peintre.entity.Role;
import com.peintre.entity.RoleMenu;
import com.peintre.entity.RoleResource;
import com.peintre.entity.UserRole;
import com.peintre.enums.StatusCode;
import com.peintre.handler.FilterInvocationSecurityMetadataSourceImpl;
import com.peintre.service.RoleMenuService;
import com.peintre.service.RoleResourceService;
import com.peintre.service.RoleService;
import com.peintre.utils.BeanCopyUtils;
import com.peintre.utils.PageUtils;
import com.peintre.vo.ConditionVo;
import com.peintre.vo.PageResultVo;
import com.peintre.vo.RoleMenuResourceVo;
import com.peintre.vo.RoleVo;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.peintre.constant.CommonConst.FALSE;

/**
 * @Program: MyBlog
 * @Description: (tb_role)表服务实现类
 * @Author: Peintre
 * @Date: 2022-11-10 12:22:50
 **/
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {
   
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private RoleResourceService roleResourceService;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private FilterInvocationSecurityMetadataSourceImpl filterInvocationSecurityMetadataSource;

    @Override
    public List<UserRoleDTO> listUserRoles() {
        // 查询用户角色列表
        List<Role> roleList = roleDao.selectList(new LambdaQueryWrapper<Role>()
                .select(Role::getId, Role::getRoleName));
        return BeanCopyUtils.copyList(roleList, UserRoleDTO.class);
    }

    @Override
    public PageResultVo<RoleDTO> listRoles(ConditionVo conditionVO) {
        // 查询角色(资源-菜单)列表
        List<RoleDTO> roleDTOList = roleDao.listRoles(PageUtils.getLimitCurrent(), PageUtils.getSize(), conditionVO);
        // 查询角色种类总量
        Integer count = roleDao.selectCount(new LambdaQueryWrapper<Role>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), Role::getRoleName, conditionVO.getKeywords()));
        return new PageResultVo<>(roleDTOList, count);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRoleMenuResource(RoleMenuResourceVo roleMenuResourceVo) {
        Role existRole = roleDao.selectOne(new LambdaQueryWrapper<Role>()
                .select(Role::getId)
                .eq(Role::getId, roleMenuResourceVo.getId()));
        if (Objects.isNull(existRole)) {
            throw new BizException("无法查询到该角色");
        }
        // 更新角色菜单关系
        if (Objects.nonNull(roleMenuResourceVo.getMenuIdList())) {
            roleMenuService.remove(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, roleMenuResourceVo.getId()));
            List<RoleMenu> roleMenuList = roleMenuResourceVo.getMenuIdList().stream()
                    .map(menuId -> RoleMenu.builder()
                            .roleId(roleMenuResourceVo.getId())
                            .menuId(menuId)
                            .build())
                    .collect(Collectors.toList());
            roleMenuService.saveBatch(roleMenuList);
        }
        //更新角色资源权限关系
        if (Objects.nonNull(roleMenuResourceVo.getResourceIdList())) {
            roleResourceService.remove(new LambdaQueryWrapper<RoleResource>()
                        .eq(RoleResource::getRoleId, roleMenuResourceVo.getId()));
            List<RoleResource> roleResourceList = roleMenuResourceVo.getResourceIdList().stream()
                    .map(resourceId -> RoleResource.builder()
                            .roleId(roleMenuResourceVo.getId())
                            .resourceId(resourceId)
                            .build())
                    .collect(Collectors.toList());
            roleResourceService.saveBatch(roleResourceList);
            //重新加载角色资源信息
            filterInvocationSecurityMetadataSource.clearDataSource();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteRoles(List<Integer> roleIdList) {
        // 判断角色下是否有用户
        Integer count = userRoleDao.selectCount(new LambdaQueryWrapper<UserRole>()
                .in(UserRole::getRoleId, roleIdList));
        if (count > 0) {
            throw new BizException("该角色下存在用户,无法删除");
        }
        roleDao.deleteBatchIds(roleIdList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addRole(RoleVo roleVo) {
        Role existRole = roleDao.selectOne(new LambdaQueryWrapper<Role>()
                .select(Role::getId)
                .eq(Role::getRoleName, roleVo.getRoleName()));
        if (Objects.nonNull(existRole)) {
            throw new BizException(StatusCode.ROLENAME_EXIST);
        }
        roleDao.insert(Role.builder()
                .roleName(roleVo.getRoleName())
                .roleLabel(roleVo.getRoleLabel())
                .isDisable(roleVo.getIsDisable())
                .build()
        );
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateRole(RoleVo roleVo) {
        Role existRole = roleDao.selectOne(new LambdaQueryWrapper<Role>()
                .select(Role::getId)
                .eq(Role::getId, roleVo.getId()));
        if (Objects.isNull(existRole)) {
            throw new BizException(StatusCode.ROLE_NOT_EXIST);
        }
        roleDao.updateById(Role.builder()
                .id(roleVo.getId())
                .roleLabel(roleVo.getRoleLabel())
                .roleName(roleVo.getRoleName())
                .isDisable(roleVo.getIsDisable())
                .build()
        );
    }

}

