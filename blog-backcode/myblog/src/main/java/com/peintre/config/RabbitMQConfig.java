package com.peintre.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.peintre.constant.MQPrefixConst.EMAIL_EXCHANGE;
import static com.peintre.constant.MQPrefixConst.EMAIL_QUEUE;

/**
 * @Program: myblog
 * @Description: RabbitMQ配置类
 * @Author: Peintre
 * @Date: 2023/1/6 15:06
 **/
@Configuration
public class RabbitMQConfig {

    @Bean
    public FanoutExchange emailExchange() {
        return new FanoutExchange(EMAIL_EXCHANGE, true, false);
    }
    @Bean
    public Queue emailQueue() {
        return new Queue(EMAIL_QUEUE, true);
    }
    @Bean
    public Binding bindingEmailDirect() {
        return BindingBuilder.bind(emailQueue()).to(emailExchange());
    }
}
