package com.example.webfluxmdc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private HeroService heroService;
    @GetMapping
    public ResponseEntity<Flux<String>> getHellos(){
        log.info("getHellos started!!");
        Flux<String> stringFlux = heroService.getStringFlux()
                .delayElements(Duration.ofMillis(30))
                .doOnNext(p->log.info("reurting p"))
                .contextWrite(Context.of("customkey1", UUID.randomUUID().toString()));
        log.info("after flux!!");
        return new ResponseEntity<>(stringFlux, HttpStatus.OK);
    }
}
