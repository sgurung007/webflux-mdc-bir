package com.example.webfluxmdc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class HeroService {
    private final static Logger log= LoggerFactory.getLogger(HeroService.class);

    public Flux<String> getStringFlux(){
        return Flux.just("Hello1", "Hello2", "Hello3", "Hello4", "Hello5", "Hello6", "Hello7", "Hello8", "Hello9",
                "Hello10", "Hello111", "Hello11", "Hello13", "Hello1", "Hello12", "Hello15").doOnNext(p->log.info("HeroService log!!"));
    }
}
