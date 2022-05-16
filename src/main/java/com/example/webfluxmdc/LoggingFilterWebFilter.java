package com.example.webfluxmdc;

import brave.baggage.BaggageField;
import brave.baggage.BaggagePropagation;
import brave.internal.extra.ExtraFactory;
import brave.propagation.ExtraFieldPropagation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.instrument.web.WebFluxSleuthOperators;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;
import reactor.util.context.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class LoggingFilterWebFilter implements WebFilter {

    private final static Logger log= LoggerFactory.getLogger(LoggingFilterWebFilter.class);

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

//        updateMainThreadMdc(loggingMap);
        String path = serverWebExchange.getRequest().getMethod().toString();
        return webFilterChain.filter(serverWebExchange)
                .contextWrite(Context.of(SURAJ_LOGGING_CONTEXT,loggingMap))
                .doOnSubscribe(p->{
                    ExtraFieldPropagation.set("principal", path);
                    ExtraFieldPropagation.set("suraj", UUID.randomUUID().toString());
                });
//                .doOnEach(WebFluxSleuthOperators
//                        .withSpanInScope(SignalType.ON_NEXT,signal ->log.info("Fnma Chassis signal [{}]",signal.get()) ));
//        return webFilterChain.filter(serverWebExchange);
//        return webFilterChain.filter(serverWebExchange);
    }

    public String[] getCustomLogging(){
        return customLogging.split(",");
    }
}
