package ru.performanceLab.yourNote.scope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SessionScope implements Scope {

    private Map<String, SessionBean> scopedObjects
            = Collections.synchronizedMap(new HashMap<>());
    private Map<String, Runnable> destructionCallbacks
            = Collections.synchronizedMap(new HashMap<>());
    private final Integer TIME_TO_LIVE = 15;


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
//        if (Duration.between(beanStart, now).toMinutes() < TIME_TO_LIVE) {
        if (Duration.between(beanStart, now).toMillis() < 4000) {
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
