package ru.performanceLab.yourNote.session.mbean;


import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

public class SessionTimeOutController implements SessionTimeOutControllerMBean {
    private long amount;
    private String unitName;
    private TemporalUnit unit;

    public SessionTimeOutController(long amount, String unitName) {
        this.setAmount(amount);
        this.setUnitName(unitName);
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        unit = ChronoUnit.valueOf(unitName);
        this.unitName = unitName;
    }

    public TemporalUnit getUnit() {
        return ChronoUnit.valueOf(unitName);
    }

}
