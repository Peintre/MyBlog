package com.peintre.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @Program: myblog
 * @Description:
 * @Author: Peintre 相册信息
 * @Date: 2022/12/27 17:48
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "相册信息")
public class PhotoAlbumVo {
    /**
     * 相册id
     */
    @ApiModelProperty(name = "id", value = "相册id", required = true, dataType = "Integer")
    private Integer id;

    /**
     * 相册名
     */
    @NotBlank(message = "相册名不能为空")
    @ApiModelProperty(name = "albumName", value = "相册名", required = true, dataType = "String")
    private String albumName;

    /**
     * 相册描述
     */
    @ApiModelProperty(name = "albumDesc", value = "相册描述", dataType = "String")
    private String albumDesc;

    /**
     * 相册封面
     */
    @NotBlank(message = "相册封面不能为空")
    @ApiModelProperty(name = "albumCover", value = "相册封面", required = true, dataType = "String")
    private String albumCover;

    /**
     * 状态值 1公开 2私密
     */
    @ApiModelProperty(name = "status", value = "状态值", required = true, dataType = "Integer")
    private Integer status;
}
