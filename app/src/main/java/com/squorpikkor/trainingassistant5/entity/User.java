package com.squorpikkor.trainingassistant5.entity;

public class User {
//    Users - id, name, login, password

    private final String id;
    private final String name;
    private final String login;
    private final String password;

    public User(String id, String name, String login, String password) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
