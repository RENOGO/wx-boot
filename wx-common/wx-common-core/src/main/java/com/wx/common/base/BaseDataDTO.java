package com.wx.common.base;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author wuweixin
 * @Date 2023/9/21 21:07
 * @Version 1.0
 * {@link BaseDataEntity}
 */
@Data
public class BaseDataDTO implements Serializable {

    private String id;
    private Date createTime;
    private Date updateTime;
    private String updateBy;
    private String createBy;
    private String remark;
    private String extra;

    public void convert(Object source) {
        BeanUtil.copyProperties(source, this);
    }

}
