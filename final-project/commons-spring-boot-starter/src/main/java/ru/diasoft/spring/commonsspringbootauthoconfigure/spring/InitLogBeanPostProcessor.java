package ru.diasoft.spring.commonsspringbootauthoconfigure.spring;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class InitLogBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(InitLog.class)) {
            log.info("Init log bean '{}'",  beanName);
        }
        return bean;
    }
}
