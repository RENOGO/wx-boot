package com.wx.common.base;

import java.util.Date;

/**
 * @Author wuweixin
 * @Date 2023/9/21 21:07
 * @Version 1.0
 * @descritube 基础do接口类，描述了数据库的基础字段
 */
public interface IBaseDO extends IConvert {

    /**
     * 主键id
     * @return
     */
    String getId();

    void setId(String id);

    /**
     * 创建时间
     * @return
     */
    Date getCreateTime();

    void setCreateTime(Date createTime);

    /**
     * 更新时间
     * @return
     */
    Date getUpdateTime();

    void setUpdateTime(Date updateTime);

    /**
     * 更新者
     * @return
     */
    String getUpdateBy();

    void setUpdateBy(String updateBy);

    /**
     * 创建者
     * @return
     */
    String getCreateBy();

    void setCreateBy(String createBy);


    /**
     * 备注
     * @return
     */
    String getRemark();

    void setRemark(String remark);


    /**
     * 扩展字段
     * @return
     */
    String getExtra();

    void setExtra(String extra);
}
