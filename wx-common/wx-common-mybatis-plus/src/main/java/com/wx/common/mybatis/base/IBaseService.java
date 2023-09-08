package com.wx.common.mybatis.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author wx
 */
public interface IBaseService<T> extends IService<T> {

    PageDataEntity<T> queryPageData(Page<T> page, T condition);

    PageDataEntity<T> queryPageData(Page<T> page);

    PageDataEntity<T> queryPageData(Integer current, Integer size);

    PageDataEntity<T> queryPageData(Integer current, Integer size, T condition);


    PageDataEntity<T> queryPageDataByWrapper(Page<T> page, Wrapper<T> queryWrapper);

    PageDataEntity<T> queryPageDataByWrapper(Integer current, Integer size, Wrapper<T> queryWrapper);



}
