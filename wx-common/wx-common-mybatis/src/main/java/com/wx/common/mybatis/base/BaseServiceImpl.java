package com.wx.common.mybatis.base;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.common.mybatis.mapper.WxBaseMapper;

import java.util.Collection;
import java.util.List;

/**
 * @param <M>
 * @param <T>
 */
public abstract class BaseServiceImpl<M extends WxBaseMapper<T>, T> extends ServiceImpl<M, T> implements IBaseService<T> {


    public Page<T> selectPage(Integer current, Integer size, Wrapper<T> queryWrapper) {
        return baseMapper.selectPage(new Page<>(current, size), queryWrapper);
    }

    public T selectOne(String field, Object value) {
        return baseMapper.selectOne(new QueryWrapper<T>().eq(field, value));
    }

    public T selectOne(SFunction<T, ?> field, Object value) {
        return baseMapper.selectOne(new LambdaQueryWrapper<T>().eq(field, value));
    }

    public T selectOne(String field1, Object value1, String field2, Object value2) {
        return baseMapper.selectOne(new QueryWrapper<T>().eq(field1, value1).eq(field2, value2));
    }

    public T selectOne(SFunction<T, ?> field1, Object value1, SFunction<T, ?> field2, Object value2) {
        return baseMapper.selectOne(new LambdaQueryWrapper<T>().eq(field1, value1).eq(field2, value2));
    }

    public Long selectCount() {
        return baseMapper.selectCount(new QueryWrapper<T>());
    }

    public Long selectCount(String field, Object value) {
        return baseMapper.selectCount(new QueryWrapper<T>().eq(field, value));
    }

    public Long selectCount(SFunction<T, ?> field, Object value) {
        return baseMapper.selectCount(new LambdaQueryWrapper<T>().eq(field, value));
    }

    public List<T> selectList() {
        return baseMapper.selectList(new QueryWrapper<>());
    }

    public List<T> selectList(String field, Object value) {
        return baseMapper.selectList(new QueryWrapper<T>().eq(field, value));
    }

    public List<T> selectList(SFunction<T, ?> field, Object value) {
        return baseMapper.selectList(new LambdaQueryWrapper<T>().eq(field, value));
    }

    public List<T> selectList(String field, Collection<?> values) {
        if (CollUtil.isEmpty(values)) {
            return CollUtil.newArrayList();
        }
        return baseMapper.selectList(new QueryWrapper<T>().in(field, values));
    }

    public List<T> selectList(SFunction<T, ?> field, Collection<?> values) {
        if (CollUtil.isEmpty(values)) {
            return CollUtil.newArrayList();
        }
        return baseMapper.selectList(new LambdaQueryWrapper<T>().in(field, values));
    }

    public List<T> selectList(SFunction<T, ?> leField, SFunction<T, ?> geField, Object value) {
        return baseMapper.selectList(new LambdaQueryWrapper<T>().le(leField, value).ge(geField, value));
    }


}
