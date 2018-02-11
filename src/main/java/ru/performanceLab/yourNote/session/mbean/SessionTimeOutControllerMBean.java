package ru.performanceLab.yourNote.session.mbean;

public interface SessionTimeOutControllerMBean {
    long getAmount();

    void setAmount(long amount);

    String getUnitName();

    void setUnitName(String unitName);
}
