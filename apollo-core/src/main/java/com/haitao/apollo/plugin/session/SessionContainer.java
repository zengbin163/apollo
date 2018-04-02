package com.haitao.apollo.plugin.session;

public class SessionContainer {
    private static final ThreadLocal<Session> sessionThreadLocal = new ThreadLocal<Session>();
    
    public static Session getSession() {
        return sessionThreadLocal.get();
    }
    
    public static void setSession(Session session) {
        sessionThreadLocal.set(session);
    }
    
    public static void clear() {
        sessionThreadLocal.set(null);
    }
}