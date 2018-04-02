package com.haitao.apollo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/** * 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候中取出ApplicaitonContext. * */
public class SpringContextUtil implements ApplicationContextAware, DisposableBean {
    private static ApplicationContext applicationContext = null;
    private static Logger logger = LoggerFactory.getLogger(SpringContextUtil.class);
    
    /** * 实现ApplicationContextAware接口, 注入Context到静态变量中. */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        logger.debug("注入ApplicationContext到SpringContextUtils:" + applicationContext);
        if (SpringContextUtil.applicationContext != null) {
            logger.warn("SpringContextUtils中的ApplicationContext被覆盖, 原有ApplicationContext为:"
                        + SpringContextUtil.applicationContext);
        }
        SpringContextUtil.applicationContext = applicationContext;
    }
    
    /** * 实现DisposableBean接口,在Context关闭时清理静态变量. */
    @Override
    public void destroy() throws Exception {
        SpringContextUtil.clear();
    }
    
    /** * 取得存储在静态变量中的ApplicationContext. */
    public static ApplicationContext getApplicationContext() {
        assertContextInjected();
        return applicationContext;
    }
    
   
    
    /** * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型. */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        assertContextInjected();
        return (T)applicationContext.getBean(name);
    }
    
    public static <T> T getBean(String beanId,Class<T> clazz){
        return (T)applicationContext.getBean(beanId, clazz);
    }
    
    /** * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型. */
    public static <T> T getBean(Class<T> requiredType) {
        assertContextInjected();
        return applicationContext.getBean(requiredType);
    }
    
    /** * 清除SpringContextUtils中的ApplicationContext为Null. */
    public static void clear() {
        logger.debug("清除SpringContextUtils中的ApplicationContext:" + applicationContext);
        applicationContext = null;
    }
    
    /** * 检查ApplicationContext不为空. */
    private static void assertContextInjected() {
        if (applicationContext == null) {
            throw new IllegalStateException(
                "applicaitonContext未注入,请在applicationContext.xml中定义SpringContextUtils");
        }
    }
    
    /** * 设置静态变量中的ApplicationContext.junit用 */
    public static void setApplicationContextForJunit(ApplicationContext applicationContext) {
        synchronized(applicationContext){
            SpringContextUtil.applicationContext = applicationContext;
            assertContextInjected();
        }
    }
}