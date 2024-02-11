package com.wx.common.log.aspect;

import cn.hutool.json.JSONUtil;
import com.wx.common.constants.PropertiesPre;
import com.wx.common.log.annotation.IgnoreLog;
import com.wx.common.utils.RequestIdUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wx
 */
@Aspect
@ConditionalOnProperty(name = PropertiesPre.LOG + ".enable", havingValue = "true", matchIfMissing = true)
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
    private static final ThreadLocal<Long> TIME_THREAD_LOCAL = new ThreadLocal<>();
    //    private static final ThreadLocal<Map<String, Object>> LOG_THREAD_LOCAL = new ThreadLocal<>();
    private static final String LOG_REQUEST = "log_request";
    private static final String LOG_RESPONSE = "log_response";
    private static final String LOG_EXCEPTION = "log_exception";


    /**
     * 配置织入点
     */
    @Pointcut("execution(* com.wx..*.controller..*.*(..))" +
            "||execution(* com.wx..*.controller..*.*(..))")
    public void logPointCut() {
    }

    /**
     * 配置织入点
     */
    @Pointcut("@annotation(org.springframework.web.bind.annotation.ExceptionHandler)")
    public void logPointCutForException() {

    }


    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点
     *
     * @param joinPoint join point for advice
     */
    @Around("logPointCut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        boolean annotationPresent = ((MethodSignature) joinPoint.getSignature()).getMethod().isAnnotationPresent(IgnoreLog.class);
        if (annotationPresent) {
            return joinPoint.proceed();
        }
        TIME_THREAD_LOCAL.set(System.currentTimeMillis());
        handleBefore(joinPoint);
        Object result = joinPoint.proceed();
        handleAfter(result);
        return result;
    }

    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点
     *
     * @param joinPoint join point for advice
     */
    @Around("logPointCutForException()")
    public Object logAroundForException(ProceedingJoinPoint joinPoint) throws Throwable {
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof Exception) {
                handleException((Throwable) arg);
                break;
            }
        }
        Object res = joinPoint.proceed();
        handleAfter(res);
        return res;
    }


    private String handleBefore(ProceedingJoinPoint joinPoint) throws Exception {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("\t").append("request start").append("\n");
        sb.append("请求request-id：").append(RequestIdUtil.getRequestId()).append("\n");
        sb.append("线程id   ").append(Thread.currentThread().getId()).append("\n");
        HttpServletRequest httpServletRequest = attributes.getRequest();
        Map<String, Object> requestParams = getFieldsName(joinPoint, methodSignature, httpServletRequest.getMethod().toLowerCase());
        sb.append("类方法 : ").append(joinPoint.getSignature().getDeclaringTypeName())
                .append(".")
                .append(joinPoint.getSignature().getName()).append("\n")
                .append(httpServletRequest.getMethod()).append(" ").append(httpServletRequest.getRequestURL().toString()).append("\n")
                .append("请求参数 :  ").append(requestParams).append("\n");
        sb.append("\t").append("request end");
        String request = sb.toString();
        logger.info(request);
        return request;
    }

    private String handleAfter(Object res) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("\t").append("response start").append("\n");
        sb.append("线程id   ").append(Thread.currentThread().getId()).append("\n");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            sb.append(request.getMethod()).append(" ").append(request.getRequestURL().toString()).append("\n");
        }
        sb.append("响应-请求request-id：").append(RequestIdUtil.getRequestId()).append("\n");
        sb.append("响应内容：").append(JSONUtil.toJsonStr(res)).append("\n");
        if (TIME_THREAD_LOCAL.get() != null) {
            long mill = System.currentTimeMillis() - TIME_THREAD_LOCAL.get();
            sb.append("方法执行耗时:  ").append(mill).append("毫秒").append("\n");
        }
        sb.append("\t").append("response end");
        String response = sb.toString();
        logger.info(response);
        TIME_THREAD_LOCAL.remove();
        return response;
    }

    private void handleException(Throwable e) {
        logger.error("request-id: " + RequestIdUtil.getRequestId(), e);
    }


    private Map<String, Object> getFieldsName(JoinPoint joinPoint, MethodSignature methodSignature, String requestMethod) throws Exception {
        // 参数值
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return null;
        }
        Class<?>[] classes = new Class[args.length];

        for (int k = 0; k < args.length; k++) {
            if (args[k] == null) {
                continue;
            }
            // 对于接受参数中含有MultipartFile，ServletRequest，ServletResponse类型的特殊处理，我这里是直接返回了null。（如果不对这三种类型判断，会报异常）
            if (args[k] instanceof MultipartFile || args[k] instanceof ServletRequest || args[k] instanceof ServletResponse) {
                return null;
            }
            if (!args[k].getClass().isPrimitive()) {
                // 当方法参数是封装类型
                Class s = args[k].getClass();

                classes[k] = s == null ? args[k].getClass() : s;
            }
        }
        Parameter[] parameters = methodSignature.getMethod().getParameters();
        if (parameters == null || parameters.length == 0) {
            return null;
        }
        // 通过map封装参数和参数值
        HashMap<String, Object> paramMap = new HashMap<>();
        for (int i = 0; i < parameters.length; i++) {
            if ("get".equals(requestMethod) || "delete".equals(requestMethod)) {
                paramMap.put(parameters[i].getName(), args[i]);
                continue;
            }
            if ("post".equals(requestMethod) || "put".equals(requestMethod)) {
                paramMap.put(parameters[i].getName(), JSONUtil.toJsonStr(args[i]));
            }
        }
        return paramMap;
    }


    /**
     * 存储日志的逻辑
     *
     * @param logType
     * @param value
     */
    private void saveLog(String logType, Object value) {

    }


}
