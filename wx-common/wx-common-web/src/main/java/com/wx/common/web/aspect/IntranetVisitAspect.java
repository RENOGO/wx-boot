package com.wx.common.web.aspect;

import cn.hutool.extra.servlet.ServletUtil;
import com.wx.common.utils.IpUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wwx
 * @version 1.0.0
 * @ClassName IntranetVisitAspect.java
 * @Description TODO
 * @createTime 2022年11月04日 17:56:00
 */
@Aspect
public class IntranetVisitAspect {

    /**
     * 配置织入点
     */
    @Pointcut("@annotation(com.wx.common.web.annotation.IntranetVisit)")
    public void pointCut() {

    }

    /**
     * 方法调用前会触发
     */
    @Before("pointCut()")
    public void logBefore() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        //这个方法拿到的是真实ip，代理服务器转发的时候一般会在请求头添加真正请求源的ip，以追踪源客户端，而getRemoteAddr不一定拿得到真实的ip，可能拿到代理服务器的
        String ip = ServletUtil.getClientIP(request);
        boolean internal = IpUtil.internalIp(ip);
        if (internal) {
            return;
        }
        throw new RuntimeException("当前接口只允许内网访问");
    }
}
