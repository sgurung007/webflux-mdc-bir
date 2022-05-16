package com.example.webfluxmdc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.context.Context;

import java.time.Duration;
import java.util.UUID;

@RestController
@RequestMapping("/hero")
public class HeroController {
    private final static Logger log= LoggerFactory.getLogger(HeroController.class);
    @GetMapping
    public ResponseEntity<Flux<String>> getHellos(){
        log.info("getHellos started!!");
        Flux<String> stringFlux = Flux.just("Hello1", "Hello2", "Hello3", "Hello4", "Hello5", "Hello6", "Hello7", "Hello8", "Hello9",
                        "Hello10", "Hello111", "Hello11", "Hello13", "Hello1", "Hello12", "Hello15")
                .delayElements(Duration.ofMillis(30))
                .doOnNext(p->log.info("reurting p"))
                .contextWrite(Context.of("customkey1", UUID.randomUUID().toString()));
        log.info("after flux!!");
        return new ResponseEntity<>(stringFlux, HttpStatus.OK);
    }
}
