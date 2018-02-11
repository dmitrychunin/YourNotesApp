package ru.performanceLab.yourNote.service.dao;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class CachedResultAnnotatedBeanPostProcessor implements BeanPostProcessor {
    private Map<String, ClassInfoContainer> originalAnnotatedClasses = new HashMap<>();

    @Autowired
    private Cache cache;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        ClassInfoContainer classInfo = new ClassInfoContainer();
        classInfo.setClassObj(beanClass);

        for (Method method : beanClass.getMethods()) {
            CachedResult cachedResult = method.getAnnotation(CachedResult.class);
            if (cachedResult != null) {
                classInfo.addMethod(method);
            }
        }

        if (!classInfo.getMethods().isEmpty()) {
            originalAnnotatedClasses.put(beanName, classInfo);
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, String beanName) throws BeansException {
        ClassInfoContainer classInfo = originalAnnotatedClasses.get(beanName);
        if (classInfo == null) {
            return bean;
        }

        InvocationHandler noteDaoCacheProxy = (Object proxy, Method method, Object[] args) -> {
            /*
            TODO:remove duplicated annotation in interface
            interface method: method doesn't equals overriden class method: classInfo.getMethods().get(index)
            */
            if (method.getAnnotation(CachedResult.class) == null) {
                return method.invoke(bean, args);
            }

            String cacheKey = Arrays.toString(args);
            Object result = cache.getNote(cacheKey);

            if (result == null) {
                result = method.invoke(bean, args);
                cache.addNote(cacheKey, result);
            }
            return result;
        };

        Class originalClass = classInfo.getClassObj();
        Object proxyBean = Proxy.newProxyInstance(
                originalClass.getClassLoader(),
                originalClass.getInterfaces(),
                noteDaoCacheProxy);
        return proxyBean;
    }
}
