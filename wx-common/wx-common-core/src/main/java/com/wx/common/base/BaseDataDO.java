package com.wx.common.base;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author wuweixin
 * @Date 2023/9/21 21:07
 * @Version 1.0
 * {@link BaseDO}
 */
@Data
public class BaseDataDO implements IConvert, Serializable {

    /**
     * 主键id
     */
    private String id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 备注
     */
    private String remark;
    /**
     * 扩展字段
     */
    private String extra;


}
