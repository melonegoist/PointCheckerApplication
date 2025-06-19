package ru.melon_egoist.management;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.MBeanExporter;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class JmxConfig {
    @Bean
    public MBeanExporter mBeanExporter(PointCounter pointCounter) {
        MBeanExporter exporter = new MBeanExporter();
        Map<String, Object> beans = new HashMap<>();

        beans.put("ru.melon_egoist.management:type=PointCounter, description=PointCounter", pointCounter);
        exporter.setBeans(beans);

        return exporter;
    }

    @Bean
    public PointCounter pointCounter() {
        return new PointCounter();
    }
}
