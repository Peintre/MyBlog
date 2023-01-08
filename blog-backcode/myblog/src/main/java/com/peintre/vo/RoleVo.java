package com.peintre.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Program: myblog
 * @Description: 角色基本信息
 * @Author: Peintre
 * @Date: 2022/12/4 14:44
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "角色基本信息")
public class RoleVo {
    /**
     * id
     */
    @ApiModelProperty(name = "id", value = "角色id", dataType = "Integer")
    private Integer id;

    /**
     * 标签名
     */
    @NotBlank(message = "角色名不能为空")
    @ApiModelProperty(name = "roleName", value = "角色名", required = true, dataType = "String")
    private String roleName;

    /**
     * 标签名
     */
    @NotBlank(message = "权限标签不能为空")
    @ApiModelProperty(name = "categoryName", value = "标签名", required = true, dataType = "String")
    private String roleLabel;

    /**
     * 是否禁用
     */
    @NotNull(message = "禁用属性不能为空")
    @ApiModelProperty(name = "disabled", value = "禁用", required = true, dataType = "Integer")
    private Integer isDisable;

}
