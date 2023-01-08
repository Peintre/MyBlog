package com.peintre.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.peintre.dao.MessageDao;
import com.peintre.dto.MessageBackDTO;
import com.peintre.entity.Message;
import com.peintre.service.MessageService;
import com.peintre.utils.BeanCopyUtils;
import com.peintre.utils.PageUtils;
import com.peintre.vo.ConditionVo;
import com.peintre.vo.PageResultVo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

/**
 * @Program: MyBlog
 * @Description: (tb_message)表服务实现类
 * @Author: Peintre
 * @Date: 2022-12-21 17:07:22
 **/
@Service("messageService")
public class MessageServiceImpl extends ServiceImpl<MessageDao, Message> implements MessageService {
   
    @Autowired
    private MessageDao messageDao;

    @Override
    public PageResultVo<MessageBackDTO> listMessageBackDTO(ConditionVo condition) {
        // 分页查询留言列表
        Page<Message> page = new Page<>(PageUtils.getCurrent(), PageUtils.getSize());
        LambdaQueryWrapper<Message> messageLambdaQueryWrapper = new LambdaQueryWrapper<Message>()
                .like(StringUtils.isNotBlank(condition.getKeywords()), Message::getNickname, condition.getKeywords())
                .eq(Objects.nonNull(condition.getIsReview()), Message::getIsReview, condition.getIsReview())
                .orderByDesc(Message::getId);
        Page<Message> messagePage = messageDao.selectPage(page, messageLambdaQueryWrapper);
        // 转换DTO
        List<MessageBackDTO> messageBackDTOList = BeanCopyUtils.copyList(messagePage.getRecords(), MessageBackDTO.class);
        return new PageResultVo<>(messageBackDTOList, (int) messagePage.getTotal());
    }
}

