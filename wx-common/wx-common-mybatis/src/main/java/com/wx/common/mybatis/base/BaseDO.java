package com.wx.common.mybatis.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wx.common.base.IBaseDO;
import com.wx.common.base.IConvert;
import com.wx.common.mybatis.constants.DeleteFlagEnum;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 要求所有表都要有以下通用字段
 * 基础类，如果需要添加额外的注解注释，那就复写这个成员变量再写
 *
 * @author wx
 */
public abstract class BaseDO<T extends Model<?>> extends Model<T> implements IBaseDO, IConvert, Serializable {


    @TableId("id")
    @ApiModelProperty(value = "主键Id")
    private String id;
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @TableField(fill = FieldFill.UPDATE)
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
    @TableField(fill = FieldFill.UPDATE)
    @ApiModelProperty(value = "修改者的id")
    private String updateBy;
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建者的id")
    private String createBy;
    @ApiModelProperty(value = "备注")
    private String remark;
    @JsonIgnore
    @ApiModelProperty(value = "是否被删除，0没删除，1被删除")
    private DeleteFlagEnum deleteFlag;
    @JsonIgnore
    @ApiModelProperty(value = "扩展字段")
    private String extra;

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.id;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String getUpdateBy() {
        return updateBy;
    }

    @Override
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public String getCreateBy() {
        return createBy;
    }

    @Override
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public DeleteFlagEnum getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(DeleteFlagEnum deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @Override
    public String getExtra() {
        return extra;
    }

    @Override
    public void setExtra(String extra) {
        this.extra = extra;
    }
}
