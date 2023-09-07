package com.wx.common.swagger.model;


import org.springframework.util.StringUtils;

public class Header {

    private String headerName;
    private String description;
    private String type;

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        if (StringUtils.isEmpty(type)) {
            return "String";
        }
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
