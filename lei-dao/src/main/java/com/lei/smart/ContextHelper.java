package com.lei.smart;



import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ContextHelper implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context)
            throws BeansException {
        ContextHelper.context=context;
    }

    public static StrategyManager getStrategyManager(){
        return context.getBean(StrategyManager.class);
    }

}