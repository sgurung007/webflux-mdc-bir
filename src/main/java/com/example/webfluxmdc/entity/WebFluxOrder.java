package com.example.webfluxmdc.entity;

import java.util.UUID;

/**
 * @author bir birs sgrg007@gmail.com
 * @project bir-microservices
 * @since 3/21/2022
 */
public class WebFluxOrder {
    private UUID id;
    private String name;

    public WebFluxOrder() {
    }

    public WebFluxOrder(UUID id, String name) {
        this.id = UUID.randomUUID();
        this.name=name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
