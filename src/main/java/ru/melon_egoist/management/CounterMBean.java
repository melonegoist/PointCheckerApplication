package ru.melon_egoist.management;

public interface CounterMBean {
    int getTotalPoints();
    int getMissedPoints();
    void countNewPoint();
    void countNewMissedPoint();
}
