package ru.melon_egoist.management;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(objectName = "")
public class PointCounter implements CounterMBean{

    private int totalPoints = 0;
    private int missedPoints = 0;
    private boolean isLastMissedPoint = false;

    @Override
    @ManagedAttribute
    public int getTotalPoints() {
        return totalPoints;
    }

    @Override
    @ManagedAttribute
    public int getMissedPoints() {
        return missedPoints;
    }

    @Override
    @ManagedOperation
    public void countNewPoint() {
        totalPoints++;
        isLastMissedPoint = false;
    }

    @Override
    @ManagedOperation
    public void countNewMissedPoint() {
        if (!isLastMissedPoint) {
            missedPoints++;
            isLastMissedPoint = true;
        } else {
            sendNotification("You missed point twice in a row!");
            isLastMissedPoint = false;
        }
    }

    public void sendNotification(String message) {
        System.out.println(message);
    }
}
