package com.front.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Employee {

    private Long id;
    private String name;
    private String surname;
    private Factory factory;

    public Employee(String name, String surname, Factory factory) {
        this.name = name;
        this.surname = surname;
        this.factory = factory;
    }

    public Employee(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Employee() {
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", factory=" + factory +
                '}';
    }
}
