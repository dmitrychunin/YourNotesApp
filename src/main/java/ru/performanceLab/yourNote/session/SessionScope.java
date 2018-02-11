package ru.performanceLab.yourNote.session;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import ru.performanceLab.yourNote.session.mbean.SessionTimeOutController;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SessionScope implements Scope {

    private final SessionTimeOutController sessionTimeOutController;
    private Map<String, SessionBean> scopedObjects
            = Collections.synchronizedMap(new HashMap<>());
    private Map<String, Runnable> destructionCallbacks
            = Collections.synchronizedMap(new HashMap<>());

    public SessionScope() {
        sessionTimeOutController = new SessionTimeOutController(15, ChronoUnit.MINUTES.name());

        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        try {
            server.registerMBean(sessionTimeOutController, new ObjectName("appControllers", "name", "sessionTimeOut"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public SessionBean get(String userName, ObjectFactory<?> objectFactory) {
        //не содержит - создаем
        //содержит но больше 15 - удаляем
        //содержит но меньше 15 - отдаем
        SessionBean sessionBean = (SessionBean) objectFactory.getObject();
        userName = sessionBean.getUserName();
        if (!scopedObjects.containsKey(userName)) {
            scopedObjects.put(userName, sessionBean);
            return sessionBean;
        }

        LocalTime beanStart = LocalTime.ofSecondOfDay(scopedObjects.get(userName).getBeanStart());
        LocalTime now = LocalTime.now();
        if (Duration.between(beanStart, now).compareTo(
                Duration.of(sessionTimeOutController.getAmount(), sessionTimeOutController.getUnit())) < 0) {
            return scopedObjects.get(userName);
        } else {
            SessionBean removedBean = scopedObjects.remove(userName);
            removedBean.setSessionOpen(false);
            return removedBean;
        }
    }

    @Override
    public Object remove(String userName) {
        destructionCallbacks.remove(userName);
        return scopedObjects.remove(userName);
    }

    @Override
    public void registerDestructionCallback(String userName, Runnable callback) {
        destructionCallbacks.put(userName, callback);
    }

    @Override
    public Object resolveContextualObject(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getConversationId() {
        throw new UnsupportedOperationException();
    }
}
