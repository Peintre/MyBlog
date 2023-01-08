package com.peintre.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.peintre.dto.MessageBackDTO;
import com.peintre.entity.Message;
import com.peintre.vo.ConditionVo;
import com.peintre.vo.PageResultVo;

/**
 * @Program: MyBlog
 * @Description: (tb_message)表服务接口
 * @Author: Peintre
 * @Date: 2022-12-21 17:07:22
 **/
public interface MessageService extends IService<Message> {

    /**
     * (后台)查询留言
     *
     * @param condition 条件
     * @return 留言列表
     */
    PageResultVo<MessageBackDTO> listMessageBackDTO(ConditionVo condition);
}

