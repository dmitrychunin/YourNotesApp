package ru.performanceLab.yourNote.db.dao.cache;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class Cache {
    /*TODO: replace map by more acceptable limited collection?*/
    private Map<String, Object> cache = new HashMap<>();

    public Object getNote(String key) {
        return cache.get(key);
    }

    public void addNote(String key, Object note) {
        cache.put(key, note);
    }
    /*TODO: autowired (mbean config???)*/
    /*TODO warm caching init*/


}
