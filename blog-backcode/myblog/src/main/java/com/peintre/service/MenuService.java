package com.peintre.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.peintre.dto.LabelOptionDTO;
import com.peintre.dto.MenuDTO;
import com.peintre.dto.UserMenuDTO;
import com.peintre.entity.Menu;
import com.peintre.vo.ConditionVo;
import com.peintre.vo.MenuVo;

import java.util.List;


/**
 * @Program: MyBlog
 * @Description: (tb_menu)表服务接口
 * @Author: Peintre
 * @Date: 2022-11-10 13:16:16
 **/
public interface MenuService extends IService<Menu> {

    /**
     * 查看当前用户菜单
     *
     * @return 菜单列表
     */
    List<UserMenuDTO> listUserMenus();

    /**
     * 获取所有菜单(返回树形式)
     *
     * @return 菜单标签列表
     */
    List<LabelOptionDTO> listMenuOptions();

    /**
     * 获取菜单列表
     *
     * @return void
     */
    List<MenuDTO> listMenus(ConditionVo conditionVO);

    /**
     * 新增/修改菜单
     *
     * @param menuVO 菜单信息
     */
    void saveOrUpdateMenu(MenuVo menuVO);

    /**
     * 删除菜单
     *
     * @param menuId 菜单ID
     */
    void deleteMenu(Integer menuId);
}

