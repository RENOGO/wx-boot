package com.wx.common.web.config;

import com.wx.common.constants.PropertiesPre;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author wx
 * @Date 2023/9/8 12:53
 * @Version 1.0
 */
@Component
@ConfigurationProperties(PropertiesPre.WEB)
public class CommonWebProperties {

    private List<String> baseControllerPath;

    private boolean showSysError;


    public List<String> getBaseControllerPath() {
        return baseControllerPath;
    }

    public void setBaseControllerPath(List<String> baseControllerPath) {
        this.baseControllerPath = baseControllerPath;
    }

    public boolean isShowSysError() {
        return showSysError;
    }

    public void setShowSysError(boolean showSysError) {
        this.showSysError = showSysError;
    }
}
