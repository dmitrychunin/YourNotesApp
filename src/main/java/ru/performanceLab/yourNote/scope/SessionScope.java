package ru.performanceLab.yourNote.scope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.data.util.Pair;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SessionScope implements Scope {

    private Map<String, Pair<LocalTime,Object>> scopedObjects
            = Collections.synchronizedMap(new HashMap<>());
    private Map<String, Runnable> destructionCallbacks
            = Collections.synchronizedMap(new HashMap<>());


    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        //не содержит - создаем
        //содержит но больше 15 - удаляем
        //содержит но меньше 15 - отдаем
        if(!scopedObjects.containsKey(name)) {
            Pair<LocalTime, Object> pair = Pair.of(LocalTime.now(), objectFactory.getObject());
            scopedObjects.put(name, pair);
            return pair.getSecond();
        }

        LocalTime beanStart = scopedObjects.get(name).getFirst();
        LocalTime now = LocalTime.now();
//        if (Duration.between(beanStart, now).toMinutes() < 15) {
        if (Duration.between(beanStart, now).toMillis() < 4000) {
            return scopedObjects.get(name).getSecond();

        } else {
            scopedObjects.remove(name);
            return null;
        }
    }

    @Override
    public Object remove(String name) {
        destructionCallbacks.remove(name);
        return scopedObjects.remove(name);
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
        destructionCallbacks.put(name, callback);
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
