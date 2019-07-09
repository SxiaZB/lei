package com.lei.smart.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Order(-1)
public class DynamicDataSourceAspect {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    //    @Before("@annotation(com.ucan.framework.dataSource.DataSource)") //前置通知
    @Before("execution(* com.lei.smart.mapper.*.*(..))")
    public void testBefore(JoinPoint point) {

//        logger.info("begin mapper>>>");
        Class<?> target = point.getTarget().getClass();
        MethodSignature signature = (MethodSignature) point.getSignature();
        // 默认使用目标类型的注解，如果没有则使用其实现接口的注解
        for (Class<?> clazz : target.getInterfaces()) {
            resolveDataSource(clazz, signature.getMethod(),2);
        }
        resolveDataSource(target, signature.getMethod(),2);

    }

    /**
     * 提取目标对象方法注解和类型注解中的数据源标识
     *
     * @param clazz
     * @param method
     */
    private void resolveDataSource(Class<?> clazz, Method method,Integer type) {
        try {
            Class<?>[] types = method.getParameterTypes();
            // 默认使用类型注解
            if (clazz.isAnnotationPresent(DataSource.class)) {
                DataSource source = clazz.getAnnotation(DataSource.class);
                DataSourceTypeManager.setDataSource(source.value());
                if(type.intValue()==1){
                    DataSourceTypeManager.clearDataSource();
                }
            }
            // 方法注解可以覆盖类型注解
            Method m = clazz.getMethod(method.getName(), types);
            if (m != null && m.isAnnotationPresent(DataSource.class)) {
                DataSource source = m.getAnnotation(DataSource.class);
                DataSourceTypeManager.setDataSource(source.value());
                if(type.intValue()==1){
                    DataSourceTypeManager.clearDataSource();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After("execution(* com.lei.smart.mapper.*.*(..))") //后置通知
    public void testAfter(JoinPoint point) {
//        logger.info("end mapper");
        Class<?> target = point.getTarget().getClass();
        MethodSignature signature = (MethodSignature) point.getSignature();
        // 默认使用目标类型的注解，如果没有则使用其实现接口的注解
        for (Class<?> clazz : target.getInterfaces()) {
            resolveDataSource(clazz, signature.getMethod(),1);
        }
        resolveDataSource(target, signature.getMethod(),1);
    }
}
