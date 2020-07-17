package com.coolwen.experimentplatformv2.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 解决shiro中service无法注入
 *
 * @author CoolWen
 * @version 2020-07-17 23:47
 */
@Component
public class SpringBeanFactoryUtils implements ApplicationContextAware {
    private static ApplicationContext context = null;

    public static <T> T getBean(Class<T> type) {
        return context.getBean(type);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringBeanFactoryUtils.context == null) {
            SpringBeanFactoryUtils.context = applicationContext;
        }
    }
}
