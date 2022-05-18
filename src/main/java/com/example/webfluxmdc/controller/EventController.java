package com.example.webfluxmdc.controller;

import com.example.webfluxmdc.entity.WebFluxEvent;
import com.example.webfluxmdc.entity.WebFluxOrder;
import com.example.webfluxmdc.stream.AWSKinesisProdcuer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @author bir birs sgrg007@gmail.com
 * @project bir-microservices
 * @since 3/21/2022
 */
@RestController
@RequestMapping("/aws-kinesis-producer")
public class EventController {
    @Value("${originator}")
    private String originator;

    private final AWSKinesisProdcuer awsKinesisProdcuer;

    public EventController(AWSKinesisProdcuer awsKinesisProdcuer) {
        this.awsKinesisProdcuer = awsKinesisProdcuer;
    }

    @PostMapping("/send-event")
    public void sendEvent(@RequestBody WebFluxOrder order){
        awsKinesisProdcuer.sendOrder(new WebFluxEvent(order,"ORDER",originator));
    }
}
