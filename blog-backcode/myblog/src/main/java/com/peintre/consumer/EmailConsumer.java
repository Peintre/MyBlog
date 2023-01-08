package com.peintre.consumer;

import com.peintre.config.BizException;
import com.peintre.dto.blog.EmailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import static com.peintre.constant.MQPrefixConst.EMAIL_QUEUE;

/**
 * @Program: myblog
 * @Description: email消费者
 * @Author: Peintre
 * @Date: 2023/1/6 15:46
 **/
@Slf4j
@Component
@RabbitListener(queues = EMAIL_QUEUE)
public class EmailConsumer {
    /**
     * 邮箱号
     */
    @Value("${spring.mail.username}")
    private String email;

    @Autowired
    private JavaMailSender javaMailSender;

    @RabbitHandler
    public void process(EmailDTO emailDTO) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(email);
            message.setTo(emailDTO.getEmail());
            message.setSubject(emailDTO.getSubject());
            message.setText(emailDTO.getContent());
            javaMailSender.send(message);
            log.info("邮件发送成功");
        }catch (Exception e){
            log.error(e.getMessage());
            throw new BizException("邮件发送失败");
        }
    }
}
