package com.example.webfluxmdc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class LoggingFilterWebFilter implements WebFilter {

    public static final String SURAJ_LOGGING_CONTEXT="suraj-logging-context";
    public static final String SURAJ_SRE_LOGGING_CONTEXT="suraj-sre-logging-context";
    public static final String SURAJ_CUSTOM_LOGGING_CONTEXT="suraj-custom-logging-context";

    @Value("${chassis.logging}")
    private String customLogging;

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        Map<String,Object> loggingMap=new HashMap<>(); //main map

        Map<String,String> loggingSreMap=new HashMap<>(); //sre map
        Map<Integer,String> loggingCustomMap=new HashMap<>(); //sre map


        loggingSreMap.put("context", "context_data "+ System.currentTimeMillis());

        for (int i=0; i< getCustomLogging().length;i++){
            loggingCustomMap.put(i,getCustomLogging()[i]);
        }

        loggingMap.put(SURAJ_SRE_LOGGING_CONTEXT,loggingSreMap);
        loggingMap.put(SURAJ_CUSTOM_LOGGING_CONTEXT,loggingCustomMap);

//        loggingMap.put("customkey1", UUID.randomUUID().toString());
//        loggingMap.put("customkey2", UUID.randomUUID().toString());

        return webFilterChain.filter(serverWebExchange).contextWrite(Context.of(SURAJ_LOGGING_CONTEXT,loggingMap));
    }

    public String[] getCustomLogging(){
        return customLogging.split(",");
    }
}
