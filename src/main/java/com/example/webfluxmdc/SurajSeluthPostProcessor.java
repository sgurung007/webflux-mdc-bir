package com.example.webfluxmdc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.stereotype.Component;

import java.util.Properties;

public class SurajSeluthPostProcessor implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Properties properties=new Properties();
        properties.put("spring.sleuth.log.slf4j.whitelisted-mdc-keys","principal,suraj");
        properties.put("spring.sleuth.baggage-keys","principal,suraj");
        MutablePropertySources propertySources = environment.getPropertySources();
        propertySources.addFirst(new PropertiesPropertySource("surajSleuthPostProcessor",properties));
    }
}
