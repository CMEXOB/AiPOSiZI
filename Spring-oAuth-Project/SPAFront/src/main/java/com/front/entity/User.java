package com.front.entity;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Getter
@Setter
public class User {
    String email;

    public User(String email) {
        this.email = email;
    }
}
