package ru.performanceLab.yourNote.db.dao.cache;

import org.springframework.stereotype.Service;
import ru.performanceLab.yourNote.db.dao.cache.mbean.CacheCapacityController;

import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.*;

@Service
public class Cache {
    private CacheCapacityController capacityController;
    private Map<String, Object> cache;
    private final Integer STARTUP_CACHE_CAPACITY = 2;

    public Cache() throws Exception {
        capacityController = new CacheCapacityController(STARTUP_CACHE_CAPACITY);
        ManagementFactory.getPlatformMBeanServer().registerMBean(capacityController, new ObjectName("appControllers", "name", "cacheCapacity"));
        cache = new LinkedHashMap<>(capacityController.getCapacity(), 0.75f, true);
    }

    public Object getValue(String key) {
        return cache.get(key);
    }

    public void putValue(String key, Object note) {
        if(isCapacitySmallerThenSize()) {
            resizeCache();
        }
        cache.put(key, note);
    }

    private boolean isCapacitySmallerThenSize() {
        return cache.size() > capacityController.getCapacity() - 1;
    }

    private void resizeCache() {
        List<String> keyList = new ArrayList<>(cache.keySet());
        for (int i = 0; cache.size() > capacityController.getCapacity() - 1; i++) {
            cache.remove(keyList.get(i));
        }
    }
    /*TODO warm caching init*/
}
