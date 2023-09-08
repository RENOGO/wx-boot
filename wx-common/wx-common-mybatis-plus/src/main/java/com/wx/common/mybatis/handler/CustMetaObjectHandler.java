package com.wx.common.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * 自动填充
 *
 * @author wx
 */
@Component
public class CustMetaObjectHandler implements MetaObjectHandler {

    @Autowired(required = false)
    private MyBatisMetaObjectHandler dataSourceHandler;

    @Override
    public void insertFill(MetaObject metaObject) {
        if (dataSourceHandler != null) {
            String userId = dataSourceHandler.getOperatorId();
            if (!StringUtils.isEmpty(userId)) {
                this.strictInsertFill(metaObject, "createBy", () -> userId, String.class);
            }
        }
        this.strictInsertFill(metaObject, "createTime", Date::new, Date.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (dataSourceHandler != null) {
            String userId = dataSourceHandler.getOperatorId();
            if (!StringUtils.isEmpty(userId)) {
                this.strictInsertFill(metaObject, "updateBy", () -> userId, String.class);
            }
        }
        this.strictUpdateFill(metaObject, "updateTime", Date::new, Date.class);
    }
}