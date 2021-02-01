package com.kuqi.mall.mybatis.core.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @Author iloveoverfly
 * @Date 2021/1/28 10:54
 **/
public class BaseEntityHandler implements MetaObjectHandler {

    final static String ENTITY_CREATE_TIME = "createTime";
    final static String ENTITY_UPDATE_TIME = "updateTime";
    final static String ENTITY_VERSION = "version";
    final static String ENTITY_DEL_FLAG = "delFlag";
    final static String ENTITY_CREATED_BY = "createdBy";
    final static String ENTITY_UPDATED_BY = "updatedBy";

    @Override
    public void insertFill(MetaObject baseEntity) {

        if (Objects.isNull(baseEntity.getValue(ENTITY_CREATE_TIME))) {
            this.setFieldValByName(ENTITY_CREATE_TIME, LocalDateTime.now(), baseEntity);
        }
        if (Objects.isNull(baseEntity.getValue(ENTITY_UPDATE_TIME))) {
            this.setFieldValByName(ENTITY_UPDATE_TIME, LocalDateTime.now(), baseEntity);
        }
        if (Objects.isNull(baseEntity.getValue(ENTITY_VERSION))) {
            this.setFieldValByName(ENTITY_VERSION, 0, baseEntity);
        }
        if (Objects.isNull(baseEntity.getValue(ENTITY_DEL_FLAG))) {
            this.setFieldValByName(ENTITY_DEL_FLAG, 0, baseEntity);
        }
        if (Objects.isNull(baseEntity.getValue(ENTITY_CREATED_BY))) {
            this.setFieldValByName(ENTITY_CREATED_BY, "system", baseEntity);
        }
        if (Objects.isNull(baseEntity.getValue(ENTITY_UPDATED_BY))) {
            this.setFieldValByName(ENTITY_UPDATED_BY, "system", baseEntity);
        }

    }

    @Override
    public void updateFill(MetaObject baseEntity) {

        if (Objects.isNull(baseEntity.getValue(ENTITY_UPDATE_TIME))) {
            this.setFieldValByName(ENTITY_UPDATE_TIME, LocalDateTime.now(), baseEntity);
        }
        if (Objects.isNull(baseEntity.getValue(ENTITY_UPDATED_BY))) {
            this.setFieldValByName(ENTITY_UPDATED_BY, "system", baseEntity);
        }
    }
}
