package com.wx.common.web.exceptions;

import com.wx.common.base.BaseListChain;
import com.wx.common.web.annotation.ExceptionOrder;
import com.wx.common.web.exceptions.handler.BaseWebResponseExceptionHandler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @Author wuweixin
 * @Date 2023/9/18 13:43
 * @Version 1.0
 */
@Component

public class ExceptionChain
        extends BaseListChain<BaseWebResponseExceptionHandler, ExceptionParameter>
        implements ApplicationContextAware {


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, BaseWebResponseExceptionHandler> appImplementations = applicationContext.getBeansOfType(BaseWebResponseExceptionHandler.class);
        List<BaseWebResponseExceptionHandler> handler = new ArrayList<>(appImplementations.values());
        handler.sort(new Comparator<BaseWebResponseExceptionHandler>() {
            @Override
            public int compare(BaseWebResponseExceptionHandler handler1, BaseWebResponseExceptionHandler handler2) {
                int order1 = getOrder(handler1.getClass());
                int order2 = getOrder(handler2.getClass());
                return Integer.compare(order1, order2);
            }

            public <C extends BaseWebResponseExceptionHandler> int getOrder(Class<C> c) {
                int r = 0;
                if (c.isAnnotationPresent(ExceptionOrder.class)) {
                    r = c.getAnnotation(ExceptionOrder.class).order();
                }
                return r;
            }
        });
        for (BaseWebResponseExceptionHandler baseWebResponseExceptionHandler : handler) {
            addHandler(baseWebResponseExceptionHandler);
        }
    }


}
