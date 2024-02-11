package com.wx.common.security.utils;


import cn.hutool.json.JSONUtil;
import com.wx.common.web.WebResponse;

import javax.servlet.ServletResponse;
import java.io.PrintWriter;

/**
 *
 * @author wuweixin
 */
public class ResponseUtils {



    /**
     * 使用response输出JSON
     *
     * @param response
     */
    public static void out(ServletResponse response) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out = response.getWriter();
            out.println("输出");
        } catch (Exception e) {

        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }


    /**
     * 使用response输出JSON
     *
     * @param response
     * @param result
     */
    public static void out(ServletResponse response, WebResponse<?> result) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out = response.getWriter();
            out.println(JSONUtil.toJsonStr(result));
        } catch (Exception e) {

        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }


}
