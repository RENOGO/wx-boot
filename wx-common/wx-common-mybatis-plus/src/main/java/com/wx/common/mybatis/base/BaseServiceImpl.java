package com.wx.common.mybatis.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @param <M>
 * @param <T>
 */
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements IBaseService<T> {


    /**
     * 带条件的分页查询
     *
     * @param page
     * @param condition
     * @return
     */
    @Override
    public Page<T> queryPageData(Page<T> page, T condition) {
        return page(page, new QueryWrapper<>(condition));
    }

    /**
     * 不带条件的分页查询
     *
     * @param page
     * @return
     */
    @Override
    public Page<T> queryPageData(Page<T> page) {
        return page(page);
    }


    /**
     * 不带条件的分页查询
     *
     * @param current
     * @param size
     * @return
     */
    @Override
    public Page<T> queryPageData(Integer current, Integer size) {
        Page<T> page = new Page<>(current, size);
        return queryPageData(page);
    }

    /**
     * 不带条件的分页查询
     *
     * @param current
     * @param size
     * @return
     */
    @Override
    public Page<T> queryPageData(Integer current, Integer size, T condition) {
        Page<T> page = new Page<>(current, size);
        return queryPageData(page, condition);
    }

    @Override
    public Page<T> queryPageDataByWrapper(Page<T> page, Wrapper<T> queryWrapper) {
        return page(page, queryWrapper);
    }

    @Override
    public Page<T> queryPageDataByWrapper(Integer current, Integer size, Wrapper<T> queryWrapper) {
        Page<T> page = new Page<>(current, size);
        return page(page, queryWrapper);
    }
}
