package com.example.webfluxmdc.entity;


import java.util.UUID;

/**
 * @author bir birs sgrg007@gmail.com
 * @project bir-microservices
 * @since 3/21/2022
 */

public class WebFluxEvent {
    private UUID id;

    private WebFluxOrder order;

    private String type;

    private String originator;

    public WebFluxEvent() {
    }

    public WebFluxEvent(WebFluxOrder order, String type, String originator) {
        this.order = order;
        this.type = type;
        this.originator = originator;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public WebFluxOrder getOrder() {
        return order;
    }

    public void setOrder(WebFluxOrder order) {
        this.order = order;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOriginator() {
        return originator;
    }

    public void setOriginator(String originator) {
        this.originator = originator;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", order=" + order +
                ", type='" + type + '\'' +
                ", originator='" + originator + '\'' +
                '}';
    }
}
