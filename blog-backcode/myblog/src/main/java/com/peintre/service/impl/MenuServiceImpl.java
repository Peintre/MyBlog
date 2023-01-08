package com.peintre.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.peintre.config.BizException;
import com.peintre.dao.MenuDao;
import com.peintre.dao.RoleMenuDao;
import com.peintre.dto.LabelOptionDTO;
import com.peintre.dto.MenuDTO;
import com.peintre.dto.UserMenuDTO;
import com.peintre.entity.Menu;
import com.peintre.entity.RoleMenu;
import com.peintre.service.MenuService;
import com.peintre.utils.BeanCopyUtils;
import com.peintre.utils.UserUtils;
import com.peintre.vo.ConditionVo;
import com.peintre.vo.MenuVo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import static com.peintre.constant.CommonConst.*;

/**
 * @Program: MyBlog
 * @Description: (tb_menu)表服务实现类
 * @Author: Peintre
 * @Date: 2022-11-10 13:16:16
 **/
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuDao, Menu> implements MenuService {

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private RoleMenuDao roleMenuDao;


    /**
     * @param
     * @return: java.util.List<com.peintre.dto.UserMenuDTO> 菜单类
     * @description: 查询当前用户具有的菜单信息
     */
    @Override
    public List<UserMenuDTO> listUserMenus() {
        // 查询用户具有的菜单信息
        List<Menu> menuList = menuDao.listMenusByUserInfoId(UserUtils.getLoginUser().getUserInfoId());
        //将菜单格式转化为前端格式
        return converMenu(menuList);
    }

    /**
     * @param menuList 所有菜单信息
     * @return: java.util.List<com.peintre.entity.Menu>
     * @description: 获取目录列表
     */
    private List<Menu> listCatalog(List<Menu> menuList) {
        return menuList.stream()
                .filter(item -> Objects.isNull(item.getParentId()))
                .sorted(Comparator.comparing(Menu::getOrderNum))
                .collect(Collectors.toList());
    }

    /**
     * 获取目录下菜单列表
     *
     * @param menuList 菜单列表
     * @return 目录下的菜单列表
     */
    private Map<Integer, List<Menu>> getMenuMap(List<Menu> menuList) {
        return menuList.stream()
                .filter(item -> Objects.nonNull(item.getParentId()))
                .collect(Collectors.groupingBy(Menu::getParentId));
    }


    /**
     * @param menuList 菜单列表
     * @return: java.util.List<com.peintre.dto.UserMenuDTO>
     * @description: 将结果转换为前端菜单格式
     */
    private List<UserMenuDTO> converMenu(List<Menu> menuList) {
        //获取菜单目录
        List<Menu> menuListTop = listCatalog(menuList);
        //获取子菜单组
        Map<Integer, List<Menu>> childrenMap = getMenuMap(menuList);
        //返回菜单list
        UserMenuDTO userMenuDTO = new UserMenuDTO();
        if(menuListTop.size()>0){
            //递归排列菜单
            getMenuList(userMenuDTO,menuListTop,childrenMap,INDEX_NAME,menuListTop);
        }
        return userMenuDTO.getChildMenuList();
    }

    /**
     * @param userMenuDTO 用户菜单类
     * @param menuListChildTop 子菜单列表
     * @param childrenMap 子菜单组
     * @param parentName 父菜单名称
     * @return: void
     * @description: 递归排列所有菜单
     */
    private void getMenuList(UserMenuDTO userMenuDTO, List<Menu> menuListChildTop, Map<Integer, List<Menu>> childrenMap,String parentName,List<Menu> menuListTop) {
        List<UserMenuDTO> childList = new ArrayList<>();
        //遍历顶层菜单
        for(Menu item:menuListChildTop){
            UserMenuDTO userMenuTopDTO = BeanCopyUtils.copyObject(item, UserMenuDTO.class);
            userMenuTopDTO.setHidden(item.getIsHidden().equals(TRUE));
            //最顶层菜单(无子菜单)设置父菜单名
            if(INDEX_NAME.equals(parentName) && (!childrenMap.containsKey(item.getId()))){
                for(Menu menu:menuListTop){
                    if(item.getId().equals(menu.getId())){
                        userMenuTopDTO.setParentName(INDEX_NAME);
                    }
                }
            }else if(!childrenMap.containsKey(item.getId())){
                userMenuTopDTO.setParentName(parentName);
            }
            //存在子菜单
            if(childrenMap.containsKey(item.getId())){
                getMenuList(userMenuTopDTO,childrenMap.get(item.getId()),childrenMap,item.getName(),menuListTop);
            }
            childList.add(userMenuTopDTO);
            userMenuDTO.setChildMenuList(childList);
        }
    }

    /**
     * @param
     * @return: java.util.List<com.peintre.dto.LabelOptionDTO>
     * @description: 查询所有的菜单
     */
    @Override
    public List<LabelOptionDTO> listMenuOptions() {
        // 查询菜单数据
        List<Menu> menuList = menuDao.selectList(new LambdaQueryWrapper<Menu>()
                .select(Menu::getId, Menu::getName, Menu::getParentId, Menu::getOrderNum));
        // 获取目录列表
        List<Menu> catalogList = listCatalog(menuList);
        // 获取目录下的子菜单
        Map<Integer, List<Menu>> childrenMap = getMenuMap(menuList);
        // 组装目录菜单数据
        return catalogList.stream().map(item -> {
            // 获取目录下的菜单排序
            List<LabelOptionDTO> list = new ArrayList<>();
            List<Menu> children = childrenMap.get(item.getId());
            if (CollectionUtils.isNotEmpty(children)) {
                list = children.stream()
                        .sorted(Comparator.comparing(Menu::getOrderNum))
                        .map(menu -> LabelOptionDTO.builder()
                                .id(menu.getId())
                                .label(menu.getName())
                                .build())
                        .collect(Collectors.toList());
            }
            return LabelOptionDTO.builder()
                    .id(item.getId())
                    .label(item.getName())
                    .children(list)
                    .build();
        }).collect(Collectors.toList());
    }


    @Override
    public List<MenuDTO> listMenus(ConditionVo conditionVO) {
        // 查询菜单数据
        List<Menu> menuList = menuDao.selectList(new LambdaQueryWrapper<Menu>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), Menu::getName, conditionVO.getKeywords()));
        // 获取目录列表
        List<Menu> catalogList = listCatalog(menuList);
        // 获取目录下的子菜单
        Map<Integer, List<Menu>> childrenMap = getMenuMap(menuList);
        // 组装目录菜单数据
        List<MenuDTO> menuDTOList = catalogList.stream().map(item -> {
            MenuDTO menuDTO = BeanCopyUtils.copyObject(item, MenuDTO.class);
            // 获取目录下的菜单排序
            List<MenuDTO> list = BeanCopyUtils.copyList(childrenMap.get(item.getId()), MenuDTO.class).stream()
                    .sorted(Comparator.comparing(MenuDTO::getOrderNum))
                    .collect(Collectors.toList());
            menuDTO.setChildren(list);
            childrenMap.remove(item.getId());
            return menuDTO;
        }).sorted(Comparator.comparing(MenuDTO::getOrderNum)).collect(Collectors.toList());
        // 若还有菜单未取出则拼接
        if (CollectionUtils.isNotEmpty(childrenMap)) {
            List<Menu> childrenList = new ArrayList<>();
            childrenMap.values().forEach(childrenList::addAll);
            List<MenuDTO> childrenDTOList = childrenList.stream()
                    .map(item -> BeanCopyUtils.copyObject(item, MenuDTO.class))
                    .sorted(Comparator.comparing(MenuDTO::getOrderNum))
                    .collect(Collectors.toList());
            menuDTOList.addAll(childrenDTOList);
        }
        return menuDTOList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateMenu(MenuVo menuVO) {
        Menu menu = BeanCopyUtils.copyObject(menuVO, Menu.class);
        this.saveOrUpdate(menu);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteMenu(Integer menuId) {
        // 查询是否有角色关联
        Integer count = roleMenuDao.selectCount(new LambdaQueryWrapper<RoleMenu>()
                .eq(RoleMenu::getMenuId, menuId));
        if (count > 0) {
            throw new BizException("菜单下有角色关联");
        }
        // 查询子菜单
        List<Integer> menuIdList = menuDao.selectList(new LambdaQueryWrapper<Menu>()
                .select(Menu::getId)
                .eq(Menu::getParentId, menuId))
                .stream()
                .map(Menu::getId)
                .collect(Collectors.toList());
        menuIdList.add(menuId);
        menuDao.deleteBatchIds(menuIdList);
    }

}


