package com.peintre.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.peintre.dto.UserBackDTO;
import com.peintre.vo.ConditionVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.peintre.entity.UserAuth;

import java.util.List;


/**
 * @Program: MyBlog
 * @Description: (tb_user_auth)表数据访问层
 * @Author: Peintre
 * @Date: 2022-11-09 16:22:36
 **/
@Repository
public interface UserAuthDao extends BaseMapper<UserAuth> {

    /**
     * 查询后台用户数量
     *
     * @param condition 条件
     * @return 用户数量
     */
    Integer countUser(@Param("condition") ConditionVo condition);

    /**
     * 查询后台用户列表
     *
     * @param current   页码
     * @param size      大小
     * @param condition 条件
     * @return {@link List <UserBackDTO>} 用户列表
     */
    List<UserBackDTO> listUsers(@Param("current") Long current, @Param("size") Long size, @Param("condition") ConditionVo condition);


}

