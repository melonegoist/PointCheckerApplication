package ru.melon_egoist.management;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.MBeanExporter;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class JmxConfig {
    @Bean
    public MBeanExporter mBeanExporter(PointCounter pointCounter, ClickerDetector clickerDetector) {
        MBeanExporter exporter = new MBeanExporter();
        Map<String, Object> beans = new HashMap<>();

        beans.put("ru.melon_egoist.management:type=PointCounter, description=PointCounter", pointCounter);
        beans.put("ru.melon_egoist.management:type=ClickerDetector, description=ClickerDetector", clickerDetector);
        exporter.setBeans(beans);

        return exporter;
    }

    @Bean
    public PointCounter pointCounter(MeterRegistry meterRegistry) { return new PointCounter(meterRegistry); }

    @Bean
    public ClickerDetector clickerDetector(MeterRegistry meterRegistry) { return new ClickerDetector(meterRegistry); }
}
