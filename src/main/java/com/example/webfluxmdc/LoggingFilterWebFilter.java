package com.example.webfluxmdc;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
public class LoggingFilterWebFilter implements WebFilter {

    public static final String SURAJ_LOGGING_CONTEXT="suraj-logging-context";



    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        Map<String,String> loggingMap=new HashMap<>();
        loggingMap.put("context", "context_data "+ System.currentTimeMillis());

        return webFilterChain.filter(serverWebExchange).subscriberContext(context -> {
            return context.put(SURAJ_LOGGING_CONTEXT,loggingMap);
        });
    }
}
