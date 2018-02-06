package ru.performanceLab.yourNote.scope;

import java.time.LocalTime;

public class SessionBean {
    private String userName;
    private final Integer beanStart = LocalTime.now().toSecondOfDay();
    public Boolean sessionOpen = true;

    public void setSessionOpen(Boolean sessionOpen) {
        this.sessionOpen = sessionOpen;
    }

    public Boolean isOpen() {
        return sessionOpen;
    }

    public String getUserName() {

        return userName;
    }

    public Integer getBeanStart() {
        return beanStart;
    }

    public SessionBean(String name) {
        userName = name;
    }
}
