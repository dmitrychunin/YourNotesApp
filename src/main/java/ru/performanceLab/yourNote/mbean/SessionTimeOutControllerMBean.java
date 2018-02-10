package ru.performanceLab.yourNote.mbean;

public interface SessionTimeOutControllerMBean {
    long getAmount();

    void setAmount(long amount);

    String getUnitName();

    void setUnitName(String unitName);
}
