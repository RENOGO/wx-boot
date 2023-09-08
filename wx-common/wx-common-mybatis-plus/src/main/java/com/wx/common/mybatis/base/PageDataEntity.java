package com.wx.common.mybatis.base;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.io.Serializable;
import java.util.List;

/**
 * 不使用MyBatis-plus的Page类作为分页响应给外部的返回体
 *
 * @param <T>
 * @author wx
 */
public class PageDataEntity<T> implements Serializable {

    //    @ApiModelProperty(value = "返回列表")
    private List<T> result;
    //    @ApiModelProperty(value = "数据总量")
    private Long totalCount;
    //    @ApiModelProperty(value = "当前页数，第一页是1，不写默认也是1")
    private Long currentPage;
    //    @ApiModelProperty(value = "当前一页显示多少数据，不写默认10条")
    private Long pageSize;

    private Long pages;

    public static <C> PageDataEntity<C> create(IPage<C> page) {
        PageDataEntity<C> pageDataEntity = new PageDataEntity<>();
        pageDataEntity.setResult(page.getRecords());
        pageDataEntity.setPageSize(page.getSize());
        pageDataEntity.setCurrentPage(page.getCurrent());
        pageDataEntity.setTotalCount(page.getTotal());
        pageDataEntity.setPages(page.getPages());
        return pageDataEntity;
    }

    public Long getPages() {
        return pages;
    }

    public void setPages(Long pages) {
        this.pages = pages;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Long currentPage) {
        this.currentPage = currentPage;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }
}
