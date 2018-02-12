package ru.performanceLab.yourNote.db.dao.cache.mbean;

public class CacheCapacityController implements CacheCapacityControllerMBean {
    private int capacity;

    public CacheCapacityController(int capacity) {
        setCapacity(capacity);
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
