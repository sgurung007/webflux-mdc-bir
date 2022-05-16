package com.example.webfluxmdc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.Properties;

public class SleuthPropertiesListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    @Value("${chassis.logging}")
    private String chassisLogging;

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment environment = event.getEnvironment();
        Properties prop = new Properties();
        Properties prop1 = new Properties();
        System.out.println(chassisLogging + " snow!");
        prop.put("spring.sleuth.baggage-keys", "principal,suraj");
        prop1.put("spring.sleuth.log.slf4j.whitelisted-mdc-keys", "principal,suraj");
        environment.getPropertySources().addLast(new PropertiesPropertySource("myProp", prop));
        environment.getPropertySources().addLast(new PropertiesPropertySource("myProp1", prop1));
    }
}
