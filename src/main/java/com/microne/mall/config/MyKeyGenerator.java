package com.microne.mall.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * redis缓存键生成器
 */
@Configuration
public class MyKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        StringBuilder methodName = new StringBuilder(method.getName());
        methodName.append(":");
        for (Object param : params) {
            methodName.append(param.toString()).append(":");
        }
        methodName.append(":");
        methodName.append(target.toString());
        return methodName;
    }
}
