package com.common.token.base.constants;

import com.wx.common.constants.PropertiesPre;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author wuweixin
 * @Date 2024/2/12 19:32
 * @Version 1.0
 * @Descritube
 */
@ConfigurationProperties(prefix = PropertiesPre.TOKEN)
@Data
public class TokenProperties {

    /**
     * 是否开启验证权限
     */
//    private boolean enableCheckPermission;

    /**
     * 缓存用户权限的时间，单位秒
     */
    private long cachePermissionTime = TokenConstants.DEFAULT_PERMISSION_EXPIRE;

}
