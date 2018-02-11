package ru.performanceLab.yourNote.db.dao.cache;

import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class Cache {
    private final int CAPACITY = 2;
    private Map<String, Object> cache = new LinkedHashMap<String, Object>(CAPACITY, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(final Map.Entry eldest) {
            return size() > CAPACITY;
        }
    };

    public Object getValue(String key) {
        return cache.get(key);
    }

    public void putValue(String key, Object note) {
        cache.put(key, note);
    }
    /*TODO: autowired (mbean config???)*/
    /*TODO warm caching init*/


}
