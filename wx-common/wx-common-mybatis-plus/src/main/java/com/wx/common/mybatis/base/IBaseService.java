package com.wx.common.mybatis.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author wx
 */
public interface IBaseService<T> extends IService<T> {

    Page<T> queryPageData(Page<T> page, T condition);

    Page<T> queryPageData(Page<T> page);

    Page<T> queryPageData(Integer current, Integer size);

    Page<T> queryPageData(Integer current, Integer size, T condition);


    Page<T> queryPageDataByWrapper(Page<T> page, Wrapper<T> queryWrapper);

    Page<T> queryPageDataByWrapper(Integer current, Integer size, Wrapper<T> queryWrapper);



}
