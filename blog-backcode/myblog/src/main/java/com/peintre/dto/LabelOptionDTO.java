package com.peintre.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Program: myblog
 * @Description: 权限模块(菜单)标签
 * @Author: Peintre
 * @Date: 2022/12/3 16:58
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LabelOptionDTO {
    /**
     * 权限(菜单)id
     */
    private Integer id;

    /**
     * 权限(菜单)名称
     */
    private String label;

    /**
     * 子权限(子菜单)
     */
    private List<LabelOptionDTO> children;
}
