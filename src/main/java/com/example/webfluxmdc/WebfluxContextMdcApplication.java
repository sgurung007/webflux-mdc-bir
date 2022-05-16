package com.example.webfluxmdc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.time.Duration;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@SpringBootApplication
public class WebfluxContextMdcApplication {
    private static Logger log = LoggerFactory.getLogger(WebfluxContextMdcApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(WebfluxContextMdcApplication.class, args);
    }


    @Bean
    public RouterFunction<ServerResponse> router() {

        return RouterFunctions
                .route(GET("/test"), serverRequest -> {
                    log.info("log in start");

                    Flux<String> flux = Flux.just("test","test1","test2","test3","test10","test11","test22","test9"
                            ,"test","test1","test2","test3","test10","test11","test22","test9"
                            ,"test","test1","test2","test3","test10","test11","test22","test9",
                                    "test","test1","test2","test3","test10","test11","test22","test9"
                            ,"test","test1","test2","test3","test10","test11","test22","test9"
                            ,"test","test1","test2","test3","test10","test11","test22","test9"
                            ,"test","test1","test2","test3","test10","test11","test22","test9"
                            ,"test","test1","test2","test3","test10","test11","test22","test9"
                            ,"test","test1","test2","test3","test10","test11","test22","test9"
                            ,"test","test1","test2","test3","test10","test11","test22","test9")
                            .delayElements(Duration.ofMillis(10))
                            .doOnNext(s -> log.info("log in doOnNext"))
                            .doOnEach(s->log.info("log in doOnEach"))
                            .map(s -> {
                                log.info("log in map");
                                return s;
                            })
                            .flatMap(s ->
                            {
                                log.info("log in flatMap");
                                return Mono.subscriberContext().map(c -> {
                                    log.info("log in subscriberContext");
                                    return s + " " + c.getOrDefault("context", "no_data");
                                });
                            }).contextWrite(Context.of("customkey1","custmoValue1","customkey2","customValue2","customkey3","customValue3"));
//                            .subscriberContext(Context.of("context", "context_data "+ System.currentTimeMillis()));

                    log.info("log in end");



                    return ServerResponse
                            .ok()
                            .body(flux, String.class);
                });
    }
}

