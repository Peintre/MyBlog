package com.peintre.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.peintre.enums.ZoneEnum.SHANGHAI;


/**
 * mybatis plus自动填充类
 *
 * @author Peintre
 * @date 2021/06/12
 **/
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // 先判断是否存在该字段
        boolean createTime = metaObject.hasSetter("createTime");
        if (createTime) {
            this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now(ZoneId.of(SHANGHAI.getZone())));
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        boolean createTime = metaObject.hasSetter("updateTime");
        if (createTime) {
            this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now(ZoneId.of(SHANGHAI.getZone())));
        }
    }
}