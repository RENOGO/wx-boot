package com.wx.common.mybatis.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @author wwx
 * @version 1.0.0
 * @ClassName GeographyMapper.java
 * @Description TODO
 * @createTime 2022年08月24日 10:08:00
 */
public interface LibGeographyMapper {

    /**
     *
     * @param geometryString
     * @return
     */
    String geometry2Polygon(@Param("geometry") String geometryString);

    /**
     *
     * @param geometryString
     * @return
     */
    String geometry2CenterPoint(@Param("geometry") String geometryString);


}
