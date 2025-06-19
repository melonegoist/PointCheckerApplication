package ru.melon_egoist.management;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

@Component
@ManagedResource(objectName = "ru.melon_egoist:type=ClickerDetector")
public class ClickerDetector implements ClickerMBean {

    private final MeterRegistry meterRegistry;

    private final Queue<Long> clickTimestamps = new LinkedList<>();
    private static final int MAX_HISTORY_SIZE = 100;

    @Autowired
    public ClickerDetector(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;

        Gauge.builder("clicker.clickInterval", this, ClickerDetector::getClickInterval)
                .register(meterRegistry);
    }

    @Override
    @ManagedAttribute()
    public double getClickInterval() {
        if (clickTimestamps.size() < 2) {
            System.out.println("Not enough clicks in the history.");
            return 0.0;
        }

        long totalTime = 0;
        Long previousTimestamp = null;

        for (Long timestamp : clickTimestamps) {
            if (previousTimestamp != null) {
                totalTime += timestamp - previousTimestamp;
            }
            previousTimestamp = timestamp;
        }

        return totalTime / (double)  (clickTimestamps.size() - 1) / 1000;
    }

    @Override
    @ManagedOperation()
    public void registerClick() {
        clickTimestamps.add(System.currentTimeMillis());
        while (clickTimestamps.size() > MAX_HISTORY_SIZE) {
            clickTimestamps.poll();
        }
    }

}
