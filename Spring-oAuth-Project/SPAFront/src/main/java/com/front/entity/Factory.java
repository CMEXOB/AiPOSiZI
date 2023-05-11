package com.front.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Factory {

    private Long id;
    private String name;
    private String region;

    public Factory( String name, String region) {
        this.name = name;
        this.region = region;
    }

    public Factory() {
    }

    @Override
    public String toString() {
        return "Factory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", region='" + region + '\'' +
                '}';
    }
}
