package com.wx.common.mybatis.handler;

/**
 * @author wwx
 * @version 1.0.0
 * @ClassName MyBatisMetaObjectHandler.java
 * @Description 增改数据时候，注入用户id，会自动填充create_by和update_by字段
 * @createTime 2022年01月27日 16:20:00
 */
public abstract class MyBatisMetaObjectHandler implements MyBatisMetaObjectHandlerInterface {


    @Override
    public String getOperatorId() {
//        String token = Optional
//                .ofNullable(getHeaderValue(SecurityConstants.HEADER_TOKEN))
//                .orElse(getQueryValue(SecurityConstants.QUERY_TOKEN));
//        String appId = Optional
//                .ofNullable(getHeaderValue(SecurityConstants.HEADER_X_CA_KEY))
//                .orElse(getQueryValue(SecurityConstants.QUERY_APP_KEY_ID));
//        if (!StringUtils.isEmpty(token)) {
//            return fillUserId(appId, token);
//        }
//        return appId;
        return null;
    }

    /**
     * 如果是用户登录则会回调这个方法
     *
     * @param appId
     * @param token
     * @return
     */
    @Override
    public String fillUserId(String appId, String token) {
        return null;
    }

//    private static String getHeaderValue(String key) {
//        HttpServletRequest request = LibMyBatisPlusServletUtil.getRequest();
//        if (request == null) {
//            return null;
//        }
//        return LibMyBatisPlusServletUtil.getRequest().getHeader(key);
//    }
//
//    private static String getQueryValue(String key) {
//        HttpServletRequest request = LibMyBatisPlusServletUtil.getRequest();
//        if (request == null) {
//            return null;
//        }
//        String queryString = request.getQueryString();
//        Map<String, String> map = StringUtil.query2Map(queryString);
//        if (map == null) {
//            return null;
//        }
//        return map.get(key);
//    }
}
