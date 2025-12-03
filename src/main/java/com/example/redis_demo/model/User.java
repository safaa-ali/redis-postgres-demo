package com.example.redis_demo.model;

import java.io.Serializable;

public class User implements Serializable {  // must implement Serializable
    private Long id;
    private String name;

    public User() {}  // default constructor for Redis

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}

