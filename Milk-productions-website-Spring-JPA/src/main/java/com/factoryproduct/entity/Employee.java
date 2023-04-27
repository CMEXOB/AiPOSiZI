package com.factoryproduct.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Employee {

    @Id
    @SequenceGenerator(
            name = "employee_sequence",
            sequenceName = "employee_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "employee_sequence"
    )
    private Long id;
    private String name;
    private String surname;
    @ManyToOne
    @JoinColumn(name = "factoryId", referencedColumnName = "id")
    private Factory factory;

    public Employee(String name, String surname, Factory factory) {
        this.name = name;
        this.surname = surname;
        this.factory = factory;
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
